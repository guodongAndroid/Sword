package com.guodong.android.sword.kcp.kotlin

import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity
import org.jetbrains.kotlin.cli.common.messages.MessageCollector
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment
import org.jetbrains.kotlin.name.ClassId
import org.jetbrains.kotlin.name.FqName

class SwordIrGenerationExtension(
    private val messageCollector: MessageCollector,
) : IrGenerationExtension {

    override fun generate(moduleFragment: IrModuleFragment, pluginContext: IrPluginContext) {
        messageCollector.report(
            CompilerMessageSeverity.WARNING,
            "Welcome to guodongAndroid sword kcp kotlin ir generation extension"
        )

        val proxyAnnotation =
            pluginContext.referenceClass(ClassId.topLevel(SwordNames.PROXY_FQ_NAME))
        if (proxyAnnotation == null) {
            messageCollector.report(
                CompilerMessageSeverity.ERROR,
                "Not found `Proxy` annotation, make sure to add the \"sword-api-kt\" library to your dependencies"
            )
            return
        }

        moduleFragment.transform(
            SwordTransformer(pluginContext, proxyAnnotation, messageCollector),
            null
        )
    }
}