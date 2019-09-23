package com.kk.tongfu.wanandroid_kotlin.service.repository

import android.telecom.Call
import com.kk.tongfu.wanandroid_kotlin.service.model.Banner
import com.kk.tongfu.wanandroid_kotlin.service.model.Response
import retrofit2.http.GET

/**
 * Created by tongfu
 * on 2019-09-23
 * Desc:
 */

interface ApiService {

    @GET("banner/json")
    fun getBannerData(): retrofit2.Call<Response<List<Banner>>>

}