package com.kk.tongfu.wanandroid_kotlin.di

import android.content.Context
import com.kk.tongfu.wanandroid_kotlin.MainApplication
import com.kk.tongfu.wanandroid_kotlin.service.repository.ApiService
import com.kk.tongfu.wanandroid_kotlin.service.repository.ProjectRepository
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by tongfu
 * on 2019-09-23
 * Desc:
 */
@Module
class AppModule {

    @Provides
    fun provideContext(application: MainApplication): Context {
        return application.applicationContext
    }

    @Singleton
    @Provides
    fun provideApiService(okHttpClient: OkHttpClient): ApiService {
        return Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().build()
    }

    @Provides
    @Singleton
    fun provideProjectRepository(apiService: ApiService,context: Context): ProjectRepository {
        return ProjectRepository(apiService)
    }


}
