package com.guodong.android.sword.app

/**
 * Created by guodongAndroid on 2022/8/15.
 */
interface Callback {

    fun onSuccess()

    fun onFailure(cause: Throwable)
}