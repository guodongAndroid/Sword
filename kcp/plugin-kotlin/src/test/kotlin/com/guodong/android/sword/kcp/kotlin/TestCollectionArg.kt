package com.guodong.android.sword.kcp.kotlin

import com.tschuchort.compiletesting.KotlinCompilation
import org.junit.Test
import kotlin.test.assertEquals

/**
 * Created by guodongAndroid on 2023/11/20.
 */
class TestCollectionArg {

    @Test
    fun testListArg() {
        val primitive = "List"
        val testName = "Test${primitive}Arg"
        val handlerName = "Test${primitive}ArgInvocationHandler"

        val testSource = """
                import com.guodong.android.sword.api.kt.Proxy

                class $testName {
                
                    @Proxy(
                        enable = true,
                        handler = "$handlerName"
                    )
                    fun test(s: $primitive<Int>): $primitive<Int> {
                        return s
                    }

                    fun test() = test(listOf(-1))
                }
            """.trimIndent()

        val testResult = testKotlinCompilation(testName, testSource).compile()
        assertEquals(KotlinCompilation.ExitCode.OK, testResult.exitCode)

        val testClazzOrigin = testResult.classLoader.loadClass(testName)
        val testMethodResultOrigin = runTest(testClazzOrigin) as List<*>
        assertEquals(-1, testMethodResultOrigin[0])

        val handlerSource = """
                import com.guodong.android.sword.api.kt.InvocationHandler

                class $handlerName : InvocationHandler {
                    
                    private val TAG = $handlerName::class.java.simpleName

                    override fun invoke(className: String, methodName: String, args: Array<Any?>): Any? {
                        println("${'$'}TAG.invoke: className = ${'$'}className, methodName = ${'$'}methodName, args(${'$'}{args.size}) = ${'$'}{args.joinToString()}")
                        return listOf(1)
                    }
                }
            """.trimIndent()

        val handlerResult =
            swordKotlinCompilation(testName, testSource, handlerName, handlerSource).compile()
        assertEquals(KotlinCompilation.ExitCode.OK, handlerResult.exitCode)

        val testClazzSword = handlerResult.classLoader.loadClass(testName)
        val testMethodResultSword = runTest(testClazzSword) as List<*>
        assertEquals(1, testMethodResultSword[0])
    }

    @Test
    fun testMutableListArg() {
        val primitive = "MutableList"
        val testName = "Test${primitive}Arg"
        val handlerName = "Test${primitive}ArgInvocationHandler"

        val testSource = """
                import com.guodong.android.sword.api.kt.Proxy

                class $testName {
                
                    @Proxy(
                        enable = true,
                        handler = "$handlerName"
                    )
                    fun test(s: $primitive<Int>): $primitive<Int> {
                        return s
                    }

                    fun test() = test(mutableListOf(-1))
                }
            """.trimIndent()

        val testResult = testKotlinCompilation(testName, testSource).compile()
        assertEquals(KotlinCompilation.ExitCode.OK, testResult.exitCode)

        val testClazzOrigin = testResult.classLoader.loadClass(testName)
        val testMethodResultOrigin = runTest(testClazzOrigin) as MutableList<*>
        assertEquals(-1, testMethodResultOrigin[0])

        val handlerSource = """
                import com.guodong.android.sword.api.kt.InvocationHandler

                class $handlerName : InvocationHandler {
                    
                    private val TAG = $handlerName::class.java.simpleName

                    override fun invoke(className: String, methodName: String, args: Array<Any?>): Any? {
                        println("${'$'}TAG.invoke: className = ${'$'}className, methodName = ${'$'}methodName, args(${'$'}{args.size}) = ${'$'}{args.joinToString()}")
                        return mutableListOf(1)
                    }
                }
            """.trimIndent()

        val handlerResult =
            swordKotlinCompilation(testName, testSource, handlerName, handlerSource).compile()
        assertEquals(KotlinCompilation.ExitCode.OK, handlerResult.exitCode)

        val testClazzSword = handlerResult.classLoader.loadClass(testName)
        val testMethodResultSword = runTest(testClazzSword) as MutableList<*>
        assertEquals(1, testMethodResultSword[0])
    }

