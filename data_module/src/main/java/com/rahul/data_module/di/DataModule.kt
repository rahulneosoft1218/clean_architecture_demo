package com.rahul.data_module.di

import com.rahul.data_module.source.ApiService
import com.rahul.data_module.source.RetrofitApiClient
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Module
class DataModule @Inject constructor() {

    @Provides
    @DataScope
    fun provideApiService(
        @Named("baseUrl") baseUrl: String,
        okHttpClient: OkHttpClient
    ): ApiService {
        return RetrofitApiClient(baseUrl, okHttpClient).getApiService()
    }

    @Provides
    @DataScope
    fun provideOkhttpClient(extraInterceptors: List<Interceptor>?): OkHttpClient {
        val httpLogging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val okHttpClientBuilder = OkHttpClient()
            .newBuilder()

        extraInterceptors?.forEach {
            okHttpClientBuilder.addInterceptor(it)
        }
        return okHttpClientBuilder
            .addInterceptor(httpLogging)
            .build()
    }


}