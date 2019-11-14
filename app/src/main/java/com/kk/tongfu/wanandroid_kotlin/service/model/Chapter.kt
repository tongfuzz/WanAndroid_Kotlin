package com.kk.tongfu.wanandroid_kotlin.service.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by tongfu
 * on 2019-11-07
 * Desc:
 */

@Parcelize
data class Chapter(
    var courseId: Int?,
    var id: Int?,
    var name: String?,
    var order: Int?,
    var parentChapterId: Int?,
    var userControlSetTop: Boolean?,
    var visible: Int?,
    var children: MutableList<Chapter>?
):Parcelable