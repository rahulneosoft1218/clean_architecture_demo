package com.rahul.data_module.source.network.retrofit

import com.rahul.data_module.source.exceptions.ApiException
import okhttp3.Interceptor
import okhttp3.Response
import okio.IOException

abstract class NetworkCheckInterceptor : Interceptor {

    abstract fun isNetworkConnectionAvailable(): Boolean

    @kotlin.jvm.Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        val ioException = IOException()

        if (!isNetworkConnectionAvailable()) {
            throw ioException.initCause(ApiException.NoInternetConnection)
        }

        val response = chain.proceed(chain.request())
        if (response.isSuccessful) {
            return response
        }


        val apiException = when (val resCode = response.code) {
            -1 -> ApiException.NoInternetConnection
            500 -> ApiException.InternalServerException
            400, 404 -> {
                val errorRes = response.body?.string()
                ApiException.BadRequestException(errorRes)
            }
            else -> {
                ApiException.UnknownException(resCode, response.message)
            }
        }


        throw ioException.initCause(apiException)


    }
}