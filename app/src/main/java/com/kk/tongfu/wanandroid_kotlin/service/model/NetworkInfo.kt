package com.kk.tongfu.wanandroid_kotlin.service.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by tongfu
 * on 2019-11-08
 * Desc:网络信息Room存储类
 */

@Entity(tableName = "networkinfo")
data class NetworkInfo(
    @PrimaryKey val id:Int,
    @ColumnInfo(name = "is_wifi") val isWifi: Boolean,
    @ColumnInfo(name="is_cellular") val isCellular:Boolean,
    @ColumnInfo(name="is_connected") val isConnected:Boolean
)