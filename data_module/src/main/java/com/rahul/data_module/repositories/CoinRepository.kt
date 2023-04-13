package com.rahul.data_module.repositories

import com.rahul.data_module.models.requests.GetAllCoinRequest
import com.rahul.data_module.models.response.CoinEntity
import com.rahul.data_module.source.ApiService
import javax.inject.Inject

class CoinRepository @Inject constructor(private val apiService: ApiService) {


    suspend fun getAllCoins(request: GetAllCoinRequest): List<CoinEntity> {
        return apiService.getAllCoins(
            request.path,
            request.currency,
            request.order,
            request.perPage,
            request.page,
            request.sparkline
        )
    }

}