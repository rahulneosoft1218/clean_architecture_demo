package com.rahul.data_module.source

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitApiClient @Inject constructor(baseUrl: String, client: OkHttpClient) {

    private val apiService: ApiService = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(ApiService::class.java)

    fun getApiService() = apiService


}