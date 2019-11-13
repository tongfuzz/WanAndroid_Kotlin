package com.kk.tongfu.wanandroid_kotlin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.kk.tongfu.wanandroid_kotlin.R

/**
 * Created by tongfu
 * on 2019-11-12
 * Desc:
 */

class MainViewModel(private val state:SavedStateHandle):ViewModel() {

    companion object{
        const val CURRENT_TAB_ID="current_id"
    }

    init {
        saveTabId(R.id.homepage)
    }

    fun getCurrentTabId(): Int {
       return  state.get<Int>(CURRENT_TAB_ID)?:-1
    }

    fun saveTabId(id: Int) {
        state.set(CURRENT_TAB_ID,id)
    }

}