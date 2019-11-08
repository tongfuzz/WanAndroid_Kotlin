package com.kk.tongfu.wanandroid_kotlin.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kk.tongfu.wanandroid_kotlin.service.HttpCode
import com.kk.tongfu.wanandroid_kotlin.service.LoadState
import com.kk.tongfu.wanandroid_kotlin.service.RefreshState
import com.kk.tongfu.wanandroid_kotlin.service.model.Chapter
import com.kk.tongfu.wanandroid_kotlin.service.repository.ApiService
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by tongfu
 * on 2019-11-07
 * Desc:
 */

class SystemViewModel @Inject constructor(val apiService: ApiService) : BaseViewModel() {

    private val _systemList = MutableLiveData(mutableListOf<Chapter>())
    val systemList: MutableLiveData<MutableList<Chapter>>
        get() = _systemList

    init {
        getSystemListData()
    }

    fun loadData() {
        viewModelScope.launch {
            val systemListData = apiService.getSystemListData()
            if (systemListData.errorCode != HttpCode.SUCCESS) {
                stateError()
                return@launch
            }
            if (systemListData.data != null && systemListData.data.isNotEmpty()) {
                _systemList.value = systemListData.data
                stateMain()
            } else {
                stateEmpty()
            }
        }
    }

    fun refreshSystemListData() {
        if(baseRefreshState.value!=RefreshState.REFRESHING) {
            baseRefreshState.value = RefreshState.REFRESHING
            loadData()
        }
    }

    private fun getSystemListData(){
        if(baseLoadState.value!=LoadState.LOADING) {
            baseLoadState.value = LoadState.LOADING
            loadData()
        }
    }

}