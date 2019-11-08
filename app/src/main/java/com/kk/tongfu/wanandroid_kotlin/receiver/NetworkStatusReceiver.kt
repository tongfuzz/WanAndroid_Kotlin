package com.kk.tongfu.wanandroid_kotlin.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.kk.tongfu.wanandroid_kotlin.util.isNetWrokConnected

/**
 * Created by tongfu
 * on 2019-11-08
 * Desc:
 */

class NetworkStatusReceiver:BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        println("network changed")
        context?.apply {
            if(isNetWrokConnected()){
                println("network connected")
            }else{
                println("network unconnected")
            }
        }
    }
}