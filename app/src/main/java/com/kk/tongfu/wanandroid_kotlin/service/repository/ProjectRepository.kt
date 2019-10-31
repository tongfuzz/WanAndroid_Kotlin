package com.kk.tongfu.wanandroid_kotlin.service.repository

import com.kk.tongfu.wanandroid_kotlin.service.model.Article
import com.kk.tongfu.wanandroid_kotlin.service.model.ArticleList
import com.kk.tongfu.wanandroid_kotlin.service.model.Banner
import com.kk.tongfu.wanandroid_kotlin.service.model.BaseResponse
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by tongfu
 * on 2019-09-23
 * Desc:
 */

class ProjectRepository constructor(private val apiService: ApiService) {

    suspend fun getBannerData(): BaseResponse<MutableList<Banner>?> {
        return apiService.getBannerData()
    }

    suspend fun getTopArticleListData(): BaseResponse<MutableList<Article>?> {
        return apiService.getTopArticleListData()
    }

    suspend fun getArticleListData(pageNum: Int): BaseResponse<ArticleList?> {
        return apiService.getArticleListData(pageNum)
    }

}