package com.guodong.android.sword.kcp.kotlin

import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity
import org.jetbrains.kotlin.cli.common.messages.MessageCollector
import org.jetbrains.kotlin.codegen.ClassBuilder
import org.jetbrains.kotlin.codegen.DelegatingClassBuilder
import org.jetbrains.kotlin.descriptors.FunctionDescriptor
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOrigin
import org.jetbrains.org.objectweb.asm.MethodVisitor
import org.jetbrains.org.objectweb.asm.Opcodes

/**
 * Created by guodongAndroid on 2022/08/15.
 */
internal class SwordClassBuilder(
    private val messageCollector: MessageCollector,
    private val delegate: ClassBuilder
) : DelegatingClassBuilder() {

    private val annotations: List<FqName> = listOf(
        FqName("com.guodong.android.sword.api.kt.Proxy"),
    )

    override fun getDelegate(): ClassBuilder = delegate

    override fun newMethod(
        origin: JvmDeclarationOrigin,
        access: Int,
        name: String,
        desc: String,
        signature: String?,
        exceptions: Array<out String>?
    ): MethodVisitor {
        val newMethod = super.newMethod(origin, access, name, desc, signature, exceptions)

        val function = origin.descriptor as? FunctionDescriptor ?: return newMethod

        if (function.isOperator ||
            function.isInfix ||
            function.isInline ||
            function.isSuspend ||
            function.isTailrec
        ) {
            return newMethod
        }

        if (annotations.none { function.annotations.hasAnnotation(it) }) {
            return newMethod
        }

        val className = delegate.thisName

        messageCollector.report(
            CompilerMessageSeverity.WARNING,
            "Sword className = $className, methodName = $name"
        )

        val realClassName = className.substring(className.lastIndexOf("/") + 1)

        return SwordAdapter(
            Opcodes.ASM9,
            newMethod,
            realClassName,
            access,
            name,
            desc
        )
    }
}