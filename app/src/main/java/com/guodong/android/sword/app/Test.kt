package com.guodong.android.sword.app

import com.guodong.android.sword.api.kt.Proxy

/**
 * Created by guodongAndroid on 2022/8/15.
 */
class Test {

    @Proxy(
        enable = BuildConfig.isDebug,
        handler = "com.guodong.android.sword.app.GetTextNoArgInvocationHandler"
    )
    fun getTextNoArg() = "guodongAndroid"

    @Proxy(
        enable = BuildConfig.isDebug,
        handler = "com.guodong.android.sword.app.GetTextArgInvocationHandler"
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
}