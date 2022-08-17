package com.guodong.android.sword.api.kt

/**
 * 标记[InvocationHandler]的子类, 为其生成FqName索引类`HandlerFqName`
 *
 * 使用 ksp 实现
 *
 * Created by guodongAndroid on 2022/8/17.
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.BINARY)
annotation class ProxyHandler(
    /**
     * 生成字段的名称, 默认为类名
     *
     * `paramName`参数名称不要随意变动
     */
    val paramName: String = ""
)