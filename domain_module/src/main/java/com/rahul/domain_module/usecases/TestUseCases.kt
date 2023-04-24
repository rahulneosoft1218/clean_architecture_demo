package com.rahul.domain_module.usecases

import com.rahul.domain_module.core.UseCaseWrapper
import com.rahul.domain_module.di.DaggerDomainComponent
import com.rahul.domain_module.di.DomainComponent
import com.rahul.domain_module.exceptions.DomainExceptions
import com.rahul.domain_module.exceptions.DomainExceptions.Companion.mapDomainException
import kotlinx.coroutines.*
import javax.inject.Inject

abstract class TestUseCases {

    @Inject
    lateinit var getAllCoinsUseCase: GetAllCoinsUseCase

    @Inject
    lateinit var getCoinsDetailUseCase: GetCoinsDetailUseCase


    abstract fun <T> checkUseCondition(
        message: String,
        useCaseWrapper: UseCaseWrapper<DomainExceptions, T>,
        match: (UseCaseWrapper<DomainExceptions, T>) -> Boolean
    )

    private val jobs = ArrayList<Job>()

    open fun onCreate() {
        val domainComponent: DomainComponent = DaggerDomainComponent
            .factory().create("https://api.coingecko.com/", null)

        domainComponent.inject(this)
    }

    protected fun <T> executeUseCase(useCase: suspend () -> UseCaseWrapper<DomainExceptions, T>): UseCaseWrapper<DomainExceptions, T> =
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

    open fun onTerminate() {
        jobs.forEach {
            it.cancel()
        }
    }

}