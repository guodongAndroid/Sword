package com.guodong.android.sword.api.kt

/**
 * Created by guodongAndroid on 2022/8/15.
 */
interface InvocationHandler {

    fun invoke(className: String, methodName: String, args: Array<Any?>): Any?
}