package com.kk.tongfu.wanandroid_kotlin.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.kk.tongfu.wanandroid_kotlin.MainApplication
import com.kk.tongfu.wanandroid_kotlin.service.model.NetworkInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

/**
 * Created by tongfu
 * on 2019-11-08
 * Desc:
 */

class NetworkStatusReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        context?.apply {
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

            CoroutineScope(IO).launch {
                MainApplication.database?.daoService()?.insert(networkInfo)
            }

        }
    }
}