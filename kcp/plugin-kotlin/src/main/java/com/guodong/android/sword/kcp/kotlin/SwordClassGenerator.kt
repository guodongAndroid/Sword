package com.guodong.android.sword.kcp.kotlin

import org.jetbrains.kotlin.backend.jvm.extensions.ClassGenerator
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity
import org.jetbrains.kotlin.cli.common.messages.MessageCollector
import org.jetbrains.kotlin.ir.declarations.IrFunction
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction
import org.jetbrains.kotlin.ir.util.hasAnnotation
import org.jetbrains.kotlin.ir.util.isSuspend
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.org.objectweb.asm.MethodVisitor
import org.jetbrains.org.objectweb.asm.Opcodes

/**
 * Created by guodongAndroid on 2023/11/26.
 */
class SwordClassGenerator(
    private val messageCollector: MessageCollector,
    private val delegate: ClassGenerator,
) : ClassGenerator by delegate {

    private val annotations: List<FqName> = listOf(
        FqName("com.guodong.android.sword.api.kt.Proxy"),
    )

    override fun newMethod(
        declaration: IrFunction?,
        access: Int,
        name: String,
        desc: String,
        signature: String?,
        exceptions: Array<out String>?
    ): MethodVisitor {
        val newMethod = delegate.newMethod(declaration, access, name, desc, signature, exceptions)

        val irFunction = declaration ?: return newMethod

        if (irFunction.isExpect ||
            irFunction.isInline ||
            irFunction.isExternal ||
            irFunction.isSuspend
        ) {
            return newMethod
        }

        if (irFunction is IrSimpleFunction) {
            if (irFunction.isOperator ||
                irFunction.isInfix ||
                irFunction.isTailrec
            ) {
                return newMethod
            }
        }

        if (annotations.none { irFunction.annotations.hasAnnotation(it) }) {
            return newMethod
        }

        val className = irFunction.className

        messageCollector.report(
            CompilerMessageSeverity.WARNING,
            "Sword className = $className, methodName = $name"
        )

        return SwordAdapter(
            Opcodes.ASM9,
            newMethod,
            className,
            access,
            name,
            desc
        )
    }
}