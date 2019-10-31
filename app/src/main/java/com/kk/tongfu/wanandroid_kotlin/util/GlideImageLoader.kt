package com.kk.tongfu.wanandroid_kotlin.util

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.youth.banner.loader.ImageLoader

/**
 * Created by tongfu
 * on 2019-10-29
 * Desc: 为banner配置的图片加载器
 */

class GlideImageLoader : ImageLoader() {
    companion object {

        @JvmStatic
        fun loadImage(context: Context, path: Any, imageView: ImageView) {
            Glide.with(context).load(path).into(imageView)
        }
    }

    override fun displayImage(context: Context, path: Any, imageView: ImageView) {
        loadImage(context, path, imageView)
    }


}