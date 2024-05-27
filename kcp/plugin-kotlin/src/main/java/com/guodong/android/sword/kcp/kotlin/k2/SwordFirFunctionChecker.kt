package com.guodong.android.sword.kcp.kotlin.k2

import com.guodong.android.sword.kcp.kotlin.SwordNames
import com.guodong.android.sword.kcp.kotlin.k2.diagnostic.SwordKtErrors
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter
import org.jetbrains.kotlin.diagnostics.reportOn
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirFunctionChecker
import org.jetbrains.kotlin.fir.declarations.FirClass
import org.jetbrains.kotlin.fir.declarations.FirFunction
import org.jetbrains.kotlin.fir.declarations.getAnnotationByClassId
import org.jetbrains.kotlin.fir.declarations.getBooleanArgument
import org.jetbrains.kotlin.fir.declarations.getStringArgument
import org.jetbrains.kotlin.fir.declarations.hasAnnotation
import org.jetbrains.kotlin.fir.declarations.utils.classId
import org.jetbrains.kotlin.name.ClassId
import org.jetbrains.kotlin.name.FqName

/**
 * Created by guodongAndroid on 2023/12/20.
 */
class SwordFirFunctionChecker : FirFunctionChecker() {
    override fun check(
        declaration: FirFunction,
        context: CheckerContext,
        reporter: DiagnosticReporter
    ) {
        if (declaration.hasAnnotation(SwordNames.PROXY_CLASS_ID, context.session)) {
            checkFunction(declaration, context, reporter)
        }
    }

    private fun checkFunction(
        declaration: FirFunction,
        context: CheckerContext,
        reporter: DiagnosticReporter
    ) {
        val annotation =
            declaration.getAnnotationByClassId(SwordNames.PROXY_CLASS_ID, context.session)
                ?: return

        val enable =
            annotation.getBooleanArgument(SwordNames.PROXY_ENABLE_VALUE_ARGUMENT_NAME) ?: return
        if (!enable) {
            return
        }

        val handler =
            annotation.getStringArgument(SwordNames.PROXY_HANDLER_VALUE_ARGUMENT_NAME) ?: return
        if (handler.isEmpty()) {
            reporter.reportOn(
                declaration.source,
                SwordKtErrors.HANDLER_NOT_ALLOW_EMPTY,
                context
            )
            return
        }

        /*val handlerClassId = ClassId.topLevel(FqName(handler))
        val handlerClassDeclaration = context.containingDeclarations
            .filterIsInstance<FirClass>()
            .find { it.classId == handlerClassId }
        if (handlerClassDeclaration == null) {
            reporter.reportOn(
                declaration.source,
                SwordKtErrors.NOT_FOUND_INVOCATION_HANDLER,
                handler,
                context
            )
        }*/
    }
}