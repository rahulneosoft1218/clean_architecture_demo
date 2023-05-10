package com.rahul.data_module.source.network.retrofit

import com.rahul.data_module.models.requests.GetAllCoinRequest
import com.rahul.data_module.models.requests.GetCoinDetailRequest
import com.rahul.data_module.models.response.CoinEntity
import com.rahul.data_module.models.response.CoinEntityDetail
import com.rahul.data_module.source.cache.IAppCache
import com.rahul.data_module.source.network.INetworkData
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitApiClient @Inject constructor(
    apiUrl: String,
    client: OkHttpClient,
    private val appCache: IAppCache
) : INetworkData {

    private val apiService: ApiService = Retrofit.Builder()
        .baseUrl(apiUrl)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(ApiService::class.java)


    override suspend fun fetchAllCoins(request: GetAllCoinRequest): List<CoinEntity> {
        val defaultCurr = appCache.getCacheString(IAppCache.DEFAULT_CURRENCY)
        return apiService.getAllCoins(
            path = request.path,
            vs_currency = request.currency,
//            vs_currency = defaultCurr,
            order = request.order,
            perPage = request.perPage,
            page = request.page,
            sparkline = request.sparkline
        )
    }

    override suspend fun fetchCoinDetail(request: GetCoinDetailRequest): CoinEntityDetail {
        return apiService.getCoinDetail(path = request.path)
    }


}