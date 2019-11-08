package com.kk.tongfu.wanandroid_kotlin.service.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kk.tongfu.wanandroid_kotlin.service.dao.DaoService
import com.kk.tongfu.wanandroid_kotlin.service.model.NetworkInfo

/**
 * Created by tongfu
 * on 2019-11-08
 * Desc: 应用数据库
 */

@Database(entities = arrayOf(NetworkInfo::class), version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun daoService(): DaoService

}