package com.rahul.data_module.di

 import com.rahul.data_module.source.ApiService
import com.rahul.data_module.source.RetrofitApiClient
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

@dagger.Module
class DataModule constructor(
    private val baseUrl: String,
    private val extraInterceptors: List<Interceptor>? = null
) {

    @Provides
    @DataScope
    fun provideApiService(okHttpClient: OkHttpClient): ApiService {
        return RetrofitApiClient(baseUrl, okHttpClient).getApiService()
    }

    @Provides
    @DataScope
    fun provideOkhttpClient(): OkHttpClient {
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