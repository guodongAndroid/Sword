package com.guodong.android.sword.kcp.kotlin

import org.jetbrains.kotlin.backend.jvm.extensions.ClassGenerator
import org.jetbrains.kotlin.backend.jvm.extensions.ClassGeneratorExtension
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity
import org.jetbrains.kotlin.cli.common.messages.MessageCollector
import org.jetbrains.kotlin.ir.declarations.IrClass

/**
 * Created by guodongAndroid on 2022/08/15.
 */
class SwordClassGeneratorExtension(
    private val messageCollector: MessageCollector,
) : ClassGeneratorExtension {

    init {
        messageCollector.report(
            CompilerMessageSeverity.WARNING,
            "Welcome to guodongAndroid sword kcp kotlin class generator extension"
        )
    }

    override fun generateClass(
        generator: ClassGenerator,
        declaration: IrClass?
    ): ClassGenerator = if (declaration == null) generator else SwordClassGenerator(
        messageCollector,
        generator,
    )
}