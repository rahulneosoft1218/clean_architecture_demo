package com.rahul.data_module.source

import com.rahul.data_module.models.response.CoinEntity
import com.rahul.data_module.models.response.CoinEntityDetail
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("{endPath}")
    suspend fun getAllCoins(
        @Path("endPath", encoded = true) path: String,
        @Query("vs_currency") vs_currency: String,
        @Query("order") order: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int,
        @Query("sparkline") sparkline: Boolean
    ): List<CoinEntity>


    @GET("{endPath}")
    suspend fun getCoinDetail(
        @Path("endPath", encoded = true) path: String
    ): CoinEntityDetail
}