package com.guodong.android.sword.kcp.kotlin

import com.tschuchort.compiletesting.KotlinCompilation
import org.junit.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

/**
 * Created by guodongAndroid on 2023/11/19.
 */
class TestDeclarationChecker {

    @Test
    fun testDeclarationCheckerWhenProxyEnabled() {
        val testName = "TestDeclarationChecker"
        val handlerName = "TestDeclarationCheckerInvocationHandler"

        val testSource = """
                import com.guodong.android.sword.api.kt.Proxy

                class $testName {
                
                    @Proxy(
                        enable = true,
                        handler = "${handlerName}2"
                    )
                    fun test() {
                        println("I'm test method.")
                    }
                }
            """.trimIndent()

        val testResult = testKotlinCompilation(testName, testSource).compile()
        assertEquals(KotlinCompilation.ExitCode.OK, testResult.exitCode)

        val testClazzOrigin = testResult.classLoader.loadClass(testName)
        val testMethodResultOrigin = captureStdOut { runTest(testClazzOrigin) }
        assertEquals(
            """
            I'm test method.
            """.trimIndent(),
            testMethodResultOrigin
        )

        val handlerSource = """
                import com.guodong.android.sword.api.kt.InvocationHandler

                class $handlerName : InvocationHandler {
                    
                    private val TAG = $handlerName::class.java.simpleName

                    override fun invoke(className: String, methodName: String, args: Array<Any?>): Any? {
                        println("${'$'}TAG.invoke: className = ${'$'}className, methodName = ${'$'}methodName, args(${'$'}{args.size}) = ${'$'}{args.joinToString()}")
                        println("I'm test method by sword.")
                        return Unit
                    }
                }
            """.trimIndent()

        val handlerResult =
            swordKotlinCompilation(testName, testSource, handlerName, handlerSource).compile()
        assertEquals(KotlinCompilation.ExitCode.COMPILATION_ERROR, handlerResult.exitCode)
        assertContains(handlerResult.messages, "Sword: Not found [TestDeclarationCheckerInvocationHandler2] InvocationHandler.")
    }

    @Test
    fun testDeclarationCheckerWhenProxyEnabledUseK2() {
        val testName = "TestDeclarationChecker"
        val handlerName = "TestDeclarationCheckerInvocationHandler"

        val testSource = """
                import com.guodong.android.sword.api.kt.Proxy

                class $testName {
                
                    @Proxy(
                        enable = true,
                        handler = "${handlerName}2"
                    )
                    fun test() {
                        println("I'm test method.")
                    }
                }
            """.trimIndent()

        val testResult = testKotlinCompilation(testName, testSource).compile()
        assertEquals(KotlinCompilation.ExitCode.OK, testResult.exitCode)

        val testClazzOrigin = testResult.classLoader.loadClass(testName)
        val testMethodResultOrigin = captureStdOut { runTest(testClazzOrigin) }
        assertEquals(
            """
            I'm test method.
            """.trimIndent(),
            testMethodResultOrigin
        )

        val handlerSource = """
                import com.guodong.android.sword.api.kt.InvocationHandler

                class $handlerName : InvocationHandler {
                    
                    private val TAG = $handlerName::class.java.simpleName

                    override fun invoke(className: String, methodName: String, args: Array<Any?>): Any? {
                        println("${'$'}TAG.invoke: className = ${'$'}className, methodName = ${'$'}methodName, args(${'$'}{args.size}) = ${'$'}{args.joinToString()}")
                        println("I'm test method by sword.")
                        return Unit
                    }
                }
            """.trimIndent()

        val handlerResult =
            swordKotlinCompilation(testName, testSource, handlerName, handlerSource, true).compile()
        assertEquals(KotlinCompilation.ExitCode.COMPILATION_ERROR, handlerResult.exitCode)
        assertContains(handlerResult.messages, "Sword: Fir Not found [TestDeclarationCheckerInvocationHandler2] InvocationHandler.")
    }

