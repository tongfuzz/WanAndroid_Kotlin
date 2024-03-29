package com.kk.tongfu.wanandroid_kotlin.service.model


/**
 * Created by tongfu
 * on 2019-09-24
 * Desc:
 */

data class Article constructor(
   var apkLink: String,
   var author: String,
   var chapterId: Int,
   var chapterName: String,
   var collect: Boolean,
   var courseId: Int,
   var desc: String,
   var envelopePic: String,
   var top: Boolean,
   var fresh: Boolean,
   var id: Int,
   var link: String,
   var niceDate: String,
   var origin: String,
   var projectLink: String,
   var publishTime: Long,
   var superChapterId: Int,
   var superChapterName: String,
   var title: String,
   var type: Int,
   var userId: Int,
   var visible: Int,
   var zan: Int,
   var tags: List<Tag>
)