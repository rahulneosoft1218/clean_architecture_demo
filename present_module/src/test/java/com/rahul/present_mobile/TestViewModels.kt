package com.rahul.present_mobile


import com.rahul.domain_module.core.UseCaseWrapper
 import com.rahul.domain_module.exceptions.DomainExceptions
import com.rahul.present_mobile.di.ViewModelComponent
import kotlinx.coroutines.*
import org.junit.After
import org.junit.Before


abstract class TestViewModels {

    private val jobs = ArrayList<Job>()

    abstract fun onCreate(viewModelComponent: ViewModelComponent)

    @Before
    fun init() {


//
//        val viewModelComponent: ViewModelComponent =
//            Dagger.builder().build()
//
//        onCreate(viewModelComponent)
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
                UseCaseWrapper.Error(th as DomainExceptions)
            }

        }

    @After
    fun onTerminate() {
        jobs.forEach {
            it.cancel()
        }
    }


}