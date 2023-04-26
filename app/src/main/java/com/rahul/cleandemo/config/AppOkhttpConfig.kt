package com.rahul.cleandemo.config

import android.app.Application
import com.rahul.cleandemo.utils.checkInternet
import com.rahul.data_module.source.network.retrofit.NetworkCheckInterceptor
import com.rahul.data_module.source.network.retrofit.OkhttpConfiguration
import okhttp3.OkHttpClient

class AppOkhttpConfig(private val application: Application) : OkhttpConfiguration() {

    override fun getNetworkCheckInterceptor() = object : NetworkCheckInterceptor() {
        override fun isNetworkConnectionAvailable(): Boolean {
            return application.checkInternet()
        }

    }

    override fun configOkhttpClient(builder: OkHttpClient.Builder) = builder
}