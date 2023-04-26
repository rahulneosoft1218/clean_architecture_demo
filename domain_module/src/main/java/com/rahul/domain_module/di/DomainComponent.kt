package com.rahul.domain_module.di

import com.rahul.data_module.source.cache.IAppCache
import com.rahul.data_module.source.network.retrofit.OkhttpConfiguration
import com.rahul.domain_module.usecases.GetAllCoinsUseCase
import com.rahul.domain_module.usecases.GetCoinsDetailUseCase
import com.rahul.domain_module.test.TestUseCases
import dagger.BindsInstance
import dagger.Component
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
            @BindsInstance okhttpConfiguration: OkhttpConfiguration,
            @BindsInstance appcache: IAppCache
        ): DomainComponent
    }

    @Singleton
    fun inject(testUseCases : TestUseCases)

}
