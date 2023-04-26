package com.rahul.data_module.repositories

import com.rahul.data_module.source.exceptions.ApiException
import com.rahul.data_module.source.exceptions.ApiException.Companion.mapToApiException
import com.rahul.data_module.source.network.retrofit.ResultWrapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import java.io.IOException

open class DataRepository {

    suspend fun <R> executeApi(
        scope: CoroutineScope,
        api: suspend () -> R
    ): ResultWrapper<ApiException, R> {
        return scope.async(Dispatchers.IO) {
            return@async try {
                ResultWrapper.Success(api.invoke())
            } catch (th: IOException) {
                ResultWrapper.Error(th.mapToApiException())
            }
        }.await()
    }


}