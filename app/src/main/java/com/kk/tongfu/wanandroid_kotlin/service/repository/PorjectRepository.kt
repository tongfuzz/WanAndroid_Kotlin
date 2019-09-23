package com.kk.tongfu.wanandroid_kotlin.service.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kk.tongfu.wanandroid_kotlin.service.model.Banner
import com.kk.tongfu.wanandroid_kotlin.service.model.Response
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

    fun getBannerList():LiveData<List<Banner>>{

        val data:MutableLiveData<List<Banner>> = MutableLiveData()

        apiService.getBannerData().enqueue(object :Callback<Response<List<Banner>>>{
            override fun onFailure(call: Call<Response<List<Banner>>>?, t: Throwable?) {
                data.value=null
            }

            override fun onResponse(
                call: Call<Response<List<Banner>>>?,
                response: retrofit2.Response<Response<List<Banner>>>?
            ) {
                Log.e(TAG,Thread.currentThread().name)
                data.value=response?.body()?.data
            }

        })

        return data
    }

}