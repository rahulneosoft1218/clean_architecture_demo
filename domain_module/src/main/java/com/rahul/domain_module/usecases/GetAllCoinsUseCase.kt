package com.rahul.domain_module.usecases

import com.rahul.data_module.models.requests.GetAllCoinRequest
import com.rahul.data_module.models.response.CoinEntity
import com.rahul.data_module.repositories.CoinRepository
import com.rahul.data_module.source.ResultWrapper
import com.rahul.domain_module.core.UseCase
import com.rahul.domain_module.exceptions.DomainExceptions
import com.rahul.domain_module.mappers.GetAllCoinDataMapper
import com.rahul.domain_module.responses.GetAllCoinResponse
import javax.inject.Inject

class GetAllCoinsUseCase @Inject constructor(private val coinRepository: CoinRepository) :
    UseCase<List<CoinEntity>, List<GetAllCoinResponse>>() {

    private val mapper = GetAllCoinDataMapper()

    override suspend fun buildUseCase(): ResultWrapper<DomainExceptions, List<GetAllCoinResponse>> {
        val request = GetAllCoinRequest(
            "usd",
            "market_cap_desc",
            1,
            2,
            false
        )

        return coinRepository.getAllCoins(request)
            .mapResponse(mapper)
    }


}
