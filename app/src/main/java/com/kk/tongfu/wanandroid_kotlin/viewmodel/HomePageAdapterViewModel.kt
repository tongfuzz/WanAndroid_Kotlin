package com.kk.tongfu.wanandroid_kotlin.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kk.tongfu.wanandroid_kotlin.R
import com.kk.tongfu.wanandroid_kotlin.service.model.Article
import com.kk.tongfu.wanandroid_kotlin.util.ResourceUtils

/**
 * Created by tongfu
 * on 2019-10-31
 * Desc:
 */

class HomePageAdapterViewModel constructor(item: Article) : ViewModel() {

    val top = MutableLiveData<Boolean>(item.top)
    val fresh = MutableLiveData<Boolean>(item.fresh)
    val tag= MutableLiveData<String>(item.tags.firstOrNull()?.name)
    val author = MutableLiveData<String>(item.author)
    val time = MutableLiveData<String>(item.niceDate)
    val title = MutableLiveData<String>(item.title)
    val chapterName = MutableLiveData<String>(
        String.format(
            ResourceUtils.getString(R.string.chapter_name),
            item.superChapterName,
            item.chapterName
        )
    )
    val url =item.link

}