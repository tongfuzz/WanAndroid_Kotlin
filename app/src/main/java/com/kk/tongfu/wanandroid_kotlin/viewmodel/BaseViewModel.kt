package com.kk.tongfu.wanandroid_kotlin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kk.tongfu.wanandroid_kotlin.service.LoadState
import com.kk.tongfu.wanandroid_kotlin.service.RefreshState

/**
 * Created by tongfu
 * on 2019-11-07
 * Desc:
 */

open class BaseViewModel:ViewModel() {

    protected val baseLoadState: MutableLiveData<LoadState> = MutableLiveData(LoadState.SUCCESS)
    val loadState: MutableLiveData<LoadState>
        get() = baseLoadState

    protected val baseRefreshState: MutableLiveData<RefreshState> =
        MutableLiveData(RefreshState.REFRESHING_SUCCESS)
    val refreshState: LiveData<RefreshState>
        get() = baseRefreshState


    fun stateEmpty(){
        if(baseLoadState.value==LoadState.LOADING){
            baseLoadState.value=LoadState.NO_DATA
        }

        if(baseRefreshState.value==RefreshState.REFRESHING){
            baseRefreshState.value=RefreshState.REFRESHING_ERROR
        }

        if(baseRefreshState.value==RefreshState.LOADING){
            baseRefreshState.value=RefreshState.LOADING_NO_MORE_DATA
        }
    }

    fun stateMain(){

        if(baseLoadState.value==LoadState.LOADING){
            baseLoadState.value=LoadState.SUCCESS
        }

        if(baseRefreshState.value==RefreshState.REFRESHING){
            baseRefreshState.value=RefreshState.REFRESHING_SUCCESS
        }

        if(baseRefreshState.value==RefreshState.LOADING){
            baseRefreshState.value=RefreshState.LOADING_SUCCESS
        }
    }

    fun stateError(){

        if(baseLoadState.value==LoadState.LOADING){
            baseLoadState.value=LoadState.ERROR
        }

        if(baseRefreshState.value==RefreshState.REFRESHING){
            baseRefreshState.value=RefreshState.REFRESHING_ERROR
        }

        if(baseRefreshState.value==RefreshState.LOADING){
            baseRefreshState.value=RefreshState.LOADING_ERROR
        }
    }

}