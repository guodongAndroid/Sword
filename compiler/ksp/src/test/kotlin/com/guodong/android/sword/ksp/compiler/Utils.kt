package com.guodong.android.sword.ksp.compiler

import com.tschuchort.compiletesting.JvmCompilationResult
import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.SourceFile
import com.tschuchort.compiletesting.kspArgs
import com.tschuchort.compiletesting.kspWithCompilation
import com.tschuchort.compiletesting.symbolProcessorProviders
import org.intellij.lang.annotations.Language

/**
 * Created by guodongAndroid on 2023/11/16.
 */
const val KT_EXTENSION = ".kt"

fun compile(
    handlerName: String,
    @Language("kotlin") handlerSource: String,
    args: MutableMap<String, String> = mutableMapOf()
): JvmCompilationResult {
    return KotlinCompilation().apply {
        sources = listOf(SourceFile.kotlin(handlerName.plus(KT_EXTENSION), handlerSource))
        kspWithCompilation = true
        kspArgs = args
        symbolProcessorProviders = listOf(SwordSymbolProcessorProvider())
        inheritClassPath = true
    }.compile()
}