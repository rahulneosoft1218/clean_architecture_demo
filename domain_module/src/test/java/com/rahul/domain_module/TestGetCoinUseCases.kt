package com.rahul.domain_module

import com.rahul.domain_module.core.UseCaseWrapper
import com.rahul.domain_module.di.DomainComponent
import com.rahul.domain_module.params.GetCoinDetailParam
import com.rahul.domain_module.usecases.GetAllCoinsUseCase
import com.rahul.domain_module.usecases.GetCoinsDetailUseCase
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

        checkUseCondition("All Coins found!", data) { result ->
            if (result is UseCaseWrapper.Success) {
                return@checkUseCondition result.value.isNotEmpty()
            }

            return@checkUseCondition false
        }

    }

    @Test
    fun `useCase-(get coin detail)`() {

        val data = executeUseCase { getCoinsDetailUseCase.buildUseCase(GetCoinDetailParam("usd")) }

        checkUseCondition("Coin Detail found!", data) { result ->
            if (result is UseCaseWrapper.Success) {
                return@checkUseCondition result.value.isNotEmpty()
            }

            return@checkUseCondition false
        }
    }

    @Test
    fun `useCase-(get coin detail - coin not found)`() {

        val data = executeUseCase { getCoinsDetailUseCase.buildUseCase(GetCoinDetailParam("ethereum11")) }

        checkUseCondition("Coin Detail not found!", data) { result ->
            if (result is UseCaseWrapper.Error) {
                return@checkUseCondition result.error.message == "coin not found"
            }

            return@checkUseCondition false
        }
    }


}