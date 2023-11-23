package com.guodong.android.sword.kcp.kotlin

import com.google.auto.service.AutoService
import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity
import org.jetbrains.kotlin.cli.common.messages.MessageCollector
import org.jetbrains.kotlin.compiler.plugin.CompilerPluginRegistrar
import org.jetbrains.kotlin.config.CompilerConfiguration

/**
 * Created by guodongAndroid on 2022/08/15.
 */
@AutoService(CompilerPluginRegistrar::class)
class SwordCompilerPluginRegistrar : CompilerPluginRegistrar() {

    override val supportsK2: Boolean = true

    override fun ExtensionStorage.registerExtensions(configuration: CompilerConfiguration) {
        val messageCollector =
            configuration.get(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY, MessageCollector.NONE)

        messageCollector.report(
            CompilerMessageSeverity.WARNING,
            "Welcome to guodongAndroid sword kcp kotlin plugin (${BuildConfig.KOTLIN_PLUGIN_VERSION})."
        )

        /*ClassBuilderInterceptorExtension.registerExtension(
            SwordClassGenerationInterceptor(messageCollector)
        )*/

        IrGenerationExtension.registerExtension(SwordIrGenerationExtension(messageCollector))
    }
}