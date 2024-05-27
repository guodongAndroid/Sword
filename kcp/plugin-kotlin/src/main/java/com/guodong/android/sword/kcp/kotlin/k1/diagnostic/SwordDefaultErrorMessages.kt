package com.guodong.android.sword.kcp.kotlin.k1.diagnostic

import com.guodong.android.sword.kcp.kotlin.BuildConfig
import org.jetbrains.kotlin.diagnostics.rendering.DefaultErrorMessages
import org.jetbrains.kotlin.diagnostics.rendering.DiagnosticFactoryToRendererMap
import org.jetbrains.kotlin.diagnostics.rendering.Renderers

/**
 * Created by guodongAndroid on 2023/12/20.
 */
object SwordDefaultErrorMessages : DefaultErrorMessages.Extension {

    private val MAP = DiagnosticFactoryToRendererMap("Sword")

    override fun getMap() = MAP

    init {
        MAP.put(
            SwordErrors.SUGGESTION_ANNOTATED_PROXY_HANDLER,
            "Sword: suggestion dependency ksp('com.sunxiaodou.kotlin.sword-compiler-ksp:${BuildConfig.KOTLIN_PLUGIN_VERSION}') " +
                    "and annotated with @ProxyHandler."
        )

        MAP.put(
            SwordErrors.PROXY_HANDLER_SHOULD_IMPLEMENT_INVOCATION_HANDLER_INTERFACE,
            "Sword: Class annotated as @ProxyHandler, should implement InvocationHandler interface.",
        )

        MAP.put(
            SwordErrors.HANDLER_NOT_ALLOW_EMPTY,
            "Sword: @Proxy handler property value not allow empty.",
        )

        MAP.put(
            SwordErrors.NOT_FOUND_INVOCATION_HANDLER,
            "Sword: Not found [{0}] InvocationHandler.",
            Renderers.TO_STRING
        )
    }
}