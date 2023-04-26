package com.rahul.data_module.repositories

import com.rahul.data_module.models.requests.GetAllCoinRequest
import com.rahul.data_module.models.requests.GetCoinDetailRequest
import com.rahul.data_module.source.exceptions.ApiException
import com.rahul.data_module.source.network.retrofit.ResultWrapper
import org.junit.Test


internal class CoinRepositoryTest : UnitTestRepositories() {

//    private val scope  = CoT


    @Test
    fun `api-checkNetworkException`() {

        val request = GetAllCoinRequest(
            "",
            "market_cap_desc",
            1,
            2,
            false
        )


        val data = executeApi {
            coinRepository.getAllCoins(testCoroutineScope, request)
        }

        checkApiCondition("Check Network Error", data) { result ->
            if (result is ResultWrapper.Error) {
                return@checkApiCondition result.error.resCode == -1
            }

            return@checkApiCondition false
        }

    }

    @Test
    fun `api-(get all coins)`() {

        val request = GetAllCoinRequest(
            "",
            "market_cap_desc",
            1,
            2,
            false
        )


        val data = executeApi { coinRepository.getAllCoins(testCoroutineScope, request) }

        checkApiCondition("All Coins Found", data) { result ->
            if (result is ResultWrapper.Success) {
                return@checkApiCondition result.value.isNotEmpty()
            }

            return@checkApiCondition false
        }

    }


    @Test
    fun `api-(get all coins - invalid currency)`() {

        val request = GetAllCoinRequest(
            "",
            "market_cap_desc",
            1,
            2,
            false
        )


        val data = executeApi { coinRepository.getAllCoins(testCoroutineScope, request) }
        checkApiCondition("Invalid currency", data) { result ->
            if (result is ResultWrapper.Error) {
                return@checkApiCondition result.error.errorMsg?.equals("invalid vs_currency") == true
            }

            return@checkApiCondition false
        }


    }

    @Test
    fun `api-(get coin detail)`() {

        val request = GetCoinDetailRequest("ethereum")
        val data = executeApi { coinRepository.getCoinDetail(testCoroutineScope, request) }

        checkApiCondition("Coin Detail found!", data) { result ->
            if (result is ResultWrapper.Success) {
                return@checkApiCondition result.value.id?.isNotEmpty() == true
            }

            return@checkApiCondition false
        }

    }

    @Test
    fun `api-(get coin detail - no coin found)`() {

        val request = GetCoinDetailRequest("ethereum11")
        val data = executeApi { coinRepository.getCoinDetail(testCoroutineScope, request) }

        checkApiCondition("Coin Detail Not found!", data) { result ->
            if (result is ResultWrapper.Error) {
                return@checkApiCondition result.error is ApiException.BadRequestException
            }

            return@checkApiCondition false
        }

    }


}