    @Test
    fun testSetArg() {
        val primitive = "Set"
        val testName = "Test${primitive}Arg"
        val handlerName = "Test${primitive}ArgInvocationHandler"

        val testSource = """
                import com.guodong.android.sword.api.kt.Proxy

                class $testName {
                
                    @Proxy(
                        enable = true,
                        handler = "$handlerName"
                    )
                    fun test(s: $primitive<Int>): $primitive<Int> {
                        return s
                    }

                    fun test() = test(setOf(-1))
                }
            """.trimIndent()

        val testResult = testKotlinCompilation(testName, testSource).compile()
        assertEquals(KotlinCompilation.ExitCode.OK, testResult.exitCode)

        val testClazzOrigin = testResult.classLoader.loadClass(testName)
        val testMethodResultOrigin = runTest(testClazzOrigin) as Set<*>
        assertEquals(true, testMethodResultOrigin.contains(-1))

        val handlerSource = """
                import com.guodong.android.sword.api.kt.InvocationHandler

                class $handlerName : InvocationHandler {
                    
                    private val TAG = $handlerName::class.java.simpleName

                    override fun invoke(className: String, methodName: String, args: Array<Any?>): Any? {
                        println("${'$'}TAG.invoke: className = ${'$'}className, methodName = ${'$'}methodName, args(${'$'}{args.size}) = ${'$'}{args.joinToString()}")
                        return setOf(1)
                    }
                }
            """.trimIndent()

        val handlerResult =
            swordKotlinCompilation(testName, testSource, handlerName, handlerSource).compile()
        assertEquals(KotlinCompilation.ExitCode.OK, handlerResult.exitCode)

        val testClazzSword = handlerResult.classLoader.loadClass(testName)
        val testMethodResultSword = runTest(testClazzSword) as Set<*>
        assertEquals(true, testMethodResultSword.contains(1))
    }

    @Test
    fun testMutableSetArg() {
        val primitive = "MutableSet"
        val testName = "Test${primitive}Arg"
        val handlerName = "Test${primitive}ArgInvocationHandler"

        val testSource = """
                import com.guodong.android.sword.api.kt.Proxy

                class $testName {
                
                    @Proxy(
                        enable = true,
                        handler = "$handlerName"
                    )
                    fun test(s: $primitive<Int>): $primitive<Int> {
                        return s
                    }

                    fun test() = test(mutableSetOf(-1))
                }
            """.trimIndent()

        val testResult = testKotlinCompilation(testName, testSource).compile()
        assertEquals(KotlinCompilation.ExitCode.OK, testResult.exitCode)

        val testClazzOrigin = testResult.classLoader.loadClass(testName)
        val testMethodResultOrigin = runTest(testClazzOrigin) as MutableSet<*>
        assertEquals(true, testMethodResultOrigin.contains(-1))

        val handlerSource = """
                import com.guodong.android.sword.api.kt.InvocationHandler

                class $handlerName : InvocationHandler {
                    
                    private val TAG = $handlerName::class.java.simpleName

                    override fun invoke(className: String, methodName: String, args: Array<Any?>): Any? {
                        println("${'$'}TAG.invoke: className = ${'$'}className, methodName = ${'$'}methodName, args(${'$'}{args.size}) = ${'$'}{args.joinToString()}")
                        return mutableSetOf(1)
                    }
                }
            """.trimIndent()

        val handlerResult =
            swordKotlinCompilation(testName, testSource, handlerName, handlerSource).compile()
        assertEquals(KotlinCompilation.ExitCode.OK, handlerResult.exitCode)

        val testClazzSword = handlerResult.classLoader.loadClass(testName)
        val testMethodResultSword = runTest(testClazzSword) as MutableSet<*>
        assertEquals(true, testMethodResultSword.contains(1))
    }

