package com.rahul.data_module.repositories

import com.rahul.data_module.source.ResultWrapper
import com.rahul.data_module.source.exceptions.ApiException
import com.rahul.data_module.source.exceptions.ApiException.Companion.mapToApiException

open class DataRepository {

    suspend fun <R> executeApi(api: suspend () -> R): ResultWrapper<ApiException, R> {
        return try {
            ResultWrapper.Success(api.invoke())
        } catch (th: Throwable) {
            ResultWrapper.Error(th.mapToApiException())
        }
    }


}