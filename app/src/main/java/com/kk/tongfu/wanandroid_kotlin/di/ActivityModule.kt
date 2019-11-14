package com.kk.tongfu.wanandroid_kotlin.di

import com.kk.tongfu.wanandroid_kotlin.ui.MainActivity
import com.kk.tongfu.wanandroid_kotlin.ui.homepage.ArticleDetailActivity
import com.kk.tongfu.wanandroid_kotlin.ui.system.SystemDetailActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by tongfu
 * on 2019-09-19
 * Desc:
 */
@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    internal abstract fun mainActivity():MainActivity

    @ContributesAndroidInjector
    internal abstract fun detailsActivity():ArticleDetailActivity

    @ContributesAndroidInjector
    internal abstract fun systemDetailActivity():SystemDetailActivity

}