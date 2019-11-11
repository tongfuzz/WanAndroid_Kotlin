package com.kk.tongfu.wanandroid_kotlin

import android.app.Application
import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.databinding.BindingAdapter
import androidx.room.Room.*
import com.kk.tongfu.wanandroid_kotlin.di.DaggerAppComponent
import com.kk.tongfu.wanandroid_kotlin.receiver.NetworkStatusReceiver
import com.kk.tongfu.wanandroid_kotlin.service.db.AppDatabase
import com.kk.tongfu.wanandroid_kotlin.util.checkNetWorkStatus
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject


/**
 * Created by tongfu
 * on 2019-09-19
 * Desc:
 */

class MainApplication : Application(), HasAndroidInjector {


    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>


    override fun onCreate() {
        super.onCreate()
        database = databaseBuilder(this, AppDatabase::class.java, "app-data").build()
        application = this
        appContext = this
        DaggerAppComponent.builder().application(this).build().inject(this)

        //添加网络监听
        checkNetWorkStatus()
        val receiver=NetworkStatusReceiver()
        val intentFilter=IntentFilter()
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(receiver,intentFilter)
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return dispatchingAndroidInjector
    }

    companion object {

        @JvmField
        var appContext: Context? = null

        @JvmField
        var application: Application? = null

        @JvmField
        var database:AppDatabase?=null
    }

}