    @Test
    fun testMapArg() {
        val primitive = "Map"
        val testName = "Test${primitive}Arg"
        val handlerName = "Test${primitive}ArgInvocationHandler"

        val testSource = """
                import com.guodong.android.sword.api.kt.Proxy

                class $testName {
                
                    @Proxy(
                        enable = true,
                        handler = "$handlerName"
                    )
                    fun test(s: $primitive<String, Int>): $primitive<String, Int> {
                        return s
                    }

                    fun test() = test(mapOf("key" to -1))
                }
            """.trimIndent()

        val testResult = testKotlinCompilation(testName, testSource).compile()
        assertEquals(KotlinCompilation.ExitCode.OK, testResult.exitCode)

        val testClazzOrigin = testResult.classLoader.loadClass(testName)
        val testMethodResultOrigin = runTest(testClazzOrigin) as Map<*, *>
        assertEquals(-1, testMethodResultOrigin["key"])

        val handlerSource = """
                import com.guodong.android.sword.api.kt.InvocationHandler

                class $handlerName : InvocationHandler {
                    
                    private val TAG = $handlerName::class.java.simpleName

                    override fun invoke(className: String, methodName: String, args: Array<Any?>): Any? {
                        println("${'$'}TAG.invoke: className = ${'$'}className, methodName = ${'$'}methodName, args(${'$'}{args.size}) = ${'$'}{args.joinToString()}")
                        return mapOf("key" to 1)
                    }
                }
            """.trimIndent()

        val handlerResult =
            swordKotlinCompilation(testName, testSource, handlerName, handlerSource).compile()
        assertEquals(KotlinCompilation.ExitCode.OK, handlerResult.exitCode)

        val testClazzSword = handlerResult.classLoader.loadClass(testName)
        val testMethodResultSword = runTest(testClazzSword) as Map<*, *>
        assertEquals(1, testMethodResultSword["key"])
    }

    @Test
    fun testMutableMapArg() {
        val primitive = "MutableMap"
        val testName = "Test${primitive}Arg"
        val handlerName = "Test${primitive}ArgInvocationHandler"

        val testSource = """
                import com.guodong.android.sword.api.kt.Proxy

                class $testName {
                
                    @Proxy(
                        enable = true,
                        handler = "$handlerName"
                    )
                    fun test(s: $primitive<String, Int>): $primitive<String, Int> {
                        return s
                    }

                    fun test() = test(mutableMapOf("key" to -1))
                }
            """.trimIndent()

        val testResult = testKotlinCompilation(testName, testSource).compile()
        assertEquals(KotlinCompilation.ExitCode.OK, testResult.exitCode)

        val testClazzOrigin = testResult.classLoader.loadClass(testName)
        val testMethodResultOrigin = runTest(testClazzOrigin) as MutableMap<*, *>
        assertEquals(-1, testMethodResultOrigin["key"])

        val handlerSource = """
                import com.guodong.android.sword.api.kt.InvocationHandler

                class $handlerName : InvocationHandler {
                    
                    private val TAG = $handlerName::class.java.simpleName

                    override fun invoke(className: String, methodName: String, args: Array<Any?>): Any? {
                        println("${'$'}TAG.invoke: className = ${'$'}className, methodName = ${'$'}methodName, args(${'$'}{args.size}) = ${'$'}{args.joinToString()}")
                        return mutableMapOf("key" to 1)
                    }
                }
            """.trimIndent()

        val handlerResult =
            swordKotlinCompilation(testName, testSource, handlerName, handlerSource).compile()
        assertEquals(KotlinCompilation.ExitCode.OK, handlerResult.exitCode)

        val testClazzSword = handlerResult.classLoader.loadClass(testName)
        val testMethodResultSword = runTest(testClazzSword) as MutableMap<*, *>
        assertEquals(1, testMethodResultSword["key"])
    }
}