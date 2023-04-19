package com.rahul.domain_module.mappers

import com.rahul.data_module.models.response.CoinEntityDetail
import com.rahul.domain_module.core.UseCaseDataMapper

class GetCoinDetailDataMapper : UseCaseDataMapper<CoinEntityDetail, String>() {


    override fun mapResultWithData(result: CoinEntityDetail?): String {
        return result?.description?.en ?: ""
    }
}