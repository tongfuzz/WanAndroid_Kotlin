package com.kk.tongfu.wanandroid_kotlin.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kk.tongfu.wanandroid_kotlin.service.HttpCode
import com.kk.tongfu.wanandroid_kotlin.service.model.Article
import com.kk.tongfu.wanandroid_kotlin.service.repository.ProjectRepository
import com.kk.tongfu.wanandroid_kotlin.util.getNetWorkData
import javax.inject.Inject

/**
 * Created by tongfu
 * on 2019-11-13
 * Desc:
 */

class ArticleListViewModel @Inject constructor(
    val repository: ProjectRepository,
    context: Context
) : BaseViewModel(context) {

    var pageNum: Int = 1
    private val _articleList = MutableLiveData<MutableList<Article>>(mutableListOf())
    val articleList: LiveData<MutableList<Article>>
        get() = _articleList
    private var id: Int = 0

    fun setId(id: Int) {
        this.id = id
    }

    fun getArticleList() {
        if (!isStateLoading()) {
            stateLoading()
            pageNum = 1
            getListData(id, true)
        }
    }

    fun refreshData() {
        if (!isStateRefreshing()) {
            stateRefreshing()
            pageNum = 1
            getListData(id, true)
        }
    }

    private fun getListData(id: Int, isRefresh: Boolean) {
        getNetWorkData {

            var pageNumner = if (isRefresh) {
                pageNum
            } else {
                pageNum + 1
            }
            val weChatArticleListData = repository.getWeChatArticleListData(id, pageNumner)
            if (weChatArticleListData.errorCode != HttpCode.SUCCESS) {
                stateError()
                return@getNetWorkData
            }

            if (weChatArticleListData.data != null && !weChatArticleListData.data.datas.isNullOrEmpty()) {
                if (pageNumner == 1) {
                    _articleList.value = weChatArticleListData.data.datas
                } else {
                    _articleList.value = _articleList.value.apply {
                        this?.addAll(weChatArticleListData.data.datas)
                    }
                }
                if (!isRefresh) {
                    pageNum += 1
                }
                stateMain()
            } else {
                stateEmpty()
            }
        }
    }


    fun getMoreData() {
        if (!isStateLoadmore()) {
            stateLoadmore()
            getListData(id, false)
        }
    }


}