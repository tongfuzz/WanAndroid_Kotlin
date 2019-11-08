package com.kk.tongfu.wanandroid_kotlin.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kk.tongfu.wanandroid_kotlin.service.HttpCode
import com.kk.tongfu.wanandroid_kotlin.service.LoadState
import com.kk.tongfu.wanandroid_kotlin.service.RefreshState
import com.kk.tongfu.wanandroid_kotlin.service.model.BannerList
import com.kk.tongfu.wanandroid_kotlin.service.repository.ApiService
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by tongfu
 * on 2019-09-19
 * Desc:
 */

class HomePageViewModel @Inject constructor(private val apiService: ApiService) :
    BaseViewModel() {


    private val _bannerList: MutableLiveData<BannerList?> = MutableLiveData(BannerList(null))

    private val _articleList: MutableLiveData<MutableList<Any>?> = MutableLiveData()
    val articleList: MutableLiveData<MutableList<Any>?> = _articleList
    var pageNum: Int = 0

    init {
        getHomePageData()
    }


    //刷新时获取数据
    private fun getData() {
        viewModelScope.launch {

            pageNum = 0

            try {
                val bannerList = apiService.getBannerData()
                val topArticleListData = apiService.getTopArticleListData()
                val articleList = apiService.getArticleListData(pageNum)
                //如果数据没有请求成功
                if (articleList.errorCode != HttpCode.SUCCESS) {
                    stateError()
                    return@launch
                }

                //如果数据请求成功
                val data = topArticleListData.data
                if (topArticleListData.errorCode == HttpCode.SUCCESS && data != null && data.isNotEmpty()) {
                    for (article in data) {
                        article.top = true
                    }

                    if (articleList.data != null) {
                        articleList.data.datas.addAll(0, data)
                    }
                }


                //设置数据
                _bannerList.value?.data = bannerList.data
                if (bannerList.errorCode == HttpCode.SUCCESS && bannerList.data != null && bannerList.data.isNotEmpty()) {
                    (articleList.data?.datas as MutableList<Any>).add(0, _bannerList.value as Any)
                }

                val mutableList = articleList.data?.datas as MutableList<Any>

                _articleList.value = mutableList
                stateMain()
            } catch (e: Exception) {
                println(e.message)

                //stateError()
            }
        }
    }

    fun loadData() {

        baseRefreshState.value = RefreshState.LOADING
        viewModelScope.launch {
            try {
                val articleList = apiService.getArticleListData(++pageNum)
                if (articleList.errorCode != HttpCode.SUCCESS) {
                    stateError()
                    return@launch
                }

                if(pageNum==3){
                    stateEmpty()
                    return@launch
                }

                if (articleList.data != null && articleList.data.datas.isNotEmpty()) {
                    _articleList.value = _articleList.value?.apply {
                        addAll(articleList.data.datas)
                    }
                    stateMain()
                } else {
                    stateEmpty()
                }
            } catch (e: Exception) {
                println(e.message)
                /*baseLoadState.value = LoadState.NO_NETWORK
                baseRefreshState.value = RefreshState.LOADING_ERROR*/
            }
        }

    }


    fun refreshData() {
        if(baseRefreshState.value!=RefreshState.REFRESHING) {
            baseRefreshState.value = RefreshState.REFRESHING
            getData()
        }
    }


    fun getHomePageData() {
        if (baseLoadState.value != LoadState.LOADING) {
            baseLoadState.value = LoadState.LOADING
            getData()
        }
    }

}
