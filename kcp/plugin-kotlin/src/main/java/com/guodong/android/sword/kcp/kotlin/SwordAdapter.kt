package com.guodong.android.sword.kcp.kotlin

import org.jetbrains.org.objectweb.asm.*

internal class SwordAdapter(
    api: Int,
    methodVisitor: MethodVisitor,
    private val className: String,
    private val methodAccess: Int,
    private val methodName: String,
    private val methodDescriptor: String
) : MethodVisitor(api, methodVisitor), Opcodes {

    private val param = SwordParam()

    override fun visitAnnotation(descriptor: String, visible: Boolean): AnnotationVisitor {
        var av = super.visitAnnotation(descriptor, visible)

        if (debugDesc.contains(descriptor)) {
            param.hasDebugAnnotation = true
            if (av != null) {
                av = AnnotationAdapter(api, av, param)
            }
        }

        return av
    }

    override fun visitCode() {
        if (param.hasDebugAnnotation && param.enable) {

            super.visitInsn(Opcodes.ICONST_1)
            val label = Label()
            super.visitJumpInsn(Opcodes.IFEQ, label)

            val methodType = Type.getMethodType(
                methodDescriptor
            )
            val returnType = methodType.returnType

            val handler = param.handler

            if (handler.isNotEmpty()) {
                waveHandler(methodType, returnType, handler)
            } else {
                val sort = returnType.sort
                when {
                    sort == Type.VOID -> {
                        super.visitInsn(Opcodes.RETURN)
                    }
                    sort == Type.CHAR -> {
                        super.visitIntInsn(Opcodes.BIPUSH, 48)
                        super.visitInsn(returnType.getOpcode(Opcodes.IRETURN))
                    }
                    sort >= Type.BOOLEAN && sort <= Type.INT -> {
                        super.visitInsn(Opcodes.ICONST_M1)
                        super.visitInsn(returnType.getOpcode(Opcodes.IRETURN))
                    }
                    sort == Type.LONG -> {
                        super.visitLdcInsn(-1L)
                        super.visitInsn(Opcodes.LRETURN)
                    }
                    sort == Type.FLOAT -> {
                        super.visitLdcInsn(-1f)
                        super.visitInsn(Opcodes.FRETURN)
                    }
                    sort == Type.DOUBLE -> {
                        super.visitLdcInsn(-1.0)
                        super.visitInsn(Opcodes.DRETURN)
                    }
                    else -> {
                        super.visitInsn(Opcodes.ACONST_NULL)
                        super.visitInsn(Opcodes.ARETURN)
                    }
                }
            }

            super.visitLabel(label)
        }
        super.visitCode()
    }

    private fun waveHandler(t: Type, returnType: Type, handler: String) {
        val argumentTypes = t.argumentTypes
        val argumentSize = argumentTypes.size

        val isStaticMethod = methodAccess and Opcodes.ACC_STATIC != 0
        var localSize = if (isStaticMethod) 0 else 1
        val firstSlot = localSize
        for (argType in argumentTypes) {
            localSize += argType.size
        }

        val realHandler = covertToClassDescriptor(handler)

        super.visitTypeInsn(Opcodes.NEW, realHandler)
        super.visitInsn(Opcodes.DUP)
        super.visitMethodInsn(Opcodes.INVOKESPECIAL, realHandler, "<init>", "()V", false)
        super.visitVarInsn(Opcodes.ASTORE, localSize)
        super.visitVarInsn(Opcodes.ALOAD, localSize)

        super.visitLdcInsn(className)
        super.visitLdcInsn(methodName)

        weaveInt(argumentSize)
        super.visitTypeInsn(Opcodes.ANEWARRAY, "java/lang/Object")

        if (argumentTypes.isNotEmpty()) {
            waveArgs(argumentTypes, argumentSize, firstSlot)
        }

        super.visitMethodInsn(
            Opcodes.INVOKEINTERFACE,
            KT_INVOCATION_HANDLER_OWNER,
            INVOKE_METHOD,
            INVOCATION_HANDLER_INVOKE_DESC,
            true
        )

        val returnTypeSort = returnType.sort
        if (isPrimitiveType(returnTypeSort)) {
            wavePrimitiveReturn(returnTypeSort)
        } else {
            val internalName = returnType.internalName
            super.visitTypeInsn(Opcodes.CHECKCAST, internalName)
            super.visitInsn(Opcodes.ARETURN)
        }
    }

    private fun waveArgs(
        argumentTypes: Array<Type>,
        argumentSize: Int,
        firstSlot: Int
    ) {
        var slot = firstSlot
        super.visitInsn(Opcodes.DUP)
        for (i in 0 until argumentSize) {
            val argType = argumentTypes[i]
            val argTypeSort = argType.sort
            val loadOpcode = getLoadOpcode(argTypeSort)

            weaveInt(i)
            super.visitVarInsn(loadOpcode, slot)

            slot += argType.size
            if (isPrimitiveType(argTypeSort)) {
                wavePrimitiveMethodInvoke(argTypeSort)
            }

            super.visitInsn(Opcodes.AASTORE)

            if (i != argumentTypes.lastIndex) {
                super.visitInsn(Opcodes.DUP)
            }
        }
    }

    private fun covertToClassDescriptor(className: String): String {
        return className.replace("\\.".toRegex(), "/")
    }

    private fun weaveInt(num: Int) {
        if (num >= -1 && num <= 5) {
            super.visitInsn(num + 3)
        } else if (num <= 127) {
            super.visitIntInsn(Opcodes.BIPUSH, num)
        } else {
            super.visitIntInsn(Opcodes.SIPUSH, num)
        }
    }

    private fun getLoadOpcode(sort: Int): Int {
        return if (sort >= Type.BOOLEAN && sort <= Type.INT) {
            Opcodes.ILOAD
        } else if (sort == Type.LONG) {
            Opcodes.LLOAD
        } else if (sort == Type.FLOAT) {
            Opcodes.FLOAD
        } else if (sort == Type.DOUBLE) {
            Opcodes.DLOAD
        } else {
            Opcodes.ALOAD
        }
    }

    private fun isPrimitiveType(sort: Int): Boolean {
        return sort >= Type.BOOLEAN && sort <= Type.DOUBLE
    }

    private fun wavePrimitiveMethodInvoke(sort: Int) {
        when (sort) {
            Type.BOOLEAN -> {
                super.visitMethodInsn(
                    Opcodes.INVOKESTATIC,
                    "java/lang/Boolean",
                    "valueOf",
                    "(Z)Ljava/lang/Boolean;",
                    false
                )
            }
            Type.CHAR -> {
                super.visitMethodInsn(
                    Opcodes.INVOKESTATIC,
                    "java/lang/Character",
                    "valueOf",
                    "(C)Ljava/lang/Character;",
                    false
                )
            }
            Type.BYTE -> {
                super.visitMethodInsn(
                    Opcodes.INVOKESTATIC,
                    "java/lang/Byte",
                    "valueOf",
                    "(B)Ljava/lang/Byte;",
                    false
                )
            }
            Type.SHORT -> {
                super.visitMethodInsn(
                    Opcodes.INVOKESTATIC,
                    "java/lang/Short",
                    "valueOf",
                    "(S)Ljava/lang/Short;",
                    false
                )
            }
            Type.INT -> {
                super.visitMethodInsn(
                    Opcodes.INVOKESTATIC,
                    "java/lang/Integer",
                    "valueOf",
                    "(I)Ljava/lang/Integer;",
                    false
                )
            }
            Type.FLOAT -> {
                super.visitMethodInsn(
                    Opcodes.INVOKESTATIC,
                    "java/lang/Float",
                    "valueOf",
                    "(F)Ljava/lang/Float;",
                    false
                )
            }
            Type.LONG -> {
                super.visitMethodInsn(
                    Opcodes.INVOKESTATIC,
                    "java/lang/Long",
                    "valueOf",
                    "(J)Ljava/lang/Long;",
                    false
                )
            }
            Type.DOUBLE -> {
                super.visitMethodInsn(
                    Opcodes.INVOKESTATIC,
                    "java/lang/Double",
                    "valueOf",
                    "(D)Ljava/lang/Double;",
                    false
                )
            }
        }
    }

    private fun wavePrimitiveReturn(sort: Int) {
        when (sort) {
            Type.BOOLEAN -> {
                super.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/Boolean")
                super.visitMethodInsn(
                    Opcodes.INVOKEVIRTUAL,
                    "java/lang/Boolean",
                    "booleanValue",
                    "()Z",
                    false
                )
                super.visitInsn(Opcodes.IRETURN)
            }
            Type.CHAR -> {
                super.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/Character")
                super.visitMethodInsn(
                    Opcodes.INVOKEVIRTUAL,
                    "java/lang/Character",
                    "charValue",
                    "()C",
                    false
                )
                super.visitInsn(Opcodes.IRETURN)
            }
            Type.BYTE -> {
                super.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/Byte")
                super.visitMethodInsn(
                    Opcodes.INVOKEVIRTUAL,
                    "java/lang/Byte",
                    "byteValue",
                    "()B",
                    false
                )
                super.visitInsn(Opcodes.IRETURN)
            }
            Type.SHORT -> {
                super.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/Short")
                super.visitMethodInsn(
                    Opcodes.INVOKEVIRTUAL,
                    "java/lang/Short",
                    "shortValue",
                    "()S",
                    false
                )
                super.visitInsn(Opcodes.IRETURN)
            }
            Type.INT -> {
                super.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/Integer")
                super.visitMethodInsn(
                    Opcodes.INVOKEVIRTUAL,
                    "java/lang/Integer",
                    "intValue",
                    "()I",
                    false
                )
                super.visitInsn(Opcodes.IRETURN)
            }
            Type.FLOAT -> {
                super.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/Float")
                super.visitMethodInsn(
                    Opcodes.INVOKEVIRTUAL,
                    "java/lang/Float",
                    "floatValue",
                    "()F",
                    false
                )
                super.visitInsn(Opcodes.FRETURN)
            }
            Type.LONG -> {
                super.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/Long")
                super.visitMethodInsn(
                    Opcodes.INVOKEVIRTUAL,
                    "java/lang/Long",
                    "longValue",
                    "()J",
                    false
                )
                super.visitInsn(Opcodes.LRETURN)
            }
            Type.DOUBLE -> {
                super.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/Double")
                super.visitMethodInsn(
                    Opcodes.INVOKEVIRTUAL,
                    "java/lang/Double",
                    "doubleValue",
                    "()D",
                    false
                )
                super.visitInsn(Opcodes.DRETURN)
            }
        }
    }

    companion object {
        private const val DEBUG_JAVA_DESC = "Lcom/guodong/android/sword/api/Proxy;"
        private const val DEBUG_KT_DESC = "Lcom/guodong/android/sword/api/kt/Proxy;"

        private const val KT_INVOCATION_HANDLER_OWNER =
            "com/guodong/android/sword/api/kt/InvocationHandler"
        private const val INVOKE_METHOD = "invoke"
        private const val INVOCATION_HANDLER_INVOKE_DESC =
            "(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;"

        private val debugDesc = listOf(DEBUG_JAVA_DESC, DEBUG_KT_DESC)
    }
}