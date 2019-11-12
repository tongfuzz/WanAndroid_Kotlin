package com.kk.tongfu.wanandroid_kotlin.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kk.tongfu.wanandroid_kotlin.service.HttpCode
import com.kk.tongfu.wanandroid_kotlin.service.model.BannerList
import com.kk.tongfu.wanandroid_kotlin.service.model.NetworkInfo
import com.kk.tongfu.wanandroid_kotlin.service.repository.ProjectRepository
import com.kk.tongfu.wanandroid_kotlin.util.getNetWorkData
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
    var pageNum: Int = 0

    private val _netWorkInfo = MutableLiveData<NetworkInfo>()
    val netWorkInfo: LiveData<NetworkInfo>
        get() = _netWorkInfo


    init {
        _netWorkInfo.value = repository.getNetWorkInfo()?.value
        getHomePageData()
    }


    //刷新时获取数据
    private fun getData() {

        getNetWorkData {
            pageNum = 0
            val bannerList = repository.getBannerData()
            val topArticleListData = repository.getTopArticleListData()
            val articleList = repository.getArticleListData(pageNum)
            //如果数据没有请求成功
            if (articleList.errorCode != HttpCode.SUCCESS) {
                stateError()
                return@getNetWorkData
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
        }
    }

    fun loadData() {

        stateLoadmore()
        getNetWorkData {
            val articleList = repository.getArticleListData(++pageNum)
            if (articleList.errorCode != HttpCode.SUCCESS) {
                stateError()
                return@getNetWorkData
            }

            if (articleList.data != null && articleList.data.datas.isNotEmpty()) {
                _articleList.value = _articleList.value?.apply {
                    addAll(articleList.data.datas)
                }
                stateMain()
            } else {
                stateEmpty()
            }
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
