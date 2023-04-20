package com.rahul.domain_module.core

import com.rahul.data_module.source.exceptions.ApiException

sealed class UseCaseWrapper<out E, out V> {

    data class Success<out V>(val value: V) : UseCaseWrapper<Nothing, V>()
    data class Error<out E>(val error: E) : UseCaseWrapper<E, Nothing>()

    companion object Factory {

        inline fun <V> build(function: () -> V): UseCaseWrapper<ApiException, V> =
            try {
                Success(function.invoke())
            } catch (e: ApiException) {
                Error(e)
            }
    }
}