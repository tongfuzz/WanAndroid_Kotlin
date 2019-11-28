package com.kk.tongfu.wanandroid_kotlin.service.model

/**
 * Created by tongfu
 * on 2019-09-23
 * Desc:
 */

class BaseResponse<T> constructor(val errorCode:Int, val errorMsg:String?, val data:T){

}