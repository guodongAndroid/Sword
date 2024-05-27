package com.guodong.android.sword.kcp.kotlin.k2

import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirFunctionChecker
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirRegularClassChecker
import org.jetbrains.kotlin.fir.analysis.extensions.FirAdditionalCheckersExtension

/**
 * Created by guodongAndroid on 2023/12/20.
 */
class SwordFirChecker(
    session: FirSession,
) : FirAdditionalCheckersExtension(session) {
    override val declarationCheckers: DeclarationCheckers = object : DeclarationCheckers() {

        override val regularClassCheckers: Set<FirRegularClassChecker> = setOf(
            SwordFirRegularClassChecker()
        )

        override val functionCheckers: Set<FirFunctionChecker> = setOf(
            SwordFirFunctionChecker(),
        )
    }
}