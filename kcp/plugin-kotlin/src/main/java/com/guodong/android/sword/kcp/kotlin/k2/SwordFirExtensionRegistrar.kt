package com.guodong.android.sword.kcp.kotlin.k2

import org.jetbrains.kotlin.fir.analysis.extensions.FirAdditionalCheckersExtension
import org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrar

/**
 * Created by guodongAndroid on 2023/12/20.
 */
class SwordFirExtensionRegistrar : FirExtensionRegistrar() {
    override fun ExtensionRegistrarContext.configurePlugin() {
        +FirAdditionalCheckersExtension.Factory { session ->
            SwordFirChecker(session)
        }
    }
}