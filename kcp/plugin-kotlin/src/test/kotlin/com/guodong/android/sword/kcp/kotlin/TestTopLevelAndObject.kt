package com.guodong.android.sword.kcp.kotlin

import com.tschuchort.compiletesting.KotlinCompilation
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

/**
 * Created by guodongAndroid on 2023/11/20.
 */
class TestTopLevelAndObject {

    @Test
    fun testTopLevel() {
        val primitive = "TopLevel"
        val testName = "Test${primitive}"
        val handlerName = "Test${primitive}InvocationHandler"

        val testSource = """
                @file:JvmName("$testName")

                import com.guodong.android.sword.api.kt.Proxy
                import com.guodong.android.sword.kcp.kotlin.User

                @Proxy(
                    enable = true,
                    handler = "$handlerName"
                )
                fun test() = User("guodongAndroid", 18, listOf("Coding", "Movie", "Game"))
            """.trimIndent()

        val testResult = testKotlinCompilation(testName, testSource).compile()
        assertEquals(KotlinCompilation.ExitCode.OK, testResult.exitCode)

        val testClazzOrigin = testResult.classLoader.loadClass(testName)
        val testMethodResultOrigin = runTopLevelTest(testClazzOrigin)
        assertEquals(User("guodongAndroid", 18, listOf("Coding", "Movie", "Game")), testMethodResultOrigin)

        val handlerSource = """
                import com.guodong.android.sword.api.kt.InvocationHandler
                import com.guodong.android.sword.kcp.kotlin.User

                class $handlerName : InvocationHandler {
                    
                    private val TAG = $handlerName::class.java.simpleName

                    override fun invoke(className: String, methodName: String, args: Array<Any?>): Any? {
                        println("${'$'}TAG.invoke: className = ${'$'}className, methodName = ${'$'}methodName, args(${'$'}{args.size}) = ${'$'}{args.joinToString()}")
                        return User("guodongAndroid", 18, listOf("Coding", "Movie", "LOL"))
                    }
                }
            """.trimIndent()

        val handlerResult =
            swordKotlinCompilation(testName, testSource, handlerName, handlerSource).compile()
        assertEquals(KotlinCompilation.ExitCode.OK, handlerResult.exitCode)

        val testClazzSword = handlerResult.classLoader.loadClass(testName)
        val testMethodResultSword = runTopLevelTest(testClazzSword)
        assertEquals(User("guodongAndroid", 18, listOf("Coding", "Movie", "LOL")), testMethodResultSword)
        assertNotEquals(User("guodongAndroid", 18, listOf("Coding", "Movie", "Game")), testMethodResultSword)
    }

    @Test
    fun testObject() {
        val primitive = "Object"
        val testName = "Test${primitive}"
        val handlerName = "Test${primitive}InvocationHandler"

        val testSource = """
                import com.guodong.android.sword.api.kt.Proxy
                import com.guodong.android.sword.kcp.kotlin.User

                object $testName {
                
                    @Proxy(
                        enable = true,
                        handler = "$handlerName"
                    )
                    fun test() = User("guodongAndroid", 18, listOf("Coding", "Movie", "Game"))
                }
            """.trimIndent()

        val testResult = testKotlinCompilation(testName, testSource).compile()
        assertEquals(KotlinCompilation.ExitCode.OK, testResult.exitCode)

        val testClazzOrigin = testResult.classLoader.loadClass(testName)
        val testMethodResultOrigin = runObjectTest(testClazzOrigin)
        assertEquals(User("guodongAndroid", 18, listOf("Coding", "Movie", "Game")), testMethodResultOrigin)

        val handlerSource = """
                import com.guodong.android.sword.api.kt.InvocationHandler
                import com.guodong.android.sword.kcp.kotlin.User

                class $handlerName : InvocationHandler {
                    
                    private val TAG = $handlerName::class.java.simpleName

                    override fun invoke(className: String, methodName: String, args: Array<Any?>): Any? {
                        println("${'$'}TAG.invoke: className = ${'$'}className, methodName = ${'$'}methodName, args(${'$'}{args.size}) = ${'$'}{args.joinToString()}")
                        return User("guodongAndroid", 18, listOf("Coding", "Movie", "LOL"))
                    }
                }
            """.trimIndent()

        val handlerResult =
            swordKotlinCompilation(testName, testSource, handlerName, handlerSource).compile()
        assertEquals(KotlinCompilation.ExitCode.OK, handlerResult.exitCode)

        val testClazzSword = handlerResult.classLoader.loadClass(testName)
        val testMethodResultSword = runObjectTest(testClazzSword)
        assertEquals(User("guodongAndroid", 18, listOf("Coding", "Movie", "LOL")), testMethodResultSword)
        assertNotEquals(User("guodongAndroid", 18, listOf("Coding", "Movie", "Game")), testMethodResultSword)
    }
}