package com.kk.tongfu.wanandroid_kotlin.util

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.kk.tongfu.wanandroid_kotlin.service.model.Banner
import com.kk.tongfu.wanandroid_kotlin.service.model.BaseResponse
import com.kk.tongfu.wanandroid_kotlin.service.model.Tag

/**
 * Created by tongfu
 * on 2019-09-18
 * Desc: 方法扩展
 */

//通过reified关键字，将VM标记为运行是可获取类型，即我们声明的viewmodel变量是什么类型，VM表示的就是什么类型
inline fun <reified VM : ViewModel> Fragment.viewModelProvider(
    provider: ViewModelProvider.Factory
) =
    ViewModelProviders.of(this, provider).get(VM::class.java)


fun <T>BaseResponse<T>.dataConvert():T?{
    if(errorCode==200){
        return data
    }else{
        return null
    }
}

