package com.rahul.data_module.source

import com.rahul.data_module.source.exceptions.ApiException

sealed class ResultWrapper<out E, out V> {

    data class Success<out V>(val value: V) : ResultWrapper<Nothing, V>()
    data class Error<out E>(val error: E) : ResultWrapper<E, Nothing>()

    companion object Factory {

        inline fun <V> build(function: () -> V): ResultWrapper<ApiException, V> =
            try {
                Success(function.invoke())
            } catch (e: ApiException) {
                Error(e)
            }
    }
}