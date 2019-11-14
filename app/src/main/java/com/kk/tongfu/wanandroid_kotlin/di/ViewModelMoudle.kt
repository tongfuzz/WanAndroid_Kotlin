package com.kk.tongfu.wanandroid_kotlin.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kk.tongfu.wanandroid_kotlin.viewmodel.HomePageViewModel
import com.kk.tongfu.wanandroid_kotlin.ui.navigation.NavigationViewModel
import com.kk.tongfu.wanandroid_kotlin.viewmodel.ArticleListViewModel
import com.kk.tongfu.wanandroid_kotlin.viewmodel.ProjectViewModelFactory
import com.kk.tongfu.wanandroid_kotlin.viewmodel.SystemViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import javax.inject.Singleton

/**
 * Created by tongfu
 * on 2019-09-23
 * Desc:
 */

@Module
@Suppress("UNUSED")
abstract class ViewModelMoudle {

    //Binds注解表明提供ViewModelProvider.Factory时使用ProjectViewModelFactory作为代理，只能绑定抽象方法，
    // 之所以使用@Binds而不使用@Providers是因为@Binds生成的实现看起来更高效,其实可以使用下方代码代替

   /*  @Provides
    internal fun provideViewModelFactory(factory: ProjectViewModelFactory): ViewModelProvider.Factory {
        return factory
    }

    @Provides
    @IntoMap
    @ViewModelKey(HomePageViewModel::class)
    internal fun provideHomePageViewModel(viewModel: HomePageViewModel): ViewModel {
        return viewModel
    }
*/
    @Binds
    internal abstract fun bindViewModelFactory(factory: ProjectViewModelFactory):
            ViewModelProvider.Factory

    @Binds
    @Singleton
    @IntoMap
    @ViewModelKey(HomePageViewModel::class)
    internal abstract fun bindHomePageViewModel(viewModel: HomePageViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NavigationViewModel::class)
    internal abstract fun bindNavigationViewModel(viewModel:NavigationViewModel):ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SystemViewModel::class)
    internal abstract fun bindSystemViewModel(viewModel: SystemViewModel):ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ArticleListViewModel::class)
    internal abstract fun bindArticleListViewModel(viewModel:ArticleListViewModel):ViewModel

    @Binds
    @IntoMap
    @StringKey("haha")
    internal abstract fun bindStringInfo(viewModel: HomePageViewModel):ViewModel

    @Binds
    @IntoMap
    @StringKey("hehe")
    internal abstract fun bindStringInfo2(viewModel: HomePageViewModel):ViewModel

    @Binds
    @IntoMap
    @StringKey("00")
    internal abstract fun bindStringInfo3(viewModel: HomePageViewModel):ViewModel


   /* @Provides
    @IntoMap
    @StringKey("lele")
    internal  fun bindStringInfo4():String{
        return "tongfu"
    }*/

}