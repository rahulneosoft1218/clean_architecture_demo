package com.rahul.present_mobile.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.rahul.domain_module.core.UseCaseWrapper
import com.rahul.domain_module.usecases.GetAllCoinsUseCase
import com.rahul.present_mobile.core.BaseViewModel
import com.rahul.present_mobile.core.ResponseData
import com.rahul.present_mobile.di.VMScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@VMScope
class GetAllCoinViewModel @Inject constructor(private val getAllCoinsUseCase: GetAllCoinsUseCase) :
    BaseViewModel() {

    private val _allCoins = MutableLiveData<ResponseData>(ResponseData.Init)
    val allCoins = _allCoins

    fun fetchAllCoin() {

        viewModelScope.launch(Dispatchers.IO) {

            when (val result = getAllCoinsUseCase.buildUseCase()) {
                is UseCaseWrapper.Success -> _allCoins.value = ResponseData.Success(result.value)
                is UseCaseWrapper.Error -> _allCoins.value = ResponseData.Failed(result.error)
            }
        }


    }


}