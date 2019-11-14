package com.kk.tongfu.wanandroid_kotlin.di

import com.kk.tongfu.wanandroid_kotlin.SystemDetailFragment
import com.kk.tongfu.wanandroid_kotlin.ui.homepage.HomePageFragment
import com.kk.tongfu.wanandroid_kotlin.ui.navigation.NavigationFragment
import com.kk.tongfu.wanandroid_kotlin.ui.system.ArticleListFragment
import com.kk.tongfu.wanandroid_kotlin.ui.system.SystemFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by tongfu
 * on 2019-09-20
 * Desc:
 */

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    internal abstract fun homeFragment():HomePageFragment

    @ContributesAndroidInjector
    internal abstract fun navigationFragment():NavigationFragment

    @ContributesAndroidInjector
    internal abstract fun systemFragment():SystemFragment

    @ContributesAndroidInjector
    internal abstract fun systemDetailFragment():SystemDetailFragment

    @ContributesAndroidInjector
    internal abstract fun chapterFragment():ArticleListFragment
}