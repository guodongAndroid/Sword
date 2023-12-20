package com.guodong.android.sword.kcp.kotlin.k2.diagnostic

import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactoryToRendererMap
import org.jetbrains.kotlin.diagnostics.rendering.BaseDiagnosticRendererFactory
import org.jetbrains.kotlin.diagnostics.rendering.Renderers

/**
 * Created by guodongAndroid on 2023/12/20.
 */
object SwordKtDefaultErrorMessages : BaseDiagnosticRendererFactory() {
    override val MAP = KtDiagnosticFactoryToRendererMap("Sword").also { map ->
        map.put(
            SwordKtErrors.NOT_FOUND_INVOCATION_HANDLER,
            "Sword: Fir Not found [{0}] InvocationHandler.",
            Renderers.TO_STRING
        )
    }
}