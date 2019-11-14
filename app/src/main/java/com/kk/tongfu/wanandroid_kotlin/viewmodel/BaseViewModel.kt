package com.kk.tongfu.wanandroid_kotlin.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kk.tongfu.wanandroid_kotlin.MainApplication
import com.kk.tongfu.wanandroid_kotlin.R
import com.kk.tongfu.wanandroid_kotlin.service.LoadState
import com.kk.tongfu.wanandroid_kotlin.service.RefreshState
import com.kk.tongfu.wanandroid_kotlin.service.model.NetworkInfo

/**
 * Created by tongfu
 * on 2019-11-07
 * Desc:
 */

open class BaseViewModel(private val appContext: Context) : ViewModel() {

    private val baseLoadState: MutableLiveData<LoadState> = MutableLiveData(LoadState.SUCCESS)
    val loadState: LiveData<LoadState>
        get() = baseLoadState

    private val baseRefreshState: MutableLiveData<RefreshState> =
        MutableLiveData(RefreshState.REFRESHING_SUCCESS)
    val refreshState: LiveData<RefreshState>
        get() = baseRefreshState

    private val baseToastString = MutableLiveData<String>()
    val toastStr: LiveData<String>
        get() = baseToastString

    private val _netWorkInfo = MutableLiveData<NetworkInfo>()
    val netWorkInfo: LiveData<NetworkInfo>
        get() = _netWorkInfo

    init {
        _netWorkInfo.value = MainApplication.database?.daoService()?.getNetWorkInfo()?.value
    }

    //没有数据的状态
    fun stateEmpty() {
        if (baseLoadState.value == LoadState.LOADING) {
            baseLoadState.value = LoadState.NO_DATA
            baseToastString.value=appContext.getString(R.string.no_data)
        }

        if (baseRefreshState.value == RefreshState.REFRESHING) {
            baseRefreshState.value = RefreshState.REFRESHING_ERROR
            baseToastString.value=appContext.getString(R.string.no_more_new_data)
        }

        if (baseRefreshState.value == RefreshState.LOADING) {
            baseRefreshState.value = RefreshState.LOADING_NO_MORE_DATA
            baseToastString.value=appContext.getString(R.string.no_more_data)
        }
    }

    //有数据的状态
    fun stateMain() {

        if (baseLoadState.value == LoadState.LOADING) {
            baseLoadState.value = LoadState.SUCCESS
        }

        if (baseRefreshState.value == RefreshState.REFRESHING) {
            baseRefreshState.value = RefreshState.REFRESHING_SUCCESS
        }

        if (baseRefreshState.value == RefreshState.LOADING) {
            baseRefreshState.value = RefreshState.LOADING_SUCCESS
        }
    }

    //发生错误时的状态
    fun stateError() {

        if (baseLoadState.value == LoadState.LOADING) {
            baseLoadState.value = LoadState.ERROR
            baseToastString.value=appContext.getString(R.string.load_error)
        }

        if (baseRefreshState.value == RefreshState.REFRESHING) {
            baseRefreshState.value = RefreshState.REFRESHING_ERROR
            baseToastString.value=appContext.getString(R.string.failed_to_refresh)
        }

        if (baseRefreshState.value == RefreshState.LOADING) {
            baseRefreshState.value = RefreshState.LOADING_ERROR
            baseToastString.value=appContext.getString(R.string.load_error)
        }
    }

    //没有网络时的状态
    fun stateNoNetwork() {

        if (baseLoadState.value == LoadState.LOADING) {
            baseLoadState.value = LoadState.NO_NETWORK
        } else if (baseRefreshState.value == RefreshState.LOADING) {
            baseRefreshState.value = RefreshState.LOADING_NO_NETWORK
        } else if (baseRefreshState.value == RefreshState.REFRESHING) {
            baseRefreshState.value = RefreshState.REFRESHING_NO_NETWORK
        }
        baseToastString.value=appContext.getString(R.string.no_network_and_check)
    }

    //是否在加载更多的状态
    fun isStateLoadmore() = baseRefreshState.value == RefreshState.LOADING

    //是否时在刷新中的状态
    fun isStateRefreshing() = baseRefreshState.value == RefreshState.REFRESHING

    //是否是在加载中的状态
    fun isStateLoading() = baseLoadState == LoadState.LOADING

    //设置状态为加载更多
    fun stateLoadmore(){
        baseRefreshState.value=RefreshState.LOADING
    }

    //设置状态为加载中
    fun stateLoading(){
        baseLoadState.value=LoadState.LOADING
    }

    //设置状态为正在刷新
    fun stateRefreshing(){
        baseRefreshState.value=RefreshState.REFRESHING
    }

    fun isLoadingOrRefresh(): Boolean {
        return baseLoadState == LoadState.LOADING || baseRefreshState.value == RefreshState.LOADING || baseRefreshState.value == RefreshState.REFRESHING
    }

}