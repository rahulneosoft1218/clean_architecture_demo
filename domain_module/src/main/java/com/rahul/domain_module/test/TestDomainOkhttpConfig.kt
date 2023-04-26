package com.rahul.domain_module.test

import com.rahul.data_module.source.network.retrofit.NetworkCheckInterceptor
import com.rahul.data_module.source.network.retrofit.OkhttpConfiguration
import okhttp3.OkHttpClient

class TestDomainOkhttpConfig(private val internetOn: Boolean) : OkhttpConfiguration() {

    override fun getNetworkCheckInterceptor() = object : NetworkCheckInterceptor() {
        override fun isNetworkConnectionAvailable(): Boolean = internetOn
    }

    override fun configOkhttpClient(builder: OkHttpClient.Builder) = builder
}