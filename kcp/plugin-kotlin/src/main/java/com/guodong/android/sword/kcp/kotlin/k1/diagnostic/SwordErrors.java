package com.guodong.android.sword.kcp.kotlin.k1.diagnostic;

import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory1;
import org.jetbrains.kotlin.diagnostics.Errors;

import static org.jetbrains.kotlin.diagnostics.Severity.ERROR;

/**
 * Created by guodongAndroid on 2023/12/20.
 */
public interface SwordErrors {

    DiagnosticFactory1<PsiElement, String> NOT_FOUND_INVOCATION_HANDLER = DiagnosticFactory1.create(ERROR);

    Object _initializer = new Object() {
        {
            Errors.Initializer.initializeFactoryNamesAndDefaultErrorMessages(SwordErrors.class, SwordDefaultErrorMessages.INSTANCE);
        }
    };

}
