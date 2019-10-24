package com.kk.tongfu.wanandroid_kotlin.service.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kk.tongfu.wanandroid_kotlin.service.model.Article
import com.kk.tongfu.wanandroid_kotlin.service.model.Banner
import com.kk.tongfu.wanandroid_kotlin.service.model.BaseResponse
import com.kk.tongfu.wanandroid_kotlin.util.dataConvert
import retrofit2.Call
import retrofit2.Callback
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by tongfu
 * on 2019-09-23
 * Desc:
 */

@Singleton
class PorjectRepository @Inject constructor(private val apiService: ApiService) {

    val TAG:String="PorjectRepository"

    suspend fun getBannerList():LiveData<List<Banner>>{
        val data:MutableLiveData<List<Banner>> = MutableLiveData()
       /* apiService.getBannerData().enqueue(object :Callback<BaseResponse<List<Banner>>>{
            override fun onFailure(call: Call<BaseResponse<List<Banner>>>?, t: Throwable?) {
                data.value=null
            }

            override fun onResponse(
                call: Call<BaseResponse<List<Banner>>>?,
                response: retrofit2.Response<BaseResponse<List<Banner>>>?
            ) {
                Log.e(TAG,Thread.currentThread().name)
                data.value=response?.body()?.data
            }

        })*/

        data.value=apiService.getBannerData().dataConvert()

        return data
    }

    suspend fun getTopArticleList():LiveData<List<Article>>{
        val data:MutableLiveData<List<Article>> = MutableLiveData()
        /*apiService.getTopArticleListData().enqueue(object :Callback<BaseResponse<List<Article>>>{
            override fun onFailure(call: Call<BaseResponse<List<Article>>>?, t: Throwable?) {
                data.value=null
            }

            override fun onResponse(
                call: Call<BaseResponse<List<Article>>>?,
                response: retrofit2.Response<BaseResponse<List<Article>>>?
            ) {
                data.value=response?.body()?.data
            }

        })*/
        data.value=apiService.getTopArticleListData().dataConvert()
        return data
    }




}