package com.rahul.present_mobile.di


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rahul.domain_module.usecases.GetAllCoinsUseCase
import com.rahul.domain_module.usecases.GetCoinsDetailUseCase
import com.rahul.present_mobile.viewmodels.CoinViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [PresentDomainModule::class])
class VMFactoryModule {

    @Singleton
    @Provides
    fun getCoinViewModel(
        getAllCoinsUseCase: GetAllCoinsUseCase,
        getCoinsDetailUseCase: GetCoinsDetailUseCase
    ): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return CoinViewModel(getAllCoinsUseCase, getCoinsDetailUseCase) as T
            }
        }
    }


}