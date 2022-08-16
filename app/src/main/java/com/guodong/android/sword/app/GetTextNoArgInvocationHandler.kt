package com.guodong.android.sword.app

import android.util.Log
import com.guodong.android.sword.api.kt.InvocationHandler

/**
 * Created by guodongAndroid on 2022/8/15.
 */
class GetTextNoArgInvocationHandler : InvocationHandler {

    private val TAG = GetTextNoArgInvocationHandler::class.java.simpleName

    override fun invoke(className: String, methodName: String, args: Array<Any?>): Any? {
        Log.e(TAG, "invoke: className = $className, methodName = $methodName, args(${args.size}) = ${args.joinToString()}")
        return "guodongAndroid-Debug"
    }
}