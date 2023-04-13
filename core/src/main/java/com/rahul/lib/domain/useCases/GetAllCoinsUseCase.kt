package com.rahul.lib.domain.useCases

import com.rahul.lib.data.ICoinRepository
import com.rahul.lib.data.models.CoinEntity
import com.rahul.lib.domain.base.BaseUseCase

class GetAllCoinsUseCase(private val coinRepository: ICoinRepository) : BaseUseCase<List<CoinEntity>, Nothing>() {
    override suspend fun buildRequest(params: Nothing?): List<CoinEntity> {
        return coinRepository.getAll()
    }
}