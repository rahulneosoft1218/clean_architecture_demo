package com.rahul.domain_module.di

import com.rahul.data_module.di.DataScope
import com.rahul.domain_module.usecases.GetAllCoinsUseCase
import com.rahul.domain_module.usecases.GetCoinsDetailUseCase
import com.rahul.domain_module.usecases.TestUseCases
import dagger.BindsInstance
import dagger.Component
import okhttp3.Interceptor
import javax.inject.Named

@DataScope
@Component(modules = [DomainModule::class])
interface DomainComponent {


    @DataScope
    fun getAllCoinUseCase(): GetAllCoinsUseCase

    @DataScope
    fun getCoinDetailUseCase(): GetCoinsDetailUseCase

    @Component.Factory
    interface Factory {
        fun create(
            @Named("baseUrl") @BindsInstance baseUrl: String,
            @BindsInstance interceptors: List<Interceptor>?,
        ): DomainComponent
    }

    @DataScope
    fun inject(testUseCases : TestUseCases)

}
