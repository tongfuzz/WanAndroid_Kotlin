package com.kk.tongfu.wanandroid_kotlin.service.repository

import com.kk.tongfu.wanandroid_kotlin.service.HttpCode
import com.kk.tongfu.wanandroid_kotlin.service.model.BaseResponse

/**
 * Created by tongfu
 * on 2019-11-21
 * Desc:
 */

open class BaseRepository {
    suspend fun <T : Any?> safeApiCall(
        call: suspend () -> BaseResponse<T>,
        onSuccess: (data:T) -> Unit = {},
        onEmpty: () -> Unit = {},
        onError: (code: Int, message: String?) -> Unit = { i: Int, s: String? -> }
    ) {
        try {
            val response = call.invoke()
            if (response.errorCode == HttpCode.SUCCESS) {
                //网络请求成功
                if (response.data != null) {
                    onSuccess(response.data)
                } else {
                    onEmpty()
                }
            } else {
                onError(response.errorCode, response.errorMsg)
            }
        } catch (e: Exception) {
            onError(HttpCode.ERROR_NETWORK, e.message)
        }
    }


}