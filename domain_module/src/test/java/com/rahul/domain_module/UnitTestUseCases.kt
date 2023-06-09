package com.rahul.domain_module

import com.rahul.data_module.source.cache.IAppCache
import com.rahul.domain_module.core.UseCaseWrapper
import com.rahul.domain_module.exceptions.DomainExceptions
import com.rahul.domain_module.test.TestUseCases
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before


abstract class UnitTestUseCases : TestUseCases("https://api.coingecko.com/") {

//    private val scope : CoroutineScope(Dis)
    private val jobs = ArrayList<Job>()


    @Before
    override fun onCreate() {
        super.onCreate()
        getAppCache().cacheString(IAppCache.DEFAULT_CURRENCY, "usd")

    }


   override fun <T> checkUseCondition(
        message: String,
        useCaseWrapper: UseCaseWrapper<DomainExceptions, T>,
        match: (UseCaseWrapper<DomainExceptions, T>) -> Boolean
    ) {

        MatcherAssert.assertThat(
            useCaseWrapper,
            object : Matcher<UseCaseWrapper<DomainExceptions, T>> {
                override fun describeTo(description: Description?) {
                    description?.appendText(message)
                }

                override fun matches(item: Any?): Boolean {

                    if (item is UseCaseWrapper<*, *>) {
                        val result = item as UseCaseWrapper<DomainExceptions, T>
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
        jobs.forEach {
            it.cancel()
        }
    }


}