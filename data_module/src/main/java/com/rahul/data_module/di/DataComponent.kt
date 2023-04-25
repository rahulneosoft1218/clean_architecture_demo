package com.rahul.data_module.di

import com.rahul.data_module.repositories.CoinRepository
import com.rahul.data_module.repositories.TestDataRepositories
import dagger.BindsInstance
import dagger.Component
import okhttp3.Interceptor
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
            @BindsInstance interceptors: List<Interceptor>?
        ): DataComponent

    }

/*
    @Component.Builder
    interface Builder {
//        @BindsInstance
//        fun baseUrl(@Named("baseUrl") baseUrl: String): Builder
////        fun extraInterceptors( @Named("baseUrl2") extraInterceptors: List<Interceptor>?): Builder


        fun dataModule( @BindsInstance dataModule: DataModule): Builder
        fun build(): DataComponent

//        fun create(
//            @BindsInstance dataModule: DataModule,
//        ): DataComponent

    }
*/


}