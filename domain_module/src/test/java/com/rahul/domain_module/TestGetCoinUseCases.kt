package com.rahul.domain_module

import com.rahul.data_module.source.ResultWrapper
import com.rahul.domain_module.di.DomainComponent
import com.rahul.domain_module.params.GetCoinDetailParam
import com.rahul.domain_module.usecases.GetAllCoinsUseCase
import com.rahul.domain_module.usecases.GetCoinsDetailUseCase
import org.hamcrest.MatcherAssert
import org.junit.Test

class TestGetCoinUseCases : TestUseCases() {

    private lateinit var getAllCoinsUseCase: GetAllCoinsUseCase
    private lateinit var getCoinsDetailUseCase: GetCoinsDetailUseCase


    override fun onCreate(domainComponent: DomainComponent) {
        getAllCoinsUseCase = domainComponent.getAllCoinUseCase()
        getCoinsDetailUseCase = domainComponent.getCoinDetailUseCase()
    }

    @Test
    fun `useCase-(get all coins)`() {

        val data = executeUseCase { getAllCoinsUseCase.buildUseCase() }
        if (data is ResultWrapper.Success) {
            MatcherAssert.assertThat("All Coins found!", data.value.isNotEmpty())
        }
    }

    @Test
    fun `api-(get all detail)`() {

        val data = executeUseCase { getCoinsDetailUseCase.buildUseCase(GetCoinDetailParam("usd")) }
        if (data is ResultWrapper.Success) {
            MatcherAssert.assertThat("Coin Detail found!", data.value.isNotEmpty())
        }
    }


}