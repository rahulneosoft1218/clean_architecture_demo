package com.rahul.data_module.di

import com.rahul.data_module.repositories.CoinRepository
import com.rahul.data_module.test.TestDataRepositories
import com.rahul.data_module.source.cache.IAppCache
import com.rahul.data_module.source.network.retrofit.OkhttpConfiguration
import dagger.BindsInstance
import dagger.Component
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class])
interface DataComponent {


//     fun get

    @Singleton
    fun getCoinRepository(): CoinRepository

    @Singleton
    fun inject(testDataRepository: TestDataRepositories)


    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance @Named("baseUrl") baseUrl: String,
            @BindsInstance okhttpConfiguration: OkhttpConfiguration,
            @BindsInstance appcache: IAppCache
        ): DataComponent

    }



}