    @Test
    fun testDeclarationCheckerWhenProxyDisabled() {
        val testName = "TestDeclarationChecker"
        val handlerName = "TestDeclarationCheckerInvocationHandler"

        val testSource = """
                import com.guodong.android.sword.api.kt.Proxy

                class $testName {
                
                    @Proxy(
                        enable = false,
                        handler = "${handlerName}2"
                    )
                    fun test() {
                        println("I'm test method.")
                    }
                }
            """.trimIndent()

        val testResult = testKotlinCompilation(testName, testSource).compile()
        assertEquals(KotlinCompilation.ExitCode.OK, testResult.exitCode)

        val testClazzOrigin = testResult.classLoader.loadClass(testName)
        val testMethodResultOrigin = captureStdOut { runTest(testClazzOrigin) }
        assertEquals(
            """
            I'm test method.
            """.trimIndent(),
            testMethodResultOrigin
        )

        val handlerSource = """
                import com.guodong.android.sword.api.kt.InvocationHandler

                class $handlerName : InvocationHandler {
                    
                    private val TAG = $handlerName::class.java.simpleName

                    override fun invoke(className: String, methodName: String, args: Array<Any?>): Any? {
                        println("${'$'}TAG.invoke: className = ${'$'}className, methodName = ${'$'}methodName, args(${'$'}{args.size}) = ${'$'}{args.joinToString()}")
                        println("I'm test method by sword.")
                        return Unit
                    }
                }
            """.trimIndent()

        val handlerResult =
            swordKotlinCompilation(testName, testSource, handlerName, handlerSource).compile()
        assertEquals(KotlinCompilation.ExitCode.OK, handlerResult.exitCode)

        val testClazzSword = handlerResult.classLoader.loadClass(testName)
        val testMethodResultSword = captureStdOut { runTest(testClazzSword) }
        assertNotEquals(
            """
            TestNoArgInvocationHandler.invoke: className = TestNoArg, methodName = test, args(0) = 
            I'm test method by sword.
            """.trimIndent(),
            testMethodResultSword
        )

        assertEquals(
            """
            I'm test method.
            """.trimIndent(),
            testMethodResultSword
        )
    }

    @Test
    fun testDeclarationCheckerWhenProxyDisabledUseK2() {
        val testName = "TestDeclarationChecker"
        val handlerName = "TestDeclarationCheckerInvocationHandler"

        val testSource = """
                import com.guodong.android.sword.api.kt.Proxy

                class $testName {
                
                    @Proxy(
                        enable = false,
                        handler = "${handlerName}2"
                    )
                    fun test() {
                        println("I'm test method.")
                    }
                }
            """.trimIndent()

        val testResult = testKotlinCompilation(testName, testSource).compile()
        assertEquals(KotlinCompilation.ExitCode.OK, testResult.exitCode)

        val testClazzOrigin = testResult.classLoader.loadClass(testName)
        val testMethodResultOrigin = captureStdOut { runTest(testClazzOrigin) }
        assertEquals(
            """
            I'm test method.
            """.trimIndent(),
            testMethodResultOrigin
        )

        val handlerSource = """
                import com.guodong.android.sword.api.kt.InvocationHandler

                class $handlerName : InvocationHandler {
                    
                    private val TAG = $handlerName::class.java.simpleName

                    override fun invoke(className: String, methodName: String, args: Array<Any?>): Any? {
                        println("${'$'}TAG.invoke: className = ${'$'}className, methodName = ${'$'}methodName, args(${'$'}{args.size}) = ${'$'}{args.joinToString()}")
                        println("I'm test method by sword.")
                        return Unit
                    }
                }
            """.trimIndent()

        val handlerResult =
            swordKotlinCompilation(testName, testSource, handlerName, handlerSource, true).compile()
        assertEquals(KotlinCompilation.ExitCode.OK, handlerResult.exitCode)

        val testClazzSword = handlerResult.classLoader.loadClass(testName)
        val testMethodResultSword = captureStdOut { runTest(testClazzSword) }
        assertNotEquals(
            """
            TestNoArgInvocationHandler.invoke: className = TestNoArg, methodName = test, args(0) = 
            I'm test method by sword.
            """.trimIndent(),
            testMethodResultSword
        )

        assertEquals(
            """
            I'm test method.
            """.trimIndent(),
            testMethodResultSword
        )
    }
}