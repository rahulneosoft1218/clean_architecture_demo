package com.rahul.cleandemo.di

import com.rahul.cleandemo.CoinListActivity
import com.rahul.cleandemo.di.modules.VMFactoryModule
import com.rahul.data_module.source.cache.IAppCache
import com.rahul.data_module.source.network.retrofit.OkhttpConfiguration
import dagger.BindsInstance
import dagger.Component
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@Component(modules = [VMFactoryModule::class])
interface AppComponent {


    @Component.Factory
    interface Factory {
        fun create(
            @Named("baseUrl") @BindsInstance baseUrl: String,
            @BindsInstance okhttpConfiguration: OkhttpConfiguration,
            @BindsInstance appCache: IAppCache
        ): AppComponent
    }

    fun inject(coinListActivity: CoinListActivity)

}