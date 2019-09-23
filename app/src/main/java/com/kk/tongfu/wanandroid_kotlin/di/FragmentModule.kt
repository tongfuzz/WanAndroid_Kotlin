package com.kk.tongfu.wanandroid_kotlin.di

import com.kk.tongfu.wanandroid_kotlin.ui.homepage.HomePageFragment
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
}