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
import com.kk.tongfu.wanandroid_kotlin.util.getNetWorkData
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by tongfu
 * on 2019-11-07
 * Desc:
 */

class SystemViewModel @Inject constructor(private val repository: ProjectRepository,appContext:Context) : BaseViewModel(appContext) {

    private val _systemList = MutableLiveData(mutableListOf<Chapter>())
    val systemList: MutableLiveData<MutableList<Chapter>>
        get() = _systemList

    init {
        getSystemListData()
    }

    private fun loadData() {
        getNetWorkData {
            val systemListData = repository.getSystemListData()
            if (systemListData.errorCode != HttpCode.SUCCESS) {
                stateError()
                return@getNetWorkData
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
        if(!isStateRefreshing()) {
            stateRefreshing()
            loadData()
        }
    }

    fun getSystemListData(){
        if(!isStateLoading()) {
            stateLoading()
            loadData()
        }
    }

}