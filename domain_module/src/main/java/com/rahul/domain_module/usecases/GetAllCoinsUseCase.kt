package com.rahul.domain_module.usecases

import com.rahul.data_module.models.requests.GetAllCoinRequest
import com.rahul.data_module.models.response.CoinEntity
import com.rahul.data_module.repositories.CoinRepository
import com.rahul.domain_module.core.UseCase
import com.rahul.domain_module.core.UseCaseWrapper
import com.rahul.domain_module.exceptions.DomainExceptions
import com.rahul.domain_module.mappers.GetAllCoinDataMapper
import com.rahul.domain_module.responses.GetAllCoinResponse
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetAllCoinsUseCase @Inject constructor(private val coinRepository: CoinRepository) :
    UseCase<List<CoinEntity>, List<GetAllCoinResponse>>() {

    private val mapper = GetAllCoinDataMapper()

    override suspend fun buildUseCase(scope: CoroutineScope): UseCaseWrapper<DomainExceptions, List<GetAllCoinResponse>> {
        return executeUseCase(mapper) {
            val request = GetAllCoinRequest(
                "usd",
                "market_cap_desc",
                2,
                1,
                false
            )
            coinRepository.getAllCoins(scope, request)
        }
    }


}

