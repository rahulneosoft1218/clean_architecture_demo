package com.rahul.present_mobile.di

import android.app.Activity
import com.rahul.present_mobile.viewmodels.CoinViewModel
import com.rahul.present_mobile.viewmodels.TestViewModels
import dagger.BindsInstance
import dagger.Component
import okhttp3.Interceptor
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@Component(modules = [PresentDomainModule::class, PresentVMModule::class])
interface PresentComponent {

    ////    @Binds
//    @IntoMap
//    @ViewModelKey(CoinViewModel::class)
    @Singleton
    fun getCoinViewModel(): CoinViewModel


    @Component.Factory
    interface Factory {
        fun create(
            @Named("baseUrl") @BindsInstance baseUrl: String,
            @BindsInstance interceptors: List<Interceptor>?,
        ): PresentComponent
    }

    fun inject(activity: Activity)

}