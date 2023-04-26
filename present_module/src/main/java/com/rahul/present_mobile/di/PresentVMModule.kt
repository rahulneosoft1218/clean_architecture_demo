package com.rahul.present_mobile.di


import androidx.lifecycle.ViewModelProvider
import com.rahul.present_mobile.viewModelFactories.CoinViewModelFactory
import dagger.Binds
import dagger.Module

@Module(includes = [VMFactoryModule::class])
abstract class PresentVMModule {

/*
    @Binds
    internal abstract fun bindCoinViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory


    @Binds
    @IntoMap
    @ViewModelKey(CoinViewModel::class)
    internal abstract fun bindCoinViewModel(viewModel: CoinViewModel): ViewModel

*/


    @Binds
    abstract fun bindCoinViewModelFactory(factory: CoinViewModelFactory): ViewModelProvider.Factory


}