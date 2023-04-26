package com.rahul.domain_module

import com.rahul.domain_module.core.UseCaseWrapper
import com.rahul.domain_module.exceptions.DomainErrors
import com.rahul.domain_module.params.GetCoinDetailParam
import com.rahul.domain_module.test.TestDomainCache
import com.rahul.domain_module.test.TestDomainOkhttpConfig
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class TestGetCoinUseCases : UnitTestUseCases() {


    override fun getOkhttpConfig() = TestDomainOkhttpConfig(internetOn = true)

    override fun getAppCache() = TestDomainCache.getTestDomainCache()


    @Test
    fun `useCase-checkNetworkError`() {

        val data = executeUseCase { getAllCoinsUseCase.buildUseCase(scope = testCoroutineScope) }

        checkUseCondition("NO_INTERNET", data) { result ->
            if (result is UseCaseWrapper.Error) {
                return@checkUseCondition result.error.domainErrors.ordinal == DomainErrors.NO_INTERNET.ordinal
            }

            return@checkUseCondition result !is UseCaseWrapper.Success
        }

    }

    @Test
    fun `useCase-(get all coins)`() {

        val data = executeUseCase { getAllCoinsUseCase.buildUseCase(scope = testCoroutineScope) }

        checkUseCondition("All Coins found!", data) { result ->
            if (result is UseCaseWrapper.Success) {
                return@checkUseCondition result.value.isNotEmpty()
            }

            return@checkUseCondition false
        }

    }

    @Test
    fun `useCase-(get coin detail)`() {

        val data = executeUseCase {
            getCoinsDetailUseCase.buildUseCase(
                scope = testCoroutineScope,
                params = GetCoinDetailParam(currency = "usd")
            )
        }

        checkUseCondition("Coin Detail found!", data) { result ->
            if (result is UseCaseWrapper.Success) {
                return@checkUseCondition result.value.isNotEmpty()
            }

            return@checkUseCondition false
        }
    }

    @Test
    fun `useCase-(get coin detail - coin not found)`() {

        val data =
            executeUseCase {
                getCoinsDetailUseCase.buildUseCase(
                    scope = testCoroutineScope,
                    params = GetCoinDetailParam(currency = "ethereum11")
                )
            }

        checkUseCondition("Coin Detail not found!", data) { result ->
            if (result is UseCaseWrapper.Error) {
                return@checkUseCondition result.error.domainErrors == DomainErrors.BAD_REQUEST
            }

            return@checkUseCondition false
        }
    }


}