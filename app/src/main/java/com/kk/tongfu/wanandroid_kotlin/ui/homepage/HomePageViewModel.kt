package com.kk.tongfu.wanandroid_kotlin.ui.homepage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kk.tongfu.wanandroid_kotlin.service.HttpCode
import com.kk.tongfu.wanandroid_kotlin.service.LoadState
import com.kk.tongfu.wanandroid_kotlin.service.RefreshState
import com.kk.tongfu.wanandroid_kotlin.service.model.Article
import com.kk.tongfu.wanandroid_kotlin.service.model.Banner
import com.kk.tongfu.wanandroid_kotlin.service.model.HomepageData
import com.kk.tongfu.wanandroid_kotlin.service.repository.ApiService
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by tongfu
 * on 2019-09-19
 * Desc:
 */

class HomePageViewModel @Inject constructor(private val apiService: ApiService) :
    ViewModel() {


    private val _bannerList: MutableLiveData<MutableList<Banner>?> = MutableLiveData()
    val bannerList: LiveData<MutableList<Banner>?>
        get() = _bannerList

    private val _articleList: MutableLiveData<MutableList<Article>?> = MutableLiveData()
    val articleList: LiveData<MutableList<Article>?>
        get() = _articleList

    private val _homepageData: MutableLiveData<HomepageData> =
        MutableLiveData(HomepageData(bannerList = _bannerList, articleList = _articleList))
    val homePageData: LiveData<HomepageData>
        get() = _homepageData

    private val _loadState: MutableLiveData<LoadState> = MutableLiveData(LoadState.SUCCESS)
    val loadState: MutableLiveData<LoadState>
        get() = _loadState

    private val _refreshState: MutableLiveData<RefreshState> =
        MutableLiveData(RefreshState.REFRESHING_SUCCESS)
    val refreshState: LiveData<RefreshState>
        get() = _refreshState

    val hasMore: MutableLiveData<Boolean> = MutableLiveData(true)
    var pageNum: Int = 0

    init {
        _loadState.value=LoadState.LOADING
    }

    //刷新时获取数据
    fun loadData() {
        viewModelScope.launch {

            pageNum = 0
            hasMore.value = true

            try {
                val bannerList = apiService.getBannerData()
                val topArticleListData = apiService.getTopArticleListData()
                val articleList = apiService.getArticleListData(pageNum)
                //如果数据没有请求成功
                if (articleList.errorCode != HttpCode.SUCCESS) {
                    if (_refreshState.value == RefreshState.REFRESHING) {
                        //说明是刷新出现了问题
                        _refreshState.value = RefreshState.REFRESHING_ERROR
                        return@launch
                    }
                    //说明初次加载出现了问题
                    _loadState.value = LoadState.ERROR
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
                _bannerList.value = bannerList.data
                _articleList.value = articleList.data?.datas
                //更新加载状态
                if (_loadState.value == LoadState.LOADING) {
                    //如果是初次加载，设置初次加载成功
                    _loadState.value = LoadState.SUCCESS
                } else {
                    //如果是刷新加载，设置刷新加载成功
                    _refreshState.value = RefreshState.REFRESHING_SUCCESS
                }

                println("homepageviewmodel   pagenum:$pageNum")

            } catch (e: Exception) {
                println(e.message)
                _loadState.value = LoadState.NO_NETWORK
                _refreshState.value = RefreshState.REFRESHING_ERROR
            }
        }
    }

    fun loadMoreData() {

        _refreshState.value = RefreshState.LOADING
        viewModelScope.launch {
            try {
                val articleList = apiService.getArticleListData(++pageNum)
                if (articleList.errorCode != HttpCode.SUCCESS) {
                    _refreshState.value = RefreshState.LOADING_ERROR
                    return@launch
                }

                if (articleList.data != null && articleList.data.datas.isNotEmpty()) {
                    _articleList.value = _articleList.value?.apply {
                        addAll(articleList.data.datas)
                    }
                    _refreshState.value = RefreshState.LOADING_SUCCESS
                } else {
                    _refreshState.value = RefreshState.LOADING_ERROR
                    hasMore.value = false
                }
            } catch (e: Exception) {
                println(e.message)
                _loadState.value = LoadState.NO_NETWORK
                _refreshState.value = RefreshState.LOADING_ERROR
            }
        }

    }


    fun refreshData() {
        _refreshState.value = RefreshState.REFRESHING
        loadData()
    }

    fun clickTvLoading(){
        if (_loadState != LoadState.LOADING) {
            _loadState.value = LoadState.LOADING
        }
    }

}
