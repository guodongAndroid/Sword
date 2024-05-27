package com.guodong.android.sword.kcp.kotlin.k2.diagnostic

import org.jetbrains.kotlin.com.intellij.psi.PsiClass
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod
import org.jetbrains.kotlin.diagnostics.error0
import org.jetbrains.kotlin.diagnostics.error1
import org.jetbrains.kotlin.diagnostics.rendering.RootDiagnosticRendererFactory
import org.jetbrains.kotlin.diagnostics.warning0

/**
 * Created by guodongAndroid on 2023/12/20.
 */
object SwordKtErrors {

    val SUGGESTION_ANNOTATED_PROXY_HANDLER by warning0<PsiClass>()

    val PROXY_HANDLER_SHOULD_IMPLEMENT_INVOCATION_HANDLER_INTERFACE by error0<PsiClass>()

    val HANDLER_NOT_ALLOW_EMPTY by error0<PsiMethod>()

    val NOT_FOUND_INVOCATION_HANDLER by error1<PsiMethod, String>()

    init {
        RootDiagnosticRendererFactory.registerFactory(SwordKtDefaultErrorMessages)
    }
}