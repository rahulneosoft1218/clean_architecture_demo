package com.rahul.cleandemo.di

import android.content.Context
import com.rahul.cleandemo.features.CoinListActivity
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
            @BindsInstance context: Context,
            @Named("baseUrl") @BindsInstance baseUrl: String,
            @BindsInstance okhttpConfiguration: OkhttpConfiguration,
            @BindsInstance appCache: IAppCache
        ): AppComponent
    }

    fun inject(coinListActivity: CoinListActivity)

}