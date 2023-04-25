package com.rahul.domain_module.di

import com.rahul.domain_module.usecases.GetAllCoinsUseCase
import com.rahul.domain_module.usecases.GetCoinsDetailUseCase
import com.rahul.domain_module.usecases.TestUseCases
import dagger.BindsInstance
import dagger.Component
import okhttp3.Interceptor
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@Component(modules = [DomainModule::class])
interface DomainComponent {


    @Singleton
    fun getAllCoinUseCase(): GetAllCoinsUseCase

    @Singleton
    fun getCoinDetailUseCase(): GetCoinsDetailUseCase

    @Component.Factory
    interface Factory {
        fun create(
            @Named("baseUrl") @BindsInstance baseUrl: String,
            @BindsInstance interceptors: List<Interceptor>?,
        ): DomainComponent
    }

    @Singleton
    fun inject(testUseCases : TestUseCases)

}
