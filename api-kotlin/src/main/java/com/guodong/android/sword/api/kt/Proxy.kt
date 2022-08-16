package com.guodong.android.sword.api.kt

/**
 * Created by guodongAndroid on 2022/08/15.
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.BINARY)
annotation class Proxy(
    /**
     * 是否启用, 默认True
     */
    val enable: Boolean = true,

    /**
     * [InvocationHandler]实现类的全限定名, 实现类必须有无参构造方法
     *
     * e.g. com.example.ProxyTestInvocationHandler
     */
    val handler: String = "",
)