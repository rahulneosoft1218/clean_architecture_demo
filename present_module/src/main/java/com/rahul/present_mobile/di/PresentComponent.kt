package com.rahul.present_mobile.di

import com.rahul.present_mobile.core.InjectActivity
import com.rahul.present_mobile.viewModelFactories.CoinViewModelFactory
import dagger.BindsInstance
import dagger.Component
import okhttp3.Interceptor
import javax.inject.Named
import javax.inject.Singleton

//@Singleton
@Component(modules = [PresentVMModule::class])
interface PresentComponent {

//    @Singleton
//    fun getCoinViewModelFactory(): CoinViewModelFactory


    @Component.Factory
    interface Factory {
        fun create(
            @Named("baseUrl") @BindsInstance baseUrl: String,
            @BindsInstance interceptors: List<Interceptor>?,
        ): PresentComponent
    }

}