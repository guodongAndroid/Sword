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
        compilerPluginRegistrars = listOf(SwordCompilerPluginRegistrar())
        inheritClassPath = true
    }
}

fun runTest(clazz: Class<*>): Any? {
    val instance = clazz.getDeclaredConstructor().newInstance()
    return clazz.getDeclaredMethod("test").invoke(instance)
}

fun runTopLevelTest(clazz: Class<*>): Any? {
    return clazz.getDeclaredMethod("test").invoke(null)
}

fun runObjectTest(clazz: Class<*>): Any? {
    val instanceField = clazz.getDeclaredField("INSTANCE")
    val instance = instanceField.get(null)
    return clazz.getDeclaredMethod("test").invoke(instance)
}

fun runInnerTest(outerClazz: Class<*>, clazz: Class<*>): Any? {
    val outerClassConstructor = outerClazz.getDeclaredConstructor()
    val outerClassInstance = outerClassConstructor.newInstance()
    val instance = clazz.getDeclaredConstructor(outerClazz).newInstance(outerClassInstance)
    return clazz.getDeclaredMethod("test").invoke(instance)
}

fun captureStdOut(block: () -> Unit): String {
    val originalStdOut = System.out
    val originalStdErr = System.err
    val stdOutStream = ByteArrayOutputStream()
    val printStream = object : PrintStream(stdOutStream) {
        override fun println(x: Any?) {
            originalStdOut.println(x)
            super.println(x)
        }
    }
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