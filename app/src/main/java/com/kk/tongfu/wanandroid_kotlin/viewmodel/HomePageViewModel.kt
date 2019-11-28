package com.kk.tongfu.wanandroid_kotlin.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.kk.tongfu.wanandroid_kotlin.service.model.ArticleList
import com.kk.tongfu.wanandroid_kotlin.service.model.BannerList
import com.kk.tongfu.wanandroid_kotlin.service.repository.ProjectRepository
import com.kk.tongfu.wanandroid_kotlin.util.getNetWorkData
import com.kk.tongfu.wanandroid_kotlin.util.safeApiCall
import javax.inject.Inject

/**
 * Created by tongfu
 * on 2019-09-19
 * Desc:
 */

class HomePageViewModel @Inject constructor(
    private val repository: ProjectRepository,
    appContext: Context
) :
    BaseViewModel(appContext) {


    private val _bannerList: MutableLiveData<BannerList?> = MutableLiveData(BannerList(null))
    private val _articleList: MutableLiveData<MutableList<Any>?> = MutableLiveData()
    val articleList: MutableLiveData<MutableList<Any>?> = _articleList
    var pageNum: Int = 1


    init {
        getHomePageData()
    }


    //刷新时获取数据
    private fun getData() {

        getNetWorkData {

            pageNum = 1

            var articleList: ArticleList? = null

            safeApiCall(
                call = {
                    repository.getArticleListData(pageNum)
                },
                onEntitySuccess = {
                    articleList = it
                },
                onError = { _, _ ->
                    stateError()
                })



            safeApiCall(call = {
                repository.getTopArticleListData()
            }, onListSuccess = {
                it?.apply {
                    if (this.isNotEmpty()) {
                        for (article in this) {
                            article.top = true
                        }
                        articleList?.datas?.addAll(0, this)
                    }
                }
            })

            safeApiCall(
                call = {
                    repository.getBannerData()
                },
                onListSuccess = {
                    it?.apply {
                        val mutableList = articleList?.datas as MutableList<Any>
                        val banner = BannerList(this) as Any
                        mutableList.add(0, banner)
                    }
                })


            _articleList.value = articleList?.datas as MutableList<Any>
            stateMain()

        }
    }

    fun loadData() {

        stateLoadmore()
        getNetWorkData {
            /*val articleList = repository.getArticleListData(pageNum + 1)
            if (articleList.errorCode != HttpCode.SUCCESS) {
                stateError()
                return@getNetWorkData
            }

            if (articleList.data != null && articleList.data.datas.isNotEmpty()) {
                _articleList.value = _articleList.value?.apply {
                    addAll(articleList.data.datas)
                }
                pageNum += 1
                stateMain()
            } else {
                stateEmpty()
            }*/

            safeApiCall(call = {
                repository.getArticleListData(pageNum + 1)
            }, onEntitySuccess = {
                it?.datas?.also { it ->
                    if (it.isNotEmpty()) {
                        _articleList.value = _articleList.value?.apply {
                            addAll(it)
                        }
                    }
                }
                pageNum += 1
                stateMain()
            }, onEmpty = {
                stateEmpty()
            }, onError = { _, _ ->
                stateError()
            })
        }

    }


    fun refreshData() {
        if (!isStateRefreshing()) {
            stateRefreshing()
            getData()
        }
    }


    fun getHomePageData() {
        if (!isStateLoading()) {
            stateLoading()
            getData()
        }
    }


}
