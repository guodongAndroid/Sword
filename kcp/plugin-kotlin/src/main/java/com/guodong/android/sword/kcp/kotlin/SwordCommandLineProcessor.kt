package com.guodong.android.sword.kcp.kotlin

import com.google.auto.service.AutoService
import org.jetbrains.kotlin.compiler.plugin.AbstractCliOption
import org.jetbrains.kotlin.compiler.plugin.CommandLineProcessor

/**
 * Created by guodongAndroid on 2022/08/15.
 */
@AutoService(CommandLineProcessor::class)
class SwordCommandLineProcessor : CommandLineProcessor {

    override val pluginId: String = BuildConfig.KOTLIN_PLUGIN_ID

    override val pluginOptions: Collection<AbstractCliOption> = emptyList()
}