package com.rahul.domain_module.usecases

import com.rahul.data_module.models.requests.GetCoinDetailRequest
import com.rahul.data_module.models.response.CoinEntityDetail
import com.rahul.data_module.repositories.CoinRepository
import com.rahul.data_module.source.ResultWrapper
import com.rahul.domain_module.core.UseCaseWithParams
import com.rahul.domain_module.exceptions.DomainExceptions
import com.rahul.domain_module.mappers.GetCoinDetailDataMapper
import com.rahul.domain_module.params.GetCoinDetailParam
import javax.inject.Inject

class GetCoinsDetailUseCase @Inject constructor(private val coinRepository: CoinRepository) :
    UseCaseWithParams<GetCoinDetailParam, CoinEntityDetail, String>() {

    private val mapper = GetCoinDetailDataMapper()


    override suspend fun buildUseCase(params: GetCoinDetailParam): ResultWrapper<DomainExceptions, String> {
        val request = GetCoinDetailRequest(params.currency ?: "")

        return coinRepository.getCoinDetail(request)
            .mapResponse(mapper)
    }


}
