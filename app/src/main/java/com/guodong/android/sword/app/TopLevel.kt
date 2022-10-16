@file:JvmName("Top")
package com.guodong.android.sword.app

import com.guodong.android.sword.api.kt.Proxy

/**
 * Created by guodongAndroid on 2022/10/17.
 */

@Proxy(enable = true, handler = HandlerFqName.GetTextArgInvocationHandler)
fun getUser() = User("guodongAndroid-TopLevel", 28)

object TopLevel {

    @Proxy(enable = true, handler = HandlerFqName.GetTextArgInvocationHandler)
    fun getUser() = User("guodongAndroid-ObjectClass", 28)
}