package com.guodong.android.sword.kcp.kotlin

import com.tschuchort.compiletesting.KotlinCompilation
import org.junit.Test
import kotlin.test.assertEquals

/**
 * Created by guodongAndroid on 2023/11/16.
 */
class TestPrimitiveArg {

    @Test
    fun testByteArg() {
        val primitive = "Byte"
        val testName = "Test${primitive}Arg"
        val handlerName = "Test${primitive}ArgInvocationHandler"

        val testSource = """
                import com.guodong.android.sword.api.kt.Proxy

                class $testName {
                
                    @Proxy(
                        enable = true,
                        handler = "$handlerName"
                    )
                    fun test(s: $primitive): $primitive {
                        return s
                    }

                    fun test() = test(-1)
                }
            """.trimIndent()

        val testResult = testKotlinCompilation(testName, testSource).compile()
        assertEquals(KotlinCompilation.ExitCode.OK, testResult.exitCode)

        val testClazzOrigin = testResult.classLoader.loadClass(testName)
        val testMethodResultOrigin = runTest(testClazzOrigin)
        assertEquals((-1).toByte(), testMethodResultOrigin)

        val handlerSource = """
                import com.guodong.android.sword.api.kt.InvocationHandler

                class $handlerName : InvocationHandler {
                    
                    private val TAG = $handlerName::class.java.simpleName

                    override fun invoke(className: String, methodName: String, args: Array<Any?>): Any? {
                        println("${'$'}TAG.invoke: className = ${'$'}className, methodName = ${'$'}methodName, args(${'$'}{args.size}) = ${'$'}{args.joinToString()}")
                        return 1.toByte()
                    }
                }
            """.trimIndent()

        val handlerResult =
            swordKotlinCompilation(testName, testSource, handlerName, handlerSource).compile()
        assertEquals(KotlinCompilation.ExitCode.OK, handlerResult.exitCode)

        val testClazzSword = handlerResult.classLoader.loadClass(testName)
        val testMethodResultSword = runTest(testClazzSword)
        assertEquals((1).toByte(), testMethodResultSword)
    }

    @Test
    fun testBooleanArg() {
        val primitive = "Boolean"
        val testName = "Test${primitive}Arg"
        val handlerName = "Test${primitive}ArgInvocationHandler"

        val testSource = """
                import com.guodong.android.sword.api.kt.Proxy

                class $testName {
                
                    @Proxy(
                        enable = true,
                        handler = "$handlerName"
                    )
                    fun test(s: $primitive): $primitive {
                        return s
                    }

                    fun test() = test(false)
                }
            """.trimIndent()

        val testResult = testKotlinCompilation(testName, testSource).compile()
        assertEquals(KotlinCompilation.ExitCode.OK, testResult.exitCode)

        val testClazzOrigin = testResult.classLoader.loadClass(testName)
        val testMethodResultOrigin = runTest(testClazzOrigin)
        assertEquals(false, testMethodResultOrigin)

        val handlerSource = """
                import com.guodong.android.sword.api.kt.InvocationHandler

                class $handlerName : InvocationHandler {
                    
                    private val TAG = $handlerName::class.java.simpleName

                    override fun invoke(className: String, methodName: String, args: Array<Any?>): Any? {
                        println("${'$'}TAG.invoke: className = ${'$'}className, methodName = ${'$'}methodName, args(${'$'}{args.size}) = ${'$'}{args.joinToString()}")
                        return true
                    }
                }
            """.trimIndent()

        val handlerResult =
            swordKotlinCompilation(testName, testSource, handlerName, handlerSource).compile()
        assertEquals(KotlinCompilation.ExitCode.OK, handlerResult.exitCode)

        val testClazzSword = handlerResult.classLoader.loadClass(testName)
        val testMethodResultSword = runTest(testClazzSword)
        assertEquals(true, testMethodResultSword)
    }

    @Test
    fun testCharArg() {
        val primitive = "Char"
        val testName = "Test${primitive}Arg"
        val handlerName = "Test${primitive}ArgInvocationHandler"

        val testSource = """
                import com.guodong.android.sword.api.kt.Proxy

                class $testName {
                
                    @Proxy(
                        enable = true,
                        handler = "$handlerName"
                    )
                    fun test(s: $primitive): $primitive {
                        return s
                    }

                    fun test() = test('a')
                }
            """.trimIndent()

        val testResult = testKotlinCompilation(testName, testSource).compile()
        assertEquals(KotlinCompilation.ExitCode.OK, testResult.exitCode)

        val testClazzOrigin = testResult.classLoader.loadClass(testName)
        val testMethodResultOrigin = runTest(testClazzOrigin)
        assertEquals('a', testMethodResultOrigin)

        val handlerSource = """
                import com.guodong.android.sword.api.kt.InvocationHandler

                class $handlerName : InvocationHandler {
                    
                    private val TAG = $handlerName::class.java.simpleName

                    override fun invoke(className: String, methodName: String, args: Array<Any?>): Any? {
                        println("${'$'}TAG.invoke: className = ${'$'}className, methodName = ${'$'}methodName, args(${'$'}{args.size}) = ${'$'}{args.joinToString()}")
                        return 'b'
                    }
                }
            """.trimIndent()

        val handlerResult =
            swordKotlinCompilation(testName, testSource, handlerName, handlerSource).compile()
        assertEquals(KotlinCompilation.ExitCode.OK, handlerResult.exitCode)

        val testClazzSword = handlerResult.classLoader.loadClass(testName)
        val testMethodResultSword = runTest(testClazzSword)
        assertEquals('b', testMethodResultSword)
    }

