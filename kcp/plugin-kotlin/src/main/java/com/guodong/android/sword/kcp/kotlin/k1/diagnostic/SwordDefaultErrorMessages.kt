package com.guodong.android.sword.kcp.kotlin.k1.diagnostic

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
            SwordErrors.NOT_FOUND_INVOCATION_HANDLER,
            "Sword: Not found [{0}] InvocationHandler.",
            Renderers.TO_STRING
        )
    }
}