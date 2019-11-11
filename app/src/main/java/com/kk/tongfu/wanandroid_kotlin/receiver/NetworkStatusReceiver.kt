package com.kk.tongfu.wanandroid_kotlin.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.kk.tongfu.wanandroid_kotlin.MainApplication
import com.kk.tongfu.wanandroid_kotlin.service.model.NetworkInfo
import com.kk.tongfu.wanandroid_kotlin.util.checkNetWorkStatus
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
        context?.checkNetWorkStatus()
    }
}