package com.kk.tongfu.wanandroid_kotlin.service.model

/**
 * Created by tongfu
 * on 2019-11-21
 * Desc:
 */

sealed class Response<out T : Any>

data class Success<T : Any>(val result: T?) : Response<T>()

data class Error(val code: Int, val message: String?) : Response<Nothing>()

data class Empty<T:Any>(val result: T?) : Response<T>()

