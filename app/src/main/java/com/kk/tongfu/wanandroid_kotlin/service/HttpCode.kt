package com.kk.tongfu.wanandroid_kotlin.service

/**
 * Created by tongfu
 * on 2019-09-27
 * Desc:
 */

class HttpCode {

    companion object{
        /**
         * 成功
         */
        val SUCCESS = 0

        /**
         * 未知错误
         */
        val ERROR_UNKNOWN = 1000

        /**
         * HTTP错误
         */
        val ERROR_HTTP = 1001

        /**
         * 网络错误
         */
        val ERROR_NETWORK = 1002

        /**
         * 解析错误
         */
        val ERROR_PARSE = 1003

        /**
         * SSL错误
         */
        val ERROR_SSL = 1004

        /**
         * 登录失效，需要重新登录
         */
        val ERROR_LOGIN_INVALID = -1001
    }


}