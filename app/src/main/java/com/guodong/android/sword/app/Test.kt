package com.guodong.android.sword.app

import android.util.Log
import com.guodong.android.sword.api.kt.Proxy

/**
 * Created by guodongAndroid on 2022/8/15.
 */
class Test {

    @Proxy(
        enable = true,
        handler = HandlerFqNames.GET_TEXT_NO_ARG
    )
    fun getTextNoArg() = "guodongAndroid"

    @Proxy(
        enable = true,
        handler = HandlerFqNames.GetTextArgInvocationHandler
    )
    fun getTextArg(
        b: Byte,
        z: Boolean,
        c: Char,
        s: Short,
        i: Int,
        l: Long,
        f: Float,
        d: Double,
        str: String,
        ia: IntArray,
        sa: Array<String>,
        ls: List<String>,
        lls: List<List<String>>,
        map: Map<String, String>,
        user: User,
        callback: Callback,
    ): User {
        return User("guodongAndroid-Release", 28)
    }

    @Proxy(
        enable = true,
        handler = HandlerFqNames.GetTextArgInvocationHandler
    )
    fun testHandler(
        b: Byte,
        z: Boolean,
        c: Char,
        s: Short,
    ): User {
        return User("guodongAndroid-Release", 28)
    }

    @Proxy(
        enable = true,
        handler = HandlerFqNames.TEST_INT
    )
    fun testInt(): Int {
        return 1
    }

    inner class TestInner {

        private val TAG = TestInner::class.java.simpleName

        @Proxy(enable = true, handler = HandlerFqNames.GetTextArgInvocationHandler)
        fun getUser() = User("guodongAndroid-Inner", 28)

        fun anonymousFun() {
            Runnable {

                @Proxy(enable = true, handler = HandlerFqNames.GetTextArgInvocationHandler)
                fun getUser() = User("guodongAndroid-Anonymous", 28)

                val user = getUser()
                Log.e(TAG, "anonymousFun: user = $user")

            }.run()
        }
    }

    class TestStatic {
        @Proxy(enable = true, handler = HandlerFqNames.GetTextArgInvocationHandler)
        fun getUser() = User("guodongAndroid-StaticInnerClass", 28)
    }
}