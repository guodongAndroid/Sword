package com.guodong.android.sword.kcp.kotlin

import org.jetbrains.kotlin.name.ClassId
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.name.FqNameUnsafe
import org.jetbrains.kotlin.name.Name

/**
 * Created by guodongAndroid on 2023/12/20.
 */
object SwordNames {

    const val PROXY_NAME = "com.guodong.android.sword.api.kt.Proxy"
    const val PROXY_ENABLE_VALUE_ARGUMENT = "enable"
    const val PROXY_HANDLER_VALUE_ARGUMENT = "handler"

    val PROXY_FQ_NAME = FqName(PROXY_NAME)
    val PROXY_CLASS_ID = ClassId.topLevel(PROXY_FQ_NAME)

    val PROXY_ENABLE_VALUE_ARGUMENT_NAME = Name.identifier(PROXY_ENABLE_VALUE_ARGUMENT)
    val PROXY_HANDLER_VALUE_ARGUMENT_NAME = Name.identifier(PROXY_HANDLER_VALUE_ARGUMENT)
}