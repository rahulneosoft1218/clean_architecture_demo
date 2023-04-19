package com.rahul.domain_module.mappers

import com.rahul.data_module.models.response.CoinEntity
import com.rahul.domain_module.core.UseCaseDataMapper
import com.rahul.domain_module.responses.GetAllCoinResponse

class GetAllCoinDataMapper : UseCaseDataMapper<List<CoinEntity>, List<GetAllCoinResponse>>() {
    override fun mapResultWithData(result: List<CoinEntity>?): List<GetAllCoinResponse> {
        val response = ArrayList<GetAllCoinResponse>()

        result?.forEach {
            it.mapCoinEntityToResponse()?.let { response.add(it) }
        }



        return response
    }


    private fun CoinEntity?.mapCoinEntityToResponse(): GetAllCoinResponse? {

        if (this == null) return null

        val data = this
        return GetAllCoinResponse(
            data.id ?: "",
            data.symbol ?: "",
            data.name ?: "",
            data.image ?: "",
            data.current_price ?: 0.0,
        )

    }
}