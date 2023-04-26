package com.rahul.data_module.source.network

import com.rahul.data_module.models.requests.GetAllCoinRequest
import com.rahul.data_module.models.requests.GetCoinDetailRequest
import com.rahul.data_module.models.response.CoinEntity
import com.rahul.data_module.models.response.CoinEntityDetail

interface INetworkData {

    suspend fun fetchAllCoins(request: GetAllCoinRequest) : List<CoinEntity>
    suspend fun fetchCoinDetail(request: GetCoinDetailRequest) : CoinEntityDetail

}