package com.rahul.present_mobile.viewmodels

import com.rahul.domain_module.core.UseCaseWrapper
import com.rahul.domain_module.params.GetCoinDetailParam
import com.rahul.domain_module.usecases.GetAllCoinsUseCase
import com.rahul.domain_module.usecases.GetCoinsDetailUseCase
import com.rahul.present_mobile.core.BaseViewModel
import com.rahul.present_mobile.core.ResponseData
import com.rahul.present_mobile.di.VMScope
import javax.inject.Inject

@VMScope
class CoinViewModel @Inject constructor(
    private val getAllCoinsUseCase: GetAllCoinsUseCase,
    private val getCoinsDetailUseCase: GetCoinsDetailUseCase
) :
    BaseViewModel() {

    private val _allCoins = createNewResponseData()
    val allCoins = _allCoins


    private val _coinDetail = createNewResponseData()
    val coinDetail = _coinDetail

    fun fetchAllCoin() {

        executeUseCaseData({
            updateDataState(_allCoins, ResponseData.ShowLoading)
            getAllCoinsUseCase.buildUseCase()
        }) { result ->
            updateDataState(_allCoins, ResponseData.ShowLoading)
            when (result) {
                is UseCaseWrapper.Success -> _allCoins.value = ResponseData.Success(result.value)
                is UseCaseWrapper.Error -> _allCoins.value = ResponseData.Failed(result.error)
            }
        }
    }

    fun fetchCoinDetail(currency: String?) {

        executeUseCaseData({
            updateDataState(_coinDetail, ResponseData.ShowLoading)
            getCoinsDetailUseCase.buildUseCase(GetCoinDetailParam(currency))
        }) { result ->
            updateDataState(_allCoins, ResponseData.ShowLoading)
            when (result) {
                is UseCaseWrapper.Success -> _coinDetail.value = ResponseData.Success(result.value)
                is UseCaseWrapper.Error -> _coinDetail.value = ResponseData.Failed(result.error)
            }
        }
    }

}


