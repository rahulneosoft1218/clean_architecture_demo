package com.rahul.data_module.repositories

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rahul.data_module.source.exceptions.ApiException
import com.rahul.data_module.source.network.retrofit.ResultWrapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay

open class DataRepository {

    suspend fun <R> executeApi(
        scope: CoroutineScope,
        api: suspend () -> R,
    ): ResultWrapper<ApiException, R> {
        return scope.async(Dispatchers.IO) {
            return@async try {
                ResultWrapper.Success(api.invoke())
            } catch (th: Throwable) {
                mapApiException(th)
            }
        }.await()
    }

    private fun mapApiException(th: Throwable?): ResultWrapper.Error<ApiException> {

        var apiException: ApiException? = ApiException.UnknownException()

        if (th == null) return ResultWrapper.Error(apiException!!)


        if (th.cause is ApiException) {
            apiException = th.cause as ApiException?
        }
        if (apiException is ApiException.UnknownException && th.cause?.cause is ApiException) {
            apiException = th.cause?.cause as ApiException?
        }
        if (apiException is ApiException.UnknownException) {
            apiException = ApiException.UnknownException(error = th.message)
        }
        return ResultWrapper.Error(apiException
            ?: ApiException.UnknownException(error = th.message))

    }


    @kotlin.jvm.Throws
    protected suspend fun <R> mockResponse(jsonFileString: String, delayTime: Long = 2000L): R {
        try {
            delay(delayTime)
            return Gson().fromJson(jsonFileString,
                object : TypeToken<R>() {}.type)
        } catch (e: Exception) {
            throw  Throwable("Mock Response Mapper Error!")
        }
    }


}