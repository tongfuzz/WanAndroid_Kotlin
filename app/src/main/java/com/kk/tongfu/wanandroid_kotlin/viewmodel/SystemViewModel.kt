package com.kk.tongfu.wanandroid_kotlin.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kk.tongfu.wanandroid_kotlin.R
import com.kk.tongfu.wanandroid_kotlin.service.HttpCode
import com.kk.tongfu.wanandroid_kotlin.service.LoadState
import com.kk.tongfu.wanandroid_kotlin.service.RefreshState
import com.kk.tongfu.wanandroid_kotlin.service.model.Chapter
import com.kk.tongfu.wanandroid_kotlin.service.repository.ProjectRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by tongfu
 * on 2019-11-07
 * Desc:
 */

class SystemViewModel @Inject constructor(private val repository: ProjectRepository,private val context:Context) : BaseViewModel() {

    private val _systemList = MutableLiveData(mutableListOf<Chapter>())
    val systemList: MutableLiveData<MutableList<Chapter>>
        get() = _systemList

    var isNetworkConnected=MutableLiveData<Boolean>(true)

    init {
        getSystemListData()
    }

    fun loadData() {

        viewModelScope.launch {

            /*repository.getNetWorkInfo()?.apply {
                if(!isConnected){
                    if(isLoadingOrRefresh()) {
                        stateNoNetwork()
                    }else{
                        baseToastString.value=context.getString(R.string.no_network_and_check)
                    }
                    return@launch
                }
            }*/

            val systemListData = repository.getSystemListData()
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