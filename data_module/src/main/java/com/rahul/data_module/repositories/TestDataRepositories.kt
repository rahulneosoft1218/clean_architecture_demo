package com.rahul.data_module.repositories

import com.rahul.data_module.di.DaggerDataComponent
import com.rahul.data_module.di.DataComponent
import com.rahul.data_module.source.ResultWrapper
import com.rahul.data_module.source.exceptions.ApiException
import com.rahul.data_module.source.exceptions.ApiException.Companion.mapToApiException
import kotlinx.coroutines.*
import javax.inject.Inject

abstract class TestDataRepositories {

    @Inject
    lateinit var coinRepository: CoinRepository


    private val jobs = ArrayList<Job>()

    abstract fun <T> checkApiCondition(
        message: String,
        resultWrapper: ResultWrapper<ApiException, T>,
        match: (ResultWrapper<ApiException, T>) -> Boolean
    )

    open fun onCreate() {
        val dataComponent: DataComponent = DaggerDataComponent
            .factory()
            .create("https://api.coingecko.com/", null)

        dataComponent.inject(this)
    }


    protected fun <T> executeApi(api: suspend () -> ResultWrapper<ApiException, T>): ResultWrapper<ApiException, T> =
        runBlocking {
            val apiCall: Deferred<ResultWrapper<ApiException, T>> = async(Dispatchers.IO) {
                api.invoke()
            }

            jobs.add(apiCall)
            return@runBlocking try {
                apiCall.await()
            } catch (th: Throwable) {
                ResultWrapper.Error(th.mapToApiException())
            }

        }


    open fun onTerminate() {
        jobs.forEach {
            it.cancel()
        }
    }
}