package com.guodong.android.sword.kcp.kotlin

import com.google.auto.service.AutoService
import com.guodong.android.sword.kcp.kotlin.k1.SwordComponentContainerContributor
import com.guodong.android.sword.kcp.kotlin.k2.SwordFirExtensionRegistrar
import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.backend.jvm.extensions.ClassGeneratorExtension
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity
import org.jetbrains.kotlin.cli.common.messages.MessageCollector
import org.jetbrains.kotlin.compiler.plugin.CompilerPluginRegistrar
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.extensions.StorageComponentContainerContributor
import org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrarAdapter

/**
 * Created by guodongAndroid on 2022/08/15.
 */
@AutoService(CompilerPluginRegistrar::class)
class SwordCompilerPluginRegistrar(
    private val useIR: Boolean
) : CompilerPluginRegistrar() {

    // Used by service loader
    @Suppress("unused")
    constructor() : this(true)

    override val supportsK2: Boolean = true

    override fun ExtensionStorage.registerExtensions(configuration: CompilerConfiguration) {
        val messageCollector =
            configuration.get(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY, MessageCollector.NONE)

        messageCollector.report(
            CompilerMessageSeverity.WARNING,
            "Welcome to guodongAndroid sword kcp kotlin plugin (${BuildConfig.KOTLIN_PLUGIN_VERSION})."
        )

        StorageComponentContainerContributor.registerExtension(SwordComponentContainerContributor())

        FirExtensionRegistrarAdapter.registerExtension(SwordFirExtensionRegistrar())

        if (useIR) {
            IrGenerationExtension.registerExtension(SwordIrGenerationExtension(messageCollector))
        } else {
            ClassGeneratorExtension.registerExtension(SwordClassGeneratorExtension(messageCollector))
        }
    }
}