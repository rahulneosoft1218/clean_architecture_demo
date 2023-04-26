package com.rahul.cleandemo.di.modules

import androidx.lifecycle.ViewModelProvider
import com.rahul.cleandemo.viewModelFactories.CoinViewModelFactory
import com.rahul.domain_module.di.DomainModule
import com.rahul.domain_module.usecases.GetAllCoinsUseCase
import com.rahul.domain_module.usecases.GetCoinsDetailUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Suppress("UNCHECKED_CAST")
@Module(includes = [DomainModule::class])
class VMFactoryModule {


    @Singleton
    @Provides
    fun getCoinViewModelFactory(
        getAllCoinsUseCase: GetAllCoinsUseCase,
        getCoinsDetailUseCase: GetCoinsDetailUseCase
    ): ViewModelProvider.Factory {
        return CoinViewModelFactory(getAllCoinsUseCase, getCoinsDetailUseCase)
    }


}