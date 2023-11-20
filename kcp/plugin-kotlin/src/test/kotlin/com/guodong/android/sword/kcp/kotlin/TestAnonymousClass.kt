package com.guodong.android.sword.kcp.kotlin

import com.tschuchort.compiletesting.KotlinCompilation
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

/**
 * Created by guodongAndroid on 2023/11/20.
 */
class TestAnonymousClass {

    @Test
    fun testAnonymousClass() {
        val primitive = "AnonymousClass"
        val testName = "Test${primitive}"
        val handlerName = "Test${primitive}InvocationHandler"

        val testSource = """
                import com.guodong.android.sword.api.kt.Proxy
                import java.lang.Runnable

                class $testName {

                    fun test(runnable: Runnable) {
                        runnable.run()
                    }                   

                    fun test() {
                        test(object : Runnable {
                            
                            @Proxy(
                                enable = true,
                                handler = "$handlerName"
                            )
                            override fun run() {
                                println("I'm test method-one.")
                            }
                        })

                        object : Runnable {
                            
                            @Proxy(
                                enable = true,
                                handler = "$handlerName"
                            )
                            override fun run() {
                                println("I'm test method-two.")
                            }
                        }.run()
                    }
                }
            """.trimIndent()

        val testResult = testKotlinCompilation(testName, testSource).compile()
        assertEquals(KotlinCompilation.ExitCode.OK, testResult.exitCode)

        val testClazzOrigin = testResult.classLoader.loadClass(testName)
        val testMethodResultOrigin = captureStdOut { runTest(testClazzOrigin) }
        assertEquals(
            """
            I'm test method-one.
            I'm test method-two.
            """.trimIndent(),
            testMethodResultOrigin
        )

        val handlerSource = """
                import com.guodong.android.sword.api.kt.InvocationHandler
                import com.guodong.android.sword.kcp.kotlin.User

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
        assertEquals(
            """
            TestAnonymousClassInvocationHandler.invoke: className = TestAnonymousClass.test.<no name provided>, methodName = run, args(0) = 
            I'm test method by sword.
            TestAnonymousClassInvocationHandler.invoke: className = TestAnonymousClass.test.<no name provided>, methodName = run, args(0) = 
            I'm test method by sword.
            """.trimIndent(),
            testMethodResultSword
        )
    }
}