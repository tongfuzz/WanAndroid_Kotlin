package com.kk.tongfu.wanandroid_kotlin.ui.homepage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kk.tongfu.wanandroid_kotlin.service.HttpCode
import com.kk.tongfu.wanandroid_kotlin.service.LoadState
import com.kk.tongfu.wanandroid_kotlin.service.RefreshState
import com.kk.tongfu.wanandroid_kotlin.service.model.*
import com.kk.tongfu.wanandroid_kotlin.service.repository.ApiService
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by tongfu
 * on 2019-09-19
 * Desc:
 */

class HomePageViewModel @Inject constructor(private val apiService: ApiService) :
    ViewModel() {

    val _bannerList: MutableLiveData<MutableList<Banner>?> = MutableLiveData()

    val _articleList: MutableLiveData<MutableList<Article>?> = MutableLiveData()

    private val _homepageData: MutableLiveData<HomepageData> = MutableLiveData(HomepageData(bannerList = _bannerList,articleList = _articleList))
    val homePageData: LiveData<HomepageData>
        get() = _homepageData

    private val _loadState: MutableLiveData<LoadState> = MutableLiveData(LoadState.LOADING)
    val loadState: LiveData<LoadState>
        get() = _loadState

    private val _refreshState: MutableLiveData<RefreshState> =
        MutableLiveData(RefreshState.REFRESHING_SUCCESS)
    val refreshState: LiveData<RefreshState>
        get() = _refreshState

    val hasMore: MutableLiveData<Boolean> = MutableLiveData(true)
    var pageNum: Int = 0


    fun getData() {
        viewModelScope.launch {
            val bannerList = apiService.getBannerData()

            val articleList: BaseResponse<ArticleList?>
            if (_loadState == LoadState.LOADING || _refreshState == RefreshState.REFRESHING) {
                articleList = apiService.getArticleListData(0)
                pageNum = 0
                hasMore.value = true
                println("info:refresh"+pageNum)
            } else {
                articleList = apiService.getArticleListData(pageNum++)
                println("info:loadmore"+pageNum)
            }
            val topArticleListData = apiService.getTopArticleListData()


            if (articleList.errorCode != HttpCode.SUCCESS) {
                if (_refreshState.value == RefreshState.REFRESHING) {
                    //说明是刷新出现了问题
                    _refreshState.value = RefreshState.REFRESHING_ERROR
                    return@launch
                }

                if (_refreshState.value == RefreshState.LOADING) {
                    //说明是加载更多的时候出现了问题
                    _refreshState.value = RefreshState.LOADING_ERROR
                    return@launch
                }

                //说明初次加载出现了问题
                _loadState.value = LoadState.ERROR
                return@launch
            }

            if (_loadState.value == LoadState.LOADING || _refreshState.value == RefreshState.REFRESHING) {
                //加载成功，判断是刷新还是初次加载

                val data = topArticleListData.data
                if (topArticleListData.errorCode == HttpCode.SUCCESS && data != null && data.isNotEmpty()) {

                    for (article in data) {
                        article.top = true
                    }

                    if (articleList.data != null) {
                        articleList.data.datas.addAll(0, data)
                    }

                }

                _bannerList.value=bannerList.data
                _articleList.value=articleList.data?.datas

                if (_loadState.value == LoadState.LOADING) {
                    //如果是初次加载，设置初次加载成功
                    _loadState.value = LoadState.SUCCESS
                } else {
                    //如果是刷新加载，设置刷新加载成功
                    _refreshState.value = RefreshState.REFRESHING_SUCCESS
                }
            } else {
                //如果不是刷新也不是初次加载，说明它是加载更多操作
                if (articleList?.data != null && articleList.data.datas.isNotEmpty()) {
                    _articleList?.value= _articleList?.value?.apply {
                        addAll(articleList.data.datas)
                    }
                    _refreshState.value = RefreshState.LOADING_SUCCESS
                } else {
                    _refreshState.value = RefreshState.LOADING_ERROR
                    hasMore.value = false
                }
            }
        }
    }

    fun onSwipeRefresh() {
        _refreshState.value = RefreshState.REFRESHING
        getData()
    }

    fun onLoadMore() {
        _refreshState.value = RefreshState.LOADING
        getData()
    }

}
