package com.guodong.android.sword.kcp.kotlin.k2.diagnostic

import com.guodong.android.sword.kcp.kotlin.BuildConfig
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactoryToRendererMap
import org.jetbrains.kotlin.diagnostics.rendering.BaseDiagnosticRendererFactory
import org.jetbrains.kotlin.diagnostics.rendering.Renderers

/**
 * Created by guodongAndroid on 2023/12/20.
 */
object SwordKtDefaultErrorMessages : BaseDiagnosticRendererFactory() {
    override val MAP = KtDiagnosticFactoryToRendererMap("Sword").also { map ->
        map.put(
            SwordKtErrors.SUGGESTION_ANNOTATED_PROXY_HANDLER,
            "Sword: Fir suggestion dependency ksp('com.sunxiaodou.kotlin.sword-compiler-ksp:${BuildConfig.KOTLIN_PLUGIN_VERSION}') " +
                    "and annotated with @ProxyHandler."
        )

        map.put(
            SwordKtErrors.PROXY_HANDLER_SHOULD_IMPLEMENT_INVOCATION_HANDLER_INTERFACE,
            "Sword: Fir Class annotated as @ProxyHandler, should implement InvocationHandler interface."
        )

        map.put(
            SwordKtErrors.HANDLER_NOT_ALLOW_EMPTY,
            "Sword: Fir @Proxy handler property value not allow empty."
        )

        map.put(
            SwordKtErrors.NOT_FOUND_INVOCATION_HANDLER,
            "Sword: Fir Not found [{0}] InvocationHandler.",
            Renderers.TO_STRING
        )
    }
}