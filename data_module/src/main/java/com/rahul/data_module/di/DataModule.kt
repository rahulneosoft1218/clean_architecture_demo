package com.rahul.data_module.di

import com.rahul.data_module.source.cache.IAppCache
import com.rahul.data_module.source.network.INetworkData
import com.rahul.data_module.source.network.retrofit.OkhttpConfiguration
import com.rahul.data_module.source.network.retrofit.RetrofitApiClient
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Module
class DataModule @Inject constructor() {

    @Provides
    @Singleton
    fun provideNetworkData(
        @Named("baseUrl") baseUrl: String,
        okHttpClient: OkHttpClient,
        appcache: IAppCache
    ): INetworkData {
        return RetrofitApiClient(baseUrl, okHttpClient, appcache)
    }

    @Provides
    @Singleton
    fun provideOkhttpClient(
        okhttpConfiguration: OkhttpConfiguration
    ): OkHttpClient {
        val httpLogging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val builder = OkHttpClient()
            .newBuilder()
        builder.addInterceptor(httpLogging)
        builder.addInterceptor(okhttpConfiguration.getNetworkCheckInterceptor())
        return okhttpConfiguration.configOkhttpClient(builder = builder)
            .build()
    }


}