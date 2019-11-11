package com.kk.tongfu.wanandroid_kotlin.service.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kk.tongfu.wanandroid_kotlin.service.model.NetworkInfo

/**
 * Created by tongfu
 * on 2019-11-08
 * Desc:
 */

@Dao
interface DaoService {

    @Query("SELECT * FROM networkinfo WHERE id=0")
    fun getNetWorkInfo(): LiveData<NetworkInfo>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg networkInfos: NetworkInfo)
}