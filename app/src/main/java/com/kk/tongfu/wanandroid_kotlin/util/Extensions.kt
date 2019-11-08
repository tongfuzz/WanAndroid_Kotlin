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
import com.kk.tongfu.wanandroid_kotlin.service.model.BaseResponse

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

fun Context.isNetWrokConnected(): Boolean {
    val systemService = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val networkCapabilities = systemService.getNetworkCapabilities(systemService.activeNetwork)
        if (networkCapabilities != null && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
        ) {
            if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                //当前网络是wifi
                println("network  wifi")
            } else if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                //当前数据是移动网络
                println("network cellular")
            }
            return true
        }
        return false
    } else {
        val activeNetworkInfo = systemService.activeNetworkInfo
        //当前是否是wifi网络
        val isWifi = activeNetworkInfo.type == ConnectivityManager.TYPE_WIFI
        //当前网络是否是移动网络
        val isMob = activeNetworkInfo.type == ConnectivityManager.TYPE_MOBILE

        if(isWifi){
            println("network wifi")
        }

        if(isMob){
            println("network mobile")
        }

        return activeNetworkInfo.isConnected
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

