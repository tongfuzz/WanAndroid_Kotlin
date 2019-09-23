package com.kk.tongfu.wanandroid_kotlin.ui.homepage

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.kk.tongfu.wanandroid_kotlin.service.model.Banner
import com.kk.tongfu.wanandroid_kotlin.service.repository.PorjectRepository
import javax.inject.Inject

/**
 * Created by tongfu
 * on 2019-09-19
 * Desc:
 */

class HomePageViewModel @Inject constructor(repository: PorjectRepository) :
    ViewModel() {

    private var bannerListObservable: LiveData<List<Banner>> = repository.getBannerList()

    fun getBannerListObservable(): LiveData<List<Banner>> = bannerListObservable

}