package com.kk.tongfu.wanandroid_kotlin.service.model

/**
 * Created by tongfu
 * on 2019-09-24
 * Desc:
 */

class ArticleList constructor(
    val curPage: Int,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int,
    val datas: MutableList<Article>
)