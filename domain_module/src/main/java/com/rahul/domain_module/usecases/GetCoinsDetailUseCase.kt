package com.rahul.domain_module.usecases

import com.rahul.data_module.models.requests.GetCoinDetailRequest
import com.rahul.data_module.models.response.CoinEntityDetail
import com.rahul.data_module.repositories.CoinRepository
import com.rahul.data_module.source.mock.MockDataLoader
import com.rahul.domain_module.core.UseCaseWithParams
import com.rahul.domain_module.core.UseCaseWrapper
import com.rahul.domain_module.exceptions.DomainExceptions
import com.rahul.domain_module.mappers.GetCoinDetailDataMapper
import com.rahul.domain_module.params.GetCoinDetailParam
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetCoinsDetailUseCase @Inject constructor(private val coinRepository: CoinRepository) :
    UseCaseWithParams<GetCoinDetailParam, CoinEntityDetail, String>() {

    private val mapper = GetCoinDetailDataMapper()

    override suspend fun buildUseCase(
        scope: CoroutineScope,
        params: GetCoinDetailParam,
        mockDataLoader: MockDataLoader?,
    ): UseCaseWrapper<DomainExceptions, String> {
        return executeUseCase(mapper) {
            val request = GetCoinDetailRequest(params.currency ?: "")
            coinRepository.getCoinDetail(scope, request)
        }
    }


}

