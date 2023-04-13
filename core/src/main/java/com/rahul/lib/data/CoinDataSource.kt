package com.rahul.lib.data

import com.rahul.lib.data.models.CoinEntity

interface CoinDataSource {
    suspend fun getAll(): List<CoinEntity>
}

sealed class Resource<out T> {
    class Success<T>(val data: T) : Resource<T>()
    class Error(val exception : Exception) : Resource<Nothing>()
    object Loading : Resource<Nothing>()
    object Empty : Resource<Nothing>()
}