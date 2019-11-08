package com.kk.tongfu.wanandroid_kotlin.viewmodel

import androidx.lifecycle.MutableLiveData
import com.kk.tongfu.wanandroid_kotlin.service.model.Chapter

/**
 * Created by tongfu
 * on 2019-11-07
 * Desc:
 */

class ChapterViewModel(item: Chapter) : BaseViewModel() {

    var courseId = MutableLiveData<Int>(item.courseId)
    var id = MutableLiveData(item.id)
    var name = MutableLiveData(item.name)
    var childrenStr: MutableLiveData<String>?

    init {
        childrenStr = item.children?.let {
            var childString = StringBuilder()
            for (item in it) {
                childString.append("${item.name}  ")
            }
            MutableLiveData(childString.toString())
        }
    }

}