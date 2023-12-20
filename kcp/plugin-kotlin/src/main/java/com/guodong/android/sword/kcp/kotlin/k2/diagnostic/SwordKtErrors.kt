package com.guodong.android.sword.kcp.kotlin.k2.diagnostic

import org.jetbrains.kotlin.com.intellij.psi.PsiClass
import org.jetbrains.kotlin.diagnostics.error1
import org.jetbrains.kotlin.diagnostics.rendering.RootDiagnosticRendererFactory

/**
 * Created by guodongAndroid on 2023/12/20.
 */
object SwordKtErrors {

    val NOT_FOUND_INVOCATION_HANDLER by error1<PsiClass, String>()

    init {
        RootDiagnosticRendererFactory.registerFactory(SwordKtDefaultErrorMessages)
    }
}