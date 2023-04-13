package com.rahul.data_module.di

import com.rahul.data_module.source.ApiService
import com.rahul.data_module.source.RetrofitApiClient
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

@dagger.Module
class DataModule {

    @Provides
    fun provideApiService(okHttpClient: OkHttpClient):ApiService{
        return RetrofitApiClient("https://api.coingecko.com/api/v3/",okHttpClient).getApiService()
    }

    @Provides
    fun provideOkhttpClient():OkHttpClient{
        val httpLogging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient().newBuilder()
            .addInterceptor(httpLogging)
            .build()
    }






}