    @Test
    fun testShortArg() {
        val primitive = "Short"
        val testName = "Test${primitive}Arg"
        val handlerName = "Test${primitive}ArgInvocationHandler"

        val testSource = """
                import com.guodong.android.sword.api.kt.Proxy

                class $testName {
                
                    @Proxy(
                        enable = true,
                        handler = "$handlerName"
                    )
                    fun test(s: $primitive): $primitive {
                        return s
                    }

                    fun test() = test(-1)
                }
            """.trimIndent()

        val testResult = testKotlinCompilation(testName, testSource).compile()
        assertEquals(KotlinCompilation.ExitCode.OK, testResult.exitCode)

        val testClazzOrigin = testResult.classLoader.loadClass(testName)
        val testMethodResultOrigin = runTest(testClazzOrigin)
        assertEquals((-1).toShort(), testMethodResultOrigin)

        val handlerSource = """
                import com.guodong.android.sword.api.kt.InvocationHandler

                class $handlerName : InvocationHandler {
                    
                    private val TAG = $handlerName::class.java.simpleName

                    override fun invoke(className: String, methodName: String, args: Array<Any?>): Any? {
                        println("${'$'}TAG.invoke: className = ${'$'}className, methodName = ${'$'}methodName, args(${'$'}{args.size}) = ${'$'}{args.joinToString()}")
                        return 1.toShort()
                    }
                }
            """.trimIndent()

        val handlerResult =
            swordKotlinCompilation(testName, testSource, handlerName, handlerSource).compile()
        assertEquals(KotlinCompilation.ExitCode.OK, handlerResult.exitCode)

        val testClazzSword = handlerResult.classLoader.loadClass(testName)
        val testMethodResultSword = runTest(testClazzSword)
        assertEquals((1).toShort(), testMethodResultSword)
    }

    @Test
    fun testIntArg() {
        val primitive = "Int"
        val testName = "Test${primitive}Arg"
        val handlerName = "Test${primitive}ArgInvocationHandler"

        val testSource = """
                import com.guodong.android.sword.api.kt.Proxy

                class $testName {
                
                    @Proxy(
                        enable = true,
                        handler = "$handlerName"
                    )
                    fun test(s: $primitive): $primitive {
                        return s
                    }

                    fun test() = test(-1)
                }
            """.trimIndent()

        val testResult = testKotlinCompilation(testName, testSource).compile()
        assertEquals(KotlinCompilation.ExitCode.OK, testResult.exitCode)

        val testClazzOrigin = testResult.classLoader.loadClass(testName)
        val testMethodResultOrigin = runTest(testClazzOrigin)
        assertEquals(-1, testMethodResultOrigin)

        val handlerSource = """
                import com.guodong.android.sword.api.kt.InvocationHandler

                class $handlerName : InvocationHandler {
                    
                    private val TAG = $handlerName::class.java.simpleName

                    override fun invoke(className: String, methodName: String, args: Array<Any?>): Any? {
                        println("${'$'}TAG.invoke: className = ${'$'}className, methodName = ${'$'}methodName, args(${'$'}{args.size}) = ${'$'}{args.joinToString()}")
                        return 1
                    }
                }
            """.trimIndent()

        val handlerResult =
            swordKotlinCompilation(testName, testSource, handlerName, handlerSource).compile()
        assertEquals(KotlinCompilation.ExitCode.OK, handlerResult.exitCode)

        val testClazzSword = handlerResult.classLoader.loadClass(testName)
        val testMethodResultSword = runTest(testClazzSword)
        assertEquals(1, testMethodResultSword)
    }

    @Test
    fun testLongArg() {
        val primitive = "Long"
        val testName = "Test${primitive}Arg"
        val handlerName = "Test${primitive}ArgInvocationHandler"

        val testSource = """
                import com.guodong.android.sword.api.kt.Proxy

                class $testName {
                
                    @Proxy(
                        enable = true,
                        handler = "$handlerName"
                    )
                    fun test(s: $primitive): $primitive {
                        return s
                    }

                    fun test() = test(-1L)
                }
            """.trimIndent()

        val testResult = testKotlinCompilation(testName, testSource).compile()
        assertEquals(KotlinCompilation.ExitCode.OK, testResult.exitCode)

        val testClazzOrigin = testResult.classLoader.loadClass(testName)
        val testMethodResultOrigin = runTest(testClazzOrigin)
        assertEquals(-1L, testMethodResultOrigin)

        val handlerSource = """
                import com.guodong.android.sword.api.kt.InvocationHandler

                class $handlerName : InvocationHandler {
                    
                    private val TAG = $handlerName::class.java.simpleName

                    override fun invoke(className: String, methodName: String, args: Array<Any?>): Any? {
                        println("${'$'}TAG.invoke: className = ${'$'}className, methodName = ${'$'}methodName, args(${'$'}{args.size}) = ${'$'}{args.joinToString()}")
                        return 1L
                    }
                }
            """.trimIndent()

        val handlerResult =
            swordKotlinCompilation(testName, testSource, handlerName, handlerSource).compile()
        assertEquals(KotlinCompilation.ExitCode.OK, handlerResult.exitCode)

        val testClazzSword = handlerResult.classLoader.loadClass(testName)
        val testMethodResultSword = runTest(testClazzSword)
        assertEquals(1L, testMethodResultSword)
    }

    @Test
    fun testDoubleArg() {
        val primitive = "Double"
        val testName = "Test${primitive}Arg"
        val handlerName = "Test${primitive}ArgInvocationHandler"

        val testSource = """
                import com.guodong.android.sword.api.kt.Proxy

                class $testName {
                
                    @Proxy(
                        enable = true,
                        handler = "$handlerName"
                    )
                    fun test(s: $primitive): $primitive {
                        return s
                    }

                    fun test() = test((-1).toDouble())
                }
            """.trimIndent()

        val testResult = testKotlinCompilation(testName, testSource).compile()
        assertEquals(KotlinCompilation.ExitCode.OK, testResult.exitCode)

        val testClazzOrigin = testResult.classLoader.loadClass(testName)
        val testMethodResultOrigin = runTest(testClazzOrigin)
        assertEquals((-1).toDouble(), testMethodResultOrigin)

        val handlerSource = """
                import com.guodong.android.sword.api.kt.InvocationHandler

                class $handlerName : InvocationHandler {
                    
                    private val TAG = $handlerName::class.java.simpleName

                    override fun invoke(className: String, methodName: String, args: Array<Any?>): Any? {
                        println("${'$'}TAG.invoke: className = ${'$'}className, methodName = ${'$'}methodName, args(${'$'}{args.size}) = ${'$'}{args.joinToString()}")
                        return 1.toDouble()
                    }
                }
            """.trimIndent()

        val handlerResult =
            swordKotlinCompilation(testName, testSource, handlerName, handlerSource).compile()
        assertEquals(KotlinCompilation.ExitCode.OK, handlerResult.exitCode)

        val testClazzSword = handlerResult.classLoader.loadClass(testName)
        val testMethodResultSword = runTest(testClazzSword)
        assertEquals((1).toDouble(), testMethodResultSword)
    }

    @Test
    fun testFloatArg() {
        val primitive = "Float"
        val testName = "Test${primitive}Arg"
        val handlerName = "Test${primitive}ArgInvocationHandler"

        val testSource = """
                import com.guodong.android.sword.api.kt.Proxy

                class $testName {
                
                    @Proxy(
                        enable = true,
                        handler = "$handlerName"
                    )
                    fun test(s: $primitive): $primitive {
                        return s
                    }

                    fun test() = test(-1.1F)
                }
            """.trimIndent()

        val testResult = testKotlinCompilation(testName, testSource).compile()
        assertEquals(KotlinCompilation.ExitCode.OK, testResult.exitCode)

        val testClazzOrigin = testResult.classLoader.loadClass(testName)
        val testMethodResultOrigin = runTest(testClazzOrigin)
        assertEquals(-1.1F, testMethodResultOrigin)

        val handlerSource = """
                import com.guodong.android.sword.api.kt.InvocationHandler

                class $handlerName : InvocationHandler {
                    
                    private val TAG = $handlerName::class.java.simpleName

                    override fun invoke(className: String, methodName: String, args: Array<Any?>): Any? {
                        println("${'$'}TAG.invoke: className = ${'$'}className, methodName = ${'$'}methodName, args(${'$'}{args.size}) = ${'$'}{args.joinToString()}")
                        return 1.1F
                    }
                }
            """.trimIndent()

        val handlerResult =
            swordKotlinCompilation(testName, testSource, handlerName, handlerSource).compile()
        assertEquals(KotlinCompilation.ExitCode.OK, handlerResult.exitCode)

        val testClazzSword = handlerResult.classLoader.loadClass(testName)
        val testMethodResultSword = runTest(testClazzSword)
        assertEquals(1.1F, testMethodResultSword)
    }

}