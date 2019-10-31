package com.kk.tongfu.wanandroid_kotlin.util

import androidx.annotation.StringRes
import com.kk.tongfu.wanandroid_kotlin.MainApplication

/**
 * Created by tongfu
 * on 2019-10-31
 * Desc:
 */

class ResourceUtils {

    companion object {
        @JvmStatic
        fun getString(@StringRes resId: Int): String {
            return MainApplication.application?.resources?.getString(resId)?:""
        }
    }
}