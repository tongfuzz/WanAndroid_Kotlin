package com.kk.tongfu.wanandroid_kotlin.service.model

import androidx.lifecycle.MutableLiveData

/**
 * Created by tongfu
 * on 2019-09-27
 * Desc:
 */

data class HomepageData(
    var bannerList: MutableLiveData<MutableList<Banner>?>,
    var articleList: MutableLiveData<MutableList<out Any>?>
)