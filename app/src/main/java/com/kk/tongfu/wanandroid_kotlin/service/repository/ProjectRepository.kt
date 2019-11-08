package com.kk.tongfu.wanandroid_kotlin.service.repository

import com.kk.tongfu.wanandroid_kotlin.MainApplication
import com.kk.tongfu.wanandroid_kotlin.service.dao.DaoService
import com.kk.tongfu.wanandroid_kotlin.service.model.*

/**
 * Created by tongfu
 * on 2019-09-23
 * Desc:
 */

class ProjectRepository constructor(private val apiService: ApiService) : ApiService, DaoService {

    override suspend fun getNetWorkInfo(): NetworkInfo? {
        return MainApplication.database?.daoService()?.getNetWorkInfo()
    }

    override suspend fun insert(vararg networkInfos: NetworkInfo) {
        MainApplication.database?.daoService()?.insert(*networkInfos)
    }

    override suspend fun getSystemListData(): BaseResponse<MutableList<Chapter>?> {
        return apiService.getSystemListData()
    }

    override suspend fun getBannerData(): BaseResponse<MutableList<Banner>?> {
        return apiService.getBannerData()
    }

    override suspend fun getTopArticleListData(): BaseResponse<MutableList<Article>?> {
        return apiService.getTopArticleListData()
    }

    override suspend fun getArticleListData(pageNum: Int): BaseResponse<ArticleList?> {
        return apiService.getArticleListData(pageNum)
    }

}