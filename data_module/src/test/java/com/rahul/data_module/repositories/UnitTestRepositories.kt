package com.rahul.data_module.repositories

import com.rahul.data_module.source.cache.IAppCache
import com.rahul.data_module.source.exceptions.ApiException
import com.rahul.data_module.source.network.retrofit.ResultWrapper
import com.rahul.data_module.test.TestDataCache
import com.rahul.data_module.test.TestDataOkhttpConfig
import com.rahul.data_module.test.TestDataRepositories
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before


abstract class UnitTestRepositories : TestDataRepositories("https://api.coingecko.com/") {

    protected val testCoroutineScope = CoroutineScope(Dispatchers.IO)
    override fun getAppCache() = TestDataCache.getTestDataCache()
    override fun getOkhttpConfig() = TestDataOkhttpConfig(internetOn = true)

    @Before
    override fun onCreate() {
        super.onCreate()
        getAppCache().cacheString(IAppCache.DEFAULT_CURRENCY, "usd")
    }

    override fun <T> checkApiCondition(
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
    override fun onTerminate() {
        super.onTerminate()
    }


}