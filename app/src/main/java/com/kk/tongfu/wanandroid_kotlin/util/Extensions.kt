package com.kk.tongfu.wanandroid_kotlin.util

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.viewModelScope
import com.kk.tongfu.wanandroid_kotlin.MainApplication
import com.kk.tongfu.wanandroid_kotlin.service.model.BaseResponse
import com.kk.tongfu.wanandroid_kotlin.service.model.NetworkInfo
import com.kk.tongfu.wanandroid_kotlin.service.repository.ProjectRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Created by tongfu
 * on 2019-09-18
 * Desc: 方法扩展
 */

//通过reified关键字，将VM标记为运行是可获取类型，即我们声明的viewmodel变量是什么类型，VM表示的就是什么类型
inline fun <reified VM : ViewModel> Fragment.viewModelProvider(viewModelFactory: ViewModelProvider.Factory) =
    ViewModelProviders.of(this, viewModelFactory).get(VM::class.java)


fun <T> BaseResponse<T>.dataConvert(): T? {
    if (errorCode == 200) {
        return data
    } else {
        return null
    }
}

fun Context.checkNetWorkStatus() {
    this?.apply {
        val systemService =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var id = 0
        var isWifi = false
        var isCellular = false
        var isConnected = false

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities =
                systemService.getNetworkCapabilities(systemService.activeNetwork)
            if (networkCapabilities != null && networkCapabilities.hasCapability(
                    NetworkCapabilities.NET_CAPABILITY_INTERNET
                )
                && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
            ) {
                isConnected = true
                if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    //当前网络是wifi
                    println("network  wifi")
                    isWifi = true
                } else if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    //当前数据是移动网络
                    println("network cellular")
                    isCellular = true
                }
            }
        } else {
            val activeNetworkInfo = systemService.activeNetworkInfo
            //当前是否是wifi网络
            isWifi = activeNetworkInfo.type == ConnectivityManager.TYPE_WIFI
            //当前网络是否是移动网络
            isCellular = activeNetworkInfo.type == ConnectivityManager.TYPE_MOBILE
            isConnected = activeNetworkInfo.isConnected
        }

        var networkInfo = NetworkInfo(id, isWifi, isCellular, isConnected)

        CoroutineScope(Dispatchers.IO).launch {
            MainApplication.database?.daoService()?.insert(networkInfo)
        }

    }
}

fun Fragment.toast(info: String) {
    Toast.makeText(context, info, Toast.LENGTH_SHORT).show()
}

fun Fragment.toast(@StringRes info: Int) {
    Toast.makeText(context, info, Toast.LENGTH_SHORT).show()
}

fun Activity.toast(info: String) {
    Toast.makeText(this, info, Toast.LENGTH_SHORT).show()
}

fun Activity.toast(@StringRes info: Int) {
    Toast.makeText(this, info, Toast.LENGTH_SHORT).show()
}

fun ViewModel.getNetWorkData(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
) {
    try {
        viewModelScope.launch(context, start, block)
    } catch (e: Exception) {
        println(e.message)
    }
}

