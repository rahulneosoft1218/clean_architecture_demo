package com.rahul.cleandemo.viewModelFactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rahul.cleandemo.viewModels.CoinViewModel
import com.rahul.domain_module.usecases.GetAllCoinsUseCase
import com.rahul.domain_module.usecases.GetCoinsDetailUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CoinViewModelFactory @Inject constructor(
    private val getAllCoinsUseCase: GetAllCoinsUseCase,
    private val getCoinsDetailUseCase: GetCoinsDetailUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CoinViewModel(getAllCoinsUseCase, getCoinsDetailUseCase) as T
    }

}