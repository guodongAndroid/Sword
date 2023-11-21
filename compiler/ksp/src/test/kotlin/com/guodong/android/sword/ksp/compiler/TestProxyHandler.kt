package com.guodong.android.sword.ksp.compiler

import com.tschuchort.compiletesting.KotlinCompilation
import org.junit.Test
import kotlin.test.assertEquals

/**
 * Created by guodongAndroid on 2023/11/21.
 */
class TestProxyHandler {

    companion object {
        private const val DEFAULT_PACKAGE_NAME = "com.guodong.android.sword"
        private const val DEFAULT_HANDLER_NAME = "HandlerFqName"
        private const val DEFAULT_HANDLER_FQ_NAME = "${DEFAULT_PACKAGE_NAME}.${DEFAULT_HANDLER_NAME}"
    }

    @Test
    fun testHandler() {
        val handlerName = "TestNoArgInvocationHandler"
        val handlerSource = """
                import com.guodong.android.sword.api.kt.InvocationHandler
                import com.guodong.android.sword.api.kt.ProxyHandler

                @ProxyHandler
                class $handlerName : InvocationHandler {
                    
                    private val TAG = $handlerName::class.java.simpleName

                    override fun invoke(className: String, methodName: String, args: Array<Any?>): Any? {
                        println("${'$'}TAG.invoke: className = ${'$'}className, methodName = ${'$'}methodName, args(${'$'}{args.size}) = ${'$'}{args.joinToString()}")
                        println("I'm test method by sword.")
                        return Unit
                    }
                }
            """.trimIndent()

        val result = compile(handlerName, handlerSource)
        assertEquals(KotlinCompilation.ExitCode.OK, result.exitCode)

        val clazz = result.classLoader.loadClass(DEFAULT_HANDLER_FQ_NAME)
        val instance = clazz.getDeclaredField("INSTANCE").get(null)
        val field = clazz.getDeclaredField(handlerName)
        val handlerFqName = field.get(instance)
        assertEquals(handlerName, handlerFqName)
    }

    @Test
    fun testHandlerParam() {
        val handlerName = "TestNoArgInvocationHandler"
        val handlerParamName = "NoArgHandler"
        val handlerSource = """
                import com.guodong.android.sword.api.kt.InvocationHandler
                import com.guodong.android.sword.api.kt.ProxyHandler

                @ProxyHandler("$handlerParamName")
                class $handlerName : InvocationHandler {
                    
                    private val TAG = $handlerName::class.java.simpleName

                    override fun invoke(className: String, methodName: String, args: Array<Any?>): Any? {
                        println("${'$'}TAG.invoke: className = ${'$'}className, methodName = ${'$'}methodName, args(${'$'}{args.size}) = ${'$'}{args.joinToString()}")
                        println("I'm test method by sword.")
                        return Unit
                    }
                }
            """.trimIndent()

        val result = compile(handlerName, handlerSource)
        assertEquals(KotlinCompilation.ExitCode.OK, result.exitCode)

        val clazz = result.classLoader.loadClass(DEFAULT_HANDLER_FQ_NAME)
        val instance = clazz.getDeclaredField("INSTANCE").get(null)
        val field = clazz.getDeclaredField(handlerParamName)
        val handlerFqName = field.get(instance)
        assertEquals(handlerName, handlerFqName)
    }

    @Test
    fun testHandlerPackageName() {
        val handlerPackageName = "com.guodong.android.sword.test"
        val handlerName = "TestNoArgInvocationHandler"
        val handlerParamName = "NoArgHandler"
        val handlerSource = """
                import com.guodong.android.sword.api.kt.InvocationHandler
                import com.guodong.android.sword.api.kt.ProxyHandler

                @ProxyHandler("$handlerParamName")
                class $handlerName : InvocationHandler {
                    
                    private val TAG = $handlerName::class.java.simpleName

                    override fun invoke(className: String, methodName: String, args: Array<Any?>): Any? {
                        println("${'$'}TAG.invoke: className = ${'$'}className, methodName = ${'$'}methodName, args(${'$'}{args.size}) = ${'$'}{args.joinToString()}")
                        println("I'm test method by sword.")
                        return Unit
                    }
                }

                @ProxyHandler("${handlerParamName}Candidate")
                class ${handlerName}Candidate : InvocationHandler {
                    
                    private val TAG = ${handlerName}Candidate::class.java.simpleName

                    override fun invoke(className: String, methodName: String, args: Array<Any?>): Any? {
                        println("${'$'}TAG.invoke: className = ${'$'}className, methodName = ${'$'}methodName, args(${'$'}{args.size}) = ${'$'}{args.joinToString()}")
                        println("I'm test method by sword.")
                        return Unit
                    }
                }
            """.trimIndent()

        val result = compile(handlerName, handlerSource, mutableMapOf("sword.pkg" to handlerPackageName))
        assertEquals(KotlinCompilation.ExitCode.OK, result.exitCode)

        // com.guodong.android.sword.test.HandlerFqName
        val candidateHandlerFqName = "${handlerPackageName}.${DEFAULT_HANDLER_NAME}"
        val clazz = result.classLoader.loadClass(candidateHandlerFqName)
        val instance = clazz.getDeclaredField("INSTANCE").get(null)

        val field = clazz.getDeclaredField(handlerParamName)
        val handlerFqName = field.get(instance)
        assertEquals(handlerName, handlerFqName)

        val fieldCandidate = clazz.getDeclaredField("${handlerParamName}Candidate")
        val handlerFqNameCandidate = fieldCandidate.get(instance)
        assertEquals("${handlerName}Candidate", handlerFqNameCandidate)
    }

}