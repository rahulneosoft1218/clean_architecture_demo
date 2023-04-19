package com.rahul.data_module.repositories

import com.rahul.data_module.di.DataComponent
import com.rahul.data_module.models.requests.GetAllCoinRequest
import com.rahul.data_module.models.requests.GetCoinDetailRequest
import com.rahul.data_module.source.ResultWrapper
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test


class CoinRepositoryTest : TestRepository() {

    private lateinit var repository: CoinRepository


    override fun onCreate(dataComponent: DataComponent) {
        repository = dataComponent.getCoinRepository()
    }

    @Test
    fun `api-(get all coins)`() {

        val request = GetAllCoinRequest(
            "usd",
            "market_cap_desc",
            1,
            2,
            false
        )
        val data = executeApi { repository.getAllCoins(request) }
        if (data is ResultWrapper.Success) {
            assertThat("All Coins found!", data.value.isNotEmpty())
        }
    }

    @Test
    fun `api-(get all detail)`() {

        val request = GetCoinDetailRequest("ethereum")
        val data = executeApi { repository.getCoinDetail(request) }
        if (data is ResultWrapper.Success) {
            assertThat("Coin Detail found!", data.value.id == "ethereum")
        }
    }


}