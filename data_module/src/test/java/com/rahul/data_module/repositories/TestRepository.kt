package com.rahul.data_module.repositories

import com.rahul.data_module.di.DaggerDataComponent
import com.rahul.data_module.di.DataComponent
import com.rahul.data_module.di.DataModule
import com.rahul.data_module.source.ResultWrapper
import com.rahul.data_module.source.exceptions.ApiException
import com.rahul.data_module.source.exceptions.ApiException.Companion.mapToApiException
import kotlinx.coroutines.*
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before


abstract class TestRepository {

    private val jobs = ArrayList<Job>()

    abstract fun onCreate(dataComponent: DataComponent)

    @Before
    fun init() {

        val dataComponent: DataComponent = DaggerDataComponent
            .builder()
            .dataModule(DataModule("https://api.coingecko.com/"))
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


    fun <T> checkApiCondition(
        message: String,
        resultWrapper: ResultWrapper<ApiException, T>,
        match: (ResultWrapper<ApiException, T>) -> Boolean
    ) {

        assertThat(resultWrapper, object : Matcher<ResultWrapper<ApiException, T>> {
            override fun describeTo(description: Description?) {
                description?.appendText(message)
            }

            override fun matches(item: Any?): Boolean {

                if (item is ResultWrapper<*, *>) {
                    val result = item as ResultWrapper<ApiException, T>
                    return match(result)

                }

                return false
            }

            override fun describeMismatch(item: Any?, mismatchDescription: Description?) {
                mismatchDescription?.appendText(message)
            }

            @Deprecated("Deprecated in Java")
            override fun _dont_implement_Matcher___instead_extend_BaseMatcher_() {

            }

        })
    }


    @After
    fun onTerminate() {
        jobs.forEach {
            it.cancel()
        }
    }


}