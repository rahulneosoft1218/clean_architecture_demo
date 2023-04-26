package com.rahul.data_module.repositories

import com.rahul.data_module.di.DaggerDataComponent
import com.rahul.data_module.di.DataComponent
import com.rahul.data_module.source.cache.IAppCache
import com.rahul.data_module.source.exceptions.ApiException
import com.rahul.data_module.source.exceptions.ApiException.Companion.mapToApiException
import com.rahul.data_module.source.network.retrofit.NetworkCheckInterceptor
import com.rahul.data_module.source.network.retrofit.OkhttpConfiguration
import com.rahul.data_module.source.network.retrofit.ResultWrapper
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import javax.inject.Inject

abstract class TestDataRepositories(private val baseUrl: String) {


    abstract fun mockNetworkCheckInterceptor(): NetworkCheckInterceptor
    abstract fun getAppCache(): IAppCache

    @Inject
    lateinit var coinRepository: CoinRepository


    private val jobs = ArrayList<Job>()

    abstract fun <T> checkApiCondition(
        message: String,
        resultWrapper: ResultWrapper<ApiException, T>,
        match: (ResultWrapper<ApiException, T>) -> Boolean
    )

    open fun onCreate() {

        val okhttpConfiguration = object : OkhttpConfiguration() {
            override fun getNetworkCheckInterceptor() = mockNetworkCheckInterceptor()

            override fun configOkhttpClient(builder: OkHttpClient.Builder) = builder

        }

        val dataComponent: DataComponent = DaggerDataComponent
            .factory()
            .create(
                baseUrl = baseUrl,
                okhttpConfiguration = okhttpConfiguration,
                appcache = getAppCache()
            )
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