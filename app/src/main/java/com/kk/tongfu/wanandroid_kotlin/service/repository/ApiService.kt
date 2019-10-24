package com.kk.tongfu.wanandroid_kotlin.service.repository

import com.kk.tongfu.wanandroid_kotlin.service.model.Article
import com.kk.tongfu.wanandroid_kotlin.service.model.ArticleList
import com.kk.tongfu.wanandroid_kotlin.service.model.Banner
import com.kk.tongfu.wanandroid_kotlin.service.model.BaseResponse
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by tongfu
 * on 2019-09-23
 * Desc:
 */

interface ApiService {

    @GET("banner/json")
    suspend fun getBannerData(): BaseResponse<MutableList<Banner>?>

    @GET("article/top/json")
    suspend fun getTopArticleListData(): BaseResponse<MutableList<Article>?>

    @GET("article/list/{pageNum}/json")
    suspend fun getArticleListData(@Path("pageNum") pageNum: Int): BaseResponse<ArticleList?>

}