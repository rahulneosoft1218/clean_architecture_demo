package com.rahul.data_module.source.network.retrofit

import okhttp3.OkHttpClient

abstract class OkhttpConfiguration {

    abstract fun getNetworkCheckInterceptor():NetworkCheckInterceptor

    abstract fun configOkhttpClient(builder : OkHttpClient.Builder):OkHttpClient.Builder


}