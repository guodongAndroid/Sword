package com.guodong.android.sword.kcp.kotlin

/**
 * Created by guodongAndroid on 2022/08/15.
 */
internal data class SwordParam(
    /**
     * 是否有[Debug]注解
     */
    var hasDebugAnnotation: Boolean = false,

    /**
     * 是否启用, 默认True
     */
    var enable: Boolean = true,

    /**
     * [InvocationHandler]实现全限定名, 实现类必须有无参构造方法
     *
     * e.g. com.example.DebugTestInvocationHandler
     */
    var handler: String = ""
) {
    companion object {
        internal const val PARAM_ENABLE = "enable"
        internal const val PARAM_HANDLER = "handler"
    }
}