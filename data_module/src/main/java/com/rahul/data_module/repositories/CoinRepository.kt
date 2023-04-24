package com.rahul.data_module.repositories

import com.rahul.data_module.di.DataScope
import com.rahul.data_module.models.requests.GetAllCoinRequest
import com.rahul.data_module.models.requests.GetCoinDetailRequest
import com.rahul.data_module.models.response.CoinEntity
import com.rahul.data_module.models.response.CoinEntityDetail
import com.rahul.data_module.source.ApiService
import com.rahul.data_module.source.ResultWrapper
import com.rahul.data_module.source.exceptions.ApiException
import javax.inject.Inject
import javax.inject.Singleton

@DataScope
class CoinRepository @Inject constructor(private val apiService: ApiService) : DataRepository() {


    suspend fun getAllCoins(request: GetAllCoinRequest): ResultWrapper<ApiException, List<CoinEntity>> {
        return executeApi {
            apiService.getAllCoins(
                request.path,
                request.currency,
                request.order,
                request.perPage,
                request.page,
                request.sparkline
            )
        }
    }

    suspend fun getCoinDetail(request: GetCoinDetailRequest): ResultWrapper<ApiException, CoinEntityDetail> {
        return executeApi {
            apiService.getCoinDetail(request.path)
        }
    }

}