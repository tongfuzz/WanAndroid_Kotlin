package com.kk.tongfu.wanandroid_kotlin.di

import androidx.lifecycle.ViewModel
import com.kk.tongfu.wanandroid_kotlin.MainApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

/**
 * Created by tongfu
 * on 2019-09-19
 * Desc:
 */

@Singleton
@Component(modules = [AndroidInjectionModule::class,ActivityModule::class,FragmentModule::class,AppModule::class,ViewModelMoudle::class])
interface AppComponent {

    @Component.Builder
    interface Builder{
        @BindsInstance
        fun application( application:MainApplication):Builder

        fun build():AppComponent
    }

    fun inject(app:MainApplication)
}