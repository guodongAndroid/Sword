package com.guodong.android.sword.kcp.kotlin

import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.SourceFile
import org.intellij.lang.annotations.Language
import java.io.ByteArrayOutputStream
import java.io.PrintStream

/**
 * Created by guodongAndroid on 2023/11/16.
 */

const val KT_EXTENSION = ".kt"

fun testKotlinCompilation(
    testName: String,
    @Language("kotlin") testSource: String,
): KotlinCompilation {
    return KotlinCompilation().apply {
        sources = listOf(
            SourceFile.kotlin(testName.plus(KT_EXTENSION), testSource),
        )
        inheritClassPath = true
    }
}

fun swordKotlinCompilation(
    testName: String,
    @Language("kotlin") testSource: String,
    handlerName: String,
    @Language("kotlin") handlerSource: String
): KotlinCompilation {
    return KotlinCompilation().apply {
        sources = listOf(
            SourceFile.kotlin(testName.plus(KT_EXTENSION), testSource),
            SourceFile.kotlin(handlerName.plus(KT_EXTENSION), handlerSource)
        )
        compilerPlugins = listOf(SwordComponentRegistrar())
        inheritClassPath = true
    }
}

fun runTest(clazz: Class<*>): Any? {
    val instance = clazz.getDeclaredConstructor().newInstance()
    return clazz.getDeclaredMethod("test").invoke(instance)
}

fun captureStdOut(block: () -> Unit): String {
    val originalStdOut = System.out
    val originalStdErr = System.err
    val stdOutStream = ByteArrayOutputStream()
    val printStream = PrintStream(stdOutStream)
    System.setOut(printStream)
    System.setErr(printStream)
    try {
        block()
    } finally {
        System.setOut(originalStdOut)
        System.setErr(originalStdErr)
    }
    return stdOutStream.toString().unify()
}

private fun String.unify() = replace("\r\n", "\n").trimEnd()