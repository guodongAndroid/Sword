package com.guodong.android.sword.kcp.kotlin

import com.tschuchort.compiletesting.KotlinCompilation
import org.junit.Test
import kotlin.test.assertEquals

/**
 * Created by guodongAndroid on 2023/11/20.
 */
class TestPrimitiveArrayArg {

    @Test
    fun testByteArrayArg() {
        val primitive = "ByteArray"
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

                    fun test() = test(byteArrayOf(-1))
                }
            """.trimIndent()

        val testResult = testKotlinCompilation(testName, testSource).compile()
        assertEquals(KotlinCompilation.ExitCode.OK, testResult.exitCode)

        val testClazzOrigin = testResult.classLoader.loadClass(testName)
        val testMethodResultOrigin = runTest(testClazzOrigin) as ByteArray
        assertEquals((-1).toByte(), testMethodResultOrigin[0])

        val handlerSource = """
                import com.guodong.android.sword.api.kt.InvocationHandler

                class $handlerName : InvocationHandler {
                    
                    private val TAG = $handlerName::class.java.simpleName

                    override fun invoke(className: String, methodName: String, args: Array<Any?>): Any? {
                        println("${'$'}TAG.invoke: className = ${'$'}className, methodName = ${'$'}methodName, args(${'$'}{args.size}) = ${'$'}{args.joinToString()}")
                        return byteArrayOf(1)
                    }
                }
            """.trimIndent()

        val handlerResult =
            swordKotlinCompilation(testName, testSource, handlerName, handlerSource).compile()
        assertEquals(KotlinCompilation.ExitCode.OK, handlerResult.exitCode)

        val testClazzSword = handlerResult.classLoader.loadClass(testName)
        val testMethodResultSword = runTest(testClazzSword) as ByteArray
        assertEquals((1).toByte(), testMethodResultSword[0])
    }

    @Test
    fun testBooleanArrayArg() {
        val primitive = "BooleanArray"
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

                    fun test() = test(booleanArrayOf(false))
                }
            """.trimIndent()

        val testResult = testKotlinCompilation(testName, testSource).compile()
        assertEquals(KotlinCompilation.ExitCode.OK, testResult.exitCode)

        val testClazzOrigin = testResult.classLoader.loadClass(testName)
        val testMethodResultOrigin = runTest(testClazzOrigin) as BooleanArray
        assertEquals(false, testMethodResultOrigin[0])

        val handlerSource = """
                import com.guodong.android.sword.api.kt.InvocationHandler

                class $handlerName : InvocationHandler {
                    
                    private val TAG = $handlerName::class.java.simpleName

                    override fun invoke(className: String, methodName: String, args: Array<Any?>): Any? {
                        println("${'$'}TAG.invoke: className = ${'$'}className, methodName = ${'$'}methodName, args(${'$'}{args.size}) = ${'$'}{args.joinToString()}")
                        return booleanArrayOf(true)
                    }
                }
            """.trimIndent()

        val handlerResult =
            swordKotlinCompilation(testName, testSource, handlerName, handlerSource).compile()
        assertEquals(KotlinCompilation.ExitCode.OK, handlerResult.exitCode)

        val testClazzSword = handlerResult.classLoader.loadClass(testName)
        val testMethodResultSword = runTest(testClazzSword) as BooleanArray
        assertEquals(true, testMethodResultSword[0])
    }

    @Test
    fun testCharArrayArg() {
        val primitive = "CharArray"
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

                    fun test() = test(charArrayOf('a'))
                }
            """.trimIndent()

        val testResult = testKotlinCompilation(testName, testSource).compile()
        assertEquals(KotlinCompilation.ExitCode.OK, testResult.exitCode)

        val testClazzOrigin = testResult.classLoader.loadClass(testName)
        val testMethodResultOrigin = runTest(testClazzOrigin) as CharArray
        assertEquals('a', testMethodResultOrigin[0])

        val handlerSource = """
                import com.guodong.android.sword.api.kt.InvocationHandler

                class $handlerName : InvocationHandler {
                    
                    private val TAG = $handlerName::class.java.simpleName

                    override fun invoke(className: String, methodName: String, args: Array<Any?>): Any? {
                        println("${'$'}TAG.invoke: className = ${'$'}className, methodName = ${'$'}methodName, args(${'$'}{args.size}) = ${'$'}{args.joinToString()}")
                        return charArrayOf('b')
                    }
                }
            """.trimIndent()

        val handlerResult =
            swordKotlinCompilation(testName, testSource, handlerName, handlerSource).compile()
        assertEquals(KotlinCompilation.ExitCode.OK, handlerResult.exitCode)

        val testClazzSword = handlerResult.classLoader.loadClass(testName)
        val testMethodResultSword = runTest(testClazzSword) as CharArray
        assertEquals('b', testMethodResultSword[0])
    }

    @Test
    fun testShortArrayArg() {
        val primitive = "ShortArray"
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

                    fun test() = test(shortArrayOf(-1))
                }
            """.trimIndent()

        val testResult = testKotlinCompilation(testName, testSource).compile()
        assertEquals(KotlinCompilation.ExitCode.OK, testResult.exitCode)

        val testClazzOrigin = testResult.classLoader.loadClass(testName)
        val testMethodResultOrigin = runTest(testClazzOrigin) as ShortArray
        assertEquals((-1).toShort(), testMethodResultOrigin[0])

        val handlerSource = """
                import com.guodong.android.sword.api.kt.InvocationHandler

                class $handlerName : InvocationHandler {
                    
                    private val TAG = $handlerName::class.java.simpleName

                    override fun invoke(className: String, methodName: String, args: Array<Any?>): Any? {
                        println("${'$'}TAG.invoke: className = ${'$'}className, methodName = ${'$'}methodName, args(${'$'}{args.size}) = ${'$'}{args.joinToString()}")
                        return shortArrayOf(1)
                    }
                }
            """.trimIndent()

        val handlerResult =
            swordKotlinCompilation(testName, testSource, handlerName, handlerSource).compile()
        assertEquals(KotlinCompilation.ExitCode.OK, handlerResult.exitCode)

        val testClazzSword = handlerResult.classLoader.loadClass(testName)
        val testMethodResultSword = runTest(testClazzSword) as ShortArray
        assertEquals((1).toShort(), testMethodResultSword[0])
    }

    @Test
    fun testIntArrayArg() {
        val primitive = "IntArray"
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

                    fun test() = test(intArrayOf(-1))
                }
            """.trimIndent()

        val testResult = testKotlinCompilation(testName, testSource).compile()
        assertEquals(KotlinCompilation.ExitCode.OK, testResult.exitCode)

        val testClazzOrigin = testResult.classLoader.loadClass(testName)
        val testMethodResultOrigin = runTest(testClazzOrigin) as IntArray
        assertEquals(-1, testMethodResultOrigin[0])

        val handlerSource = """
                import com.guodong.android.sword.api.kt.InvocationHandler

                class $handlerName : InvocationHandler {
                    
                    private val TAG = $handlerName::class.java.simpleName

                    override fun invoke(className: String, methodName: String, args: Array<Any?>): Any? {
                        println("${'$'}TAG.invoke: className = ${'$'}className, methodName = ${'$'}methodName, args(${'$'}{args.size}) = ${'$'}{args.joinToString()}")
                        return intArrayOf(1)
                    }
                }
            """.trimIndent()

        val handlerResult =
            swordKotlinCompilation(testName, testSource, handlerName, handlerSource).compile()
        assertEquals(KotlinCompilation.ExitCode.OK, handlerResult.exitCode)

        val testClazzSword = handlerResult.classLoader.loadClass(testName)
        val testMethodResultSword = runTest(testClazzSword) as IntArray
        assertEquals(1, testMethodResultSword[0])
    }

    @Test
    fun testLongArrayArg() {
        val primitive = "LongArray"
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

                    fun test() = test(longArrayOf(-1L))
                }
            """.trimIndent()

        val testResult = testKotlinCompilation(testName, testSource).compile()
        assertEquals(KotlinCompilation.ExitCode.OK, testResult.exitCode)

        val testClazzOrigin = testResult.classLoader.loadClass(testName)
        val testMethodResultOrigin = runTest(testClazzOrigin) as LongArray
        assertEquals(-1L, testMethodResultOrigin[0])

        val handlerSource = """
                import com.guodong.android.sword.api.kt.InvocationHandler

                class $handlerName : InvocationHandler {
                    
                    private val TAG = $handlerName::class.java.simpleName

                    override fun invoke(className: String, methodName: String, args: Array<Any?>): Any? {
                        println("${'$'}TAG.invoke: className = ${'$'}className, methodName = ${'$'}methodName, args(${'$'}{args.size}) = ${'$'}{args.joinToString()}")
                        return longArrayOf(1)
                    }
                }
            """.trimIndent()

        val handlerResult =
            swordKotlinCompilation(testName, testSource, handlerName, handlerSource).compile()
        assertEquals(KotlinCompilation.ExitCode.OK, handlerResult.exitCode)

        val testClazzSword = handlerResult.classLoader.loadClass(testName)
        val testMethodResultSword = runTest(testClazzSword) as LongArray
        assertEquals(1L, testMethodResultSword[0])
    }

    @Test
    fun testDoubleArrayArg() {
        val primitive = "DoubleArray"
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

                    fun test() = test(doubleArrayOf(-1.0))
                }
            """.trimIndent()

        val testResult = testKotlinCompilation(testName, testSource).compile()
        assertEquals(KotlinCompilation.ExitCode.OK, testResult.exitCode)

        val testClazzOrigin = testResult.classLoader.loadClass(testName)
        val testMethodResultOrigin = runTest(testClazzOrigin) as DoubleArray
        assertEquals(-1.0, testMethodResultOrigin[0])

        val handlerSource = """
                import com.guodong.android.sword.api.kt.InvocationHandler

                class $handlerName : InvocationHandler {
                    
                    private val TAG = $handlerName::class.java.simpleName

                    override fun invoke(className: String, methodName: String, args: Array<Any?>): Any? {
                        println("${'$'}TAG.invoke: className = ${'$'}className, methodName = ${'$'}methodName, args(${'$'}{args.size}) = ${'$'}{args.joinToString()}")
                        return doubleArrayOf(1.0)
                    }
                }
            """.trimIndent()

        val handlerResult =
            swordKotlinCompilation(testName, testSource, handlerName, handlerSource).compile()
        assertEquals(KotlinCompilation.ExitCode.OK, handlerResult.exitCode)

        val testClazzSword = handlerResult.classLoader.loadClass(testName)
        val testMethodResultSword = runTest(testClazzSword) as DoubleArray
        assertEquals(1.0, testMethodResultSword[0])
    }

    @Test
    fun testFloatArrayArg() {
        val primitive = "FloatArray"
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

                    fun test() = test(floatArrayOf(-1F))
                }
            """.trimIndent()

        val testResult = testKotlinCompilation(testName, testSource).compile()
        assertEquals(KotlinCompilation.ExitCode.OK, testResult.exitCode)

        val testClazzOrigin = testResult.classLoader.loadClass(testName)
        val testMethodResultOrigin = runTest(testClazzOrigin) as FloatArray
        assertEquals(-1F, testMethodResultOrigin[0])

        val handlerSource = """
                import com.guodong.android.sword.api.kt.InvocationHandler

                class $handlerName : InvocationHandler {
                    
                    private val TAG = $handlerName::class.java.simpleName

                    override fun invoke(className: String, methodName: String, args: Array<Any?>): Any? {
                        println("${'$'}TAG.invoke: className = ${'$'}className, methodName = ${'$'}methodName, args(${'$'}{args.size}) = ${'$'}{args.joinToString()}")
                        return floatArrayOf(1F)
                    }
                }
            """.trimIndent()

        val handlerResult =
            swordKotlinCompilation(testName, testSource, handlerName, handlerSource).compile()
        assertEquals(KotlinCompilation.ExitCode.OK, handlerResult.exitCode)

        val testClazzSword = handlerResult.classLoader.loadClass(testName)
        val testMethodResultSword = runTest(testClazzSword) as FloatArray
        assertEquals(1F, testMethodResultSword[0])
    }
}