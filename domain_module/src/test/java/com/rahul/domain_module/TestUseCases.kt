package com.rahul.domain_module

import com.rahul.data_module.di.DataModule
import com.rahul.data_module.source.ResultWrapper
import com.rahul.domain_module.di.DaggerDomainComponent
import com.rahul.domain_module.di.DomainComponent
import com.rahul.domain_module.exceptions.DomainExceptions
import kotlinx.coroutines.*
import org.junit.After
import org.junit.Before


abstract class TestUseCases {

    private val jobs = ArrayList<Job>()

    abstract fun onCreate(domainComponent: DomainComponent)

    @Before
    fun init() {


        val domainComponent: DomainComponent =
            DaggerDomainComponent.builder()
                .dataModule(DataModule()).build()

        onCreate(domainComponent)
    }


    fun <T> executeUseCase(useCase: suspend () -> ResultWrapper<DomainExceptions, T>): ResultWrapper<DomainExceptions, T> =
        runBlocking {
            val useCaseCall: Deferred<ResultWrapper<DomainExceptions, T>> = async(Dispatchers.IO) {
                useCase.invoke()
            }

            jobs.add(useCaseCall)
            return@runBlocking try {
                useCaseCall.await()
            } catch (th: Throwable) {
                ResultWrapper.Error(th as DomainExceptions)
            }

        }

    @After
    fun onTerminate() {
        jobs.forEach {
            it.cancel()
        }
    }


}