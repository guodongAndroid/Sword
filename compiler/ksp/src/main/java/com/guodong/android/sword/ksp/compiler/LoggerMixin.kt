package com.guodong.android.sword.ksp.compiler

import com.google.devtools.ksp.processing.SymbolProcessorEnvironment

/**
 * Created by guodongAndroid on 2022/8/17.
 */
interface LoggerMixin {

    val env: SymbolProcessorEnvironment

    val logger
        get() = env.logger
}