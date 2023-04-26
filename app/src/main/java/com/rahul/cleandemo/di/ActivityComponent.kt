package com.rahul.cleandemo.di

import com.rahul.cleandemo.CoinListActivity
import com.rahul.present_mobile.di.PresentComponent
import com.rahul.present_mobile.di.PresentVMModule
import dagger.BindsInstance
import dagger.Component
import dagger.Subcomponent
import okhttp3.Interceptor
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@Subcomponent(modules = [PresentVMModule::class])
interface ActivityComponent {

    // Factory that is used to create instances of this subcomponent

    @Subcomponent.Factory
    interface Factory {
        fun create(
            @Named("baseUrl") @BindsInstance baseUrl: String,
            @BindsInstance interceptors: List<Interceptor>?,
        ): ActivityComponent
    }

    fun inject(coinListActivity: CoinListActivity)

}