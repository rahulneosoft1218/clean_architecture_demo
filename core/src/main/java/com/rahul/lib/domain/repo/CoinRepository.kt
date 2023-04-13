package com.rahul.lib.domain.repo

import com.rahul.lib.data.CoinDataSource
import com.rahul.lib.data.ICoinRepository
import com.rahul.lib.data.models.CoinEntity

class CoinRepository(private val dataSource: CoinDataSource): ICoinRepository {
    override suspend fun getAll(): List<CoinEntity> {
        return dataSource.getAll()
    }
}