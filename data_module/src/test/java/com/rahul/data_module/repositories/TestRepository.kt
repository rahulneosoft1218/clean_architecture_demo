package com.rahul.data_module.repositories

import com.rahul.data_module.di.DaggerDataComponent
import com.rahul.data_module.di.DataComponent
import com.rahul.data_module.di.DataModule
import com.rahul.data_module.source.ResultWrapper
import com.rahul.data_module.source.exceptions.ApiException
import com.rahul.data_module.source.exceptions.ApiException.Companion.mapToApiException
import kotlinx.coroutines.*
import org.junit.After
import org.junit.Before


abstract class TestRepository {

    private val jobs = ArrayList<Job>()

    abstract fun onCreate(dataComponent: DataComponent)

    @Before
    fun init() {

        val dataComponent: DataComponent = DaggerDataComponent
            .builder()
            .dataModule(DataModule())
            .build()

        onCreate(dataComponent)
    }


    fun <T> executeApi(api: suspend () -> ResultWrapper<ApiException, T>): ResultWrapper<ApiException, T> =
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

    @After
    fun onTerminate() {
        jobs.forEach {
            it.cancel()
        }
    }


}