package com.rahul.present_mobile.di


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rahul.present_mobile.core.BaseViewModel
import com.rahul.present_mobile.viewmodels.CoinViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [PresentDomainModule::class])
abstract class PresentVMModule  {


 @Binds
 internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

 @Binds
 @IntoMap
 @ViewModelKey(CoinViewModel::class)
 internal abstract fun bindCoinViewModel(viewModel: CoinViewModel): ViewModel


}