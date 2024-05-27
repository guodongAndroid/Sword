package com.guodong.android.sword.kcp.kotlin.k1.diagnostic;

import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory0;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory1;
import org.jetbrains.kotlin.diagnostics.Errors;

import static org.jetbrains.kotlin.diagnostics.Severity.ERROR;
import static org.jetbrains.kotlin.diagnostics.Severity.WARNING;

/**
 * Created by guodongAndroid on 2023/12/20.
 */
public interface SwordErrors {

    DiagnosticFactory0<PsiElement> SUGGESTION_ANNOTATED_PROXY_HANDLER = DiagnosticFactory0.create(WARNING);

    DiagnosticFactory0<PsiElement> PROXY_HANDLER_SHOULD_IMPLEMENT_INVOCATION_HANDLER_INTERFACE = DiagnosticFactory0.create(ERROR);

    DiagnosticFactory0<PsiElement> HANDLER_NOT_ALLOW_EMPTY = DiagnosticFactory0.create(ERROR);

    DiagnosticFactory1<PsiElement, String> NOT_FOUND_INVOCATION_HANDLER = DiagnosticFactory1.create(ERROR);

    Object _initializer = new Object() {
        {
            Errors.Initializer.initializeFactoryNamesAndDefaultErrorMessages(SwordErrors.class, SwordDefaultErrorMessages.INSTANCE);
        }
    };

}
