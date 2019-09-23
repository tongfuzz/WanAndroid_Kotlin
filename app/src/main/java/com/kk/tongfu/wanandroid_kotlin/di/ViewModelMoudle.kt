package com.kk.tongfu.wanandroid_kotlin.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kk.tongfu.wanandroid_kotlin.ui.homepage.HomePageViewModel
import com.kk.tongfu.wanandroid_kotlin.viewmodel.ProjectViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by tongfu
 * on 2019-09-23
 * Desc:
 */

@Module
@Suppress("UNUSED")
abstract class ViewModelMoudle  {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ProjectViewModelFactory):
            ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(HomePageViewModel::class)
    internal abstract fun bindCodelabsViewModel(viewModel: HomePageViewModel): ViewModel
}