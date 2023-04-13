package com.rahul.lib.data

import com.rahul.lib.data.models.CoinEntity

interface ICoinRepository {
    suspend fun getAll(): List<CoinEntity>
}