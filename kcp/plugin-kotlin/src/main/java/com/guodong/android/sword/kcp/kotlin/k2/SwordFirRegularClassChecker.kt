package com.guodong.android.sword.kcp.kotlin.k2

import com.guodong.android.sword.kcp.kotlin.SwordNames
import com.guodong.android.sword.kcp.kotlin.k2.diagnostic.SwordKtErrors
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter
import org.jetbrains.kotlin.diagnostics.reportOn
import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirRegularClassChecker
import org.jetbrains.kotlin.fir.declarations.FirClass
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration
import org.jetbrains.kotlin.fir.declarations.FirRegularClass
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias
import org.jetbrains.kotlin.fir.declarations.hasAnnotation
import org.jetbrains.kotlin.fir.declarations.utils.classId
import org.jetbrains.kotlin.fir.resolve.toSymbol
import org.jetbrains.kotlin.fir.symbols.SymbolInternals
import org.jetbrains.kotlin.fir.types.ConeClassLikeType
import org.jetbrains.kotlin.fir.types.ConeKotlinType
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef
import org.jetbrains.kotlin.fir.types.coneType
import org.jetbrains.kotlin.fir.types.toSymbol
import org.jetbrains.kotlin.name.ClassId

/**
 * Created by guodongAndroid on 2023/12/22.
 */
class SwordFirRegularClassChecker : FirRegularClassChecker() {
    override fun check(
        declaration: FirRegularClass,
        context: CheckerContext,
        reporter: DiagnosticReporter
    ) {
        val implementsInvocationHandlerInterface =
            declaration.implementsInvocationHandlerInterface(context.session)

        val annotateAsProxyHandlerAnnotation =
            declaration.annotateAsProxyHandlerAnnotation(context.session)

        when {
            implementsInvocationHandlerInterface && !annotateAsProxyHandlerAnnotation -> {
                reporter.reportOn(
                    declaration.source,
                    SwordKtErrors.SUGGESTION_ANNOTATED_PROXY_HANDLER,
                    context
                )
            }

            !implementsInvocationHandlerInterface && annotateAsProxyHandlerAnnotation -> {
                reporter.reportOn(
                    declaration.source,
                    SwordKtErrors.PROXY_HANDLER_SHOULD_IMPLEMENT_INVOCATION_HANDLER_INTERFACE,
                    context
                )
            }
        }
    }

    private fun FirClass.annotateAsProxyHandlerAnnotation(session: FirSession) =
        this.hasAnnotation(SwordNames.PROXY_HANDLER_CLASS_ID, session)

    private fun FirClass.implementsInvocationHandlerInterface(session: FirSession) =
        this.superTypeRefs.any {
            it.coneType.allSuperTypeClassIds(session)
                .contains(SwordNames.INVOCATION_HANDLER_CLASS_ID)
        }

    @OptIn(SymbolInternals::class)
    private fun ConeKotlinType.allSuperTypeClassIds(session: FirSession): List<ClassId> {
        if (this !is ConeClassLikeType) return emptyList()

        val thisTypeSession = toSymbol(session)?.moduleData?.session ?: return emptyList()
        val thisTypeFir = lookupTag.toSymbol(thisTypeSession)?.fir ?: return emptyList()
        return listOf(thisTypeFir.classId) + thisTypeFir.superTypes().flatMap {
            it.allSuperTypeClassIds(session)
        }
    }

    private fun FirClassLikeDeclaration.superTypes() = when (this) {
        is FirRegularClass -> superTypeRefs
        is FirTypeAlias -> listOf(expandedTypeRef)
        else -> emptyList()
    }.mapNotNull {
        when (it) {
//            is FirUserTypeRef -> typeResolver.resolveUserType(it).type
            is FirResolvedTypeRef -> it.type
            else -> null
        }
    }
}