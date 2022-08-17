package com.guodong.android.sword.ksp.compiler

import com.guodong.android.sword.api.kt.InvocationHandler

/**
 * Created by guodongAndroid on 2022/8/17.
 */
data class ProxyHandlerParam(
    /**
     * 参数名称, 默认为类名
     */
    val paramName: String,

    /**
     * [InvocationHandler]实现类的全限定名
     *
     * e.g. com.example.ProxyTestInvocationHandler
     */
    val fqName: String,
)
