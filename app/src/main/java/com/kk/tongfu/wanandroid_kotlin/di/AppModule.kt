package com.kk.tongfu.wanandroid_kotlin.di

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
    fun privideProjectRepository(apiService: ApiService): ProjectRepository {
        return ProjectRepository(apiService)
    }
}
