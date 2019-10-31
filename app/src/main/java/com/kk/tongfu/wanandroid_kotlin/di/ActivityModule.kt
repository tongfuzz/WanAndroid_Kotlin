package com.kk.tongfu.wanandroid_kotlin.di

import com.kk.tongfu.wanandroid_kotlin.ui.MainActivity
import com.kk.tongfu.wanandroid_kotlin.ui.homepage.DetailsActivity
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

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
    internal abstract fun detailsActivity():DetailsActivity

}