package com.rahul.data_module.repositories

import com.rahul.data_module.models.requests.GetAllCoinRequest
import com.rahul.data_module.models.requests.GetCoinDetailRequest
import com.rahul.data_module.models.response.CoinEntity
import com.rahul.data_module.models.response.CoinEntityDetail
import com.rahul.data_module.source.exceptions.ApiException
import com.rahul.data_module.source.network.INetworkData
import com.rahul.data_module.source.network.retrofit.ResultWrapper
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CoinRepository @Inject constructor(private val networkData: INetworkData) : DataRepository() {


    suspend fun getAllCoins(
        scope: CoroutineScope,
        request: GetAllCoinRequest
    ): ResultWrapper<ApiException, List<CoinEntity>> {
        return executeApi(scope) {
            networkData.fetchAllCoins(request)
        }
    }

    suspend fun getCoinDetail(
        scope: CoroutineScope,
        request: GetCoinDetailRequest
    ): ResultWrapper<ApiException, CoinEntityDetail> {
        return executeApi(scope) {
            networkData.fetchCoinDetail(request)
        }
    }

}