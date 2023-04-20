package com.rahul.domain_module

import com.rahul.data_module.di.DaggerDataComponent
import com.rahul.data_module.di.DataModule
import com.rahul.data_module.source.ResultWrapper
import com.rahul.domain_module.core.UseCaseWrapper
import com.rahul.domain_module.di.DaggerDomainComponent
import com.rahul.domain_module.di.DomainComponent
import com.rahul.domain_module.exceptions.DomainExceptions
import com.rahul.domain_module.exceptions.DomainExceptions.Companion.mapDomainException
import kotlinx.coroutines.*
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before


abstract class TestUseCases {

    private val jobs = ArrayList<Job>()

    abstract fun onCreate(domainComponent: DomainComponent)

    @Before
    fun init() {

        val dataComponent = DaggerDataComponent
            .builder()
            .dataModule(DataModule("https://api.coingecko.com/"))
            .build()


        val domainComponent: DomainComponent = DaggerDomainComponent
            .builder()
            .dataComponent(dataComponent)
            .build()


        onCreate(domainComponent)
    }


    fun <T> executeUseCase(useCase: suspend () -> UseCaseWrapper<DomainExceptions, T>): UseCaseWrapper<DomainExceptions, T> =
        runBlocking {
            val useCaseCall: Deferred<UseCaseWrapper<DomainExceptions, T>> = async(Dispatchers.IO) {
                useCase.invoke()
            }

            jobs.add(useCaseCall)
            return@runBlocking try {
                useCaseCall.await()
            } catch (th: Throwable) {
                UseCaseWrapper.Error(th.mapDomainException())
            }

        }


    fun <T> checkUseCondition(
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
    fun onTerminate() {
        jobs.forEach {
            it.cancel()
        }
    }


}