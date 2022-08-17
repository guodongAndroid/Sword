package com.guodong.android.sword.app

import android.util.Log
import com.guodong.android.sword.api.kt.InvocationHandler
import com.guodong.android.sword.api.kt.ProxyHandler

/**
 * Created by guodongAndroid on 2022/8/15.
 */
@ProxyHandler("GET_TEXT_NO_ARG")
class GetTextNoArgInvocationHandler : InvocationHandler {

    private val TAG = GetTextNoArgInvocationHandler::class.java.simpleName

    override fun invoke(className: String, methodName: String, args: Array<Any?>): Any? {
        Log.e(TAG, "invoke: className = $className, methodName = $methodName, args(${args.size}) = ${args.joinToString()}")
        return "guodongAndroid-Debug"
    }
}