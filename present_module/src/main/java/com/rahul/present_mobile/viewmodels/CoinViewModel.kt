package com.rahul.present_mobile.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rahul.domain_module.core.UseCaseWrapper
import com.rahul.domain_module.params.GetCoinDetailParam
import com.rahul.domain_module.responses.GetAllCoinResponse
import com.rahul.domain_module.usecases.GetAllCoinsUseCase
import com.rahul.domain_module.usecases.GetCoinsDetailUseCase
import com.rahul.present_mobile.core.BaseUIEvents
import com.rahul.present_mobile.core.ResponseData
import com.rahul.present_mobile.uiEvents.CoinListUIEvent
import com.rahul.present_mobile.utils.createNewResponseData
import com.rahul.present_mobile.utils.executeUseCaseData
import com.rahul.present_mobile.utils.updateDataState
import com.rahul.present_mobile.utils.updateFailedDataState
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CoinViewModel @Inject constructor(
    private val getAllCoinsUseCase: GetAllCoinsUseCase,
    private val getCoinsDetailUseCase: GetCoinsDetailUseCase
) :
    ViewModel() {

    private val _allCoins =
        ResponseData.Init.createNewResponseData<List<GetAllCoinResponse>, CoinListUIEvent>()
    val allCoins = _allCoins


    private val _coinDetail = ResponseData.Init.createNewResponseData<String, BaseUIEvents>()
    val coinDetail = _coinDetail

    fun fetchAllCoin() {

        _allCoins.updateDataState(ResponseData.UIEvents(CoinListUIEvent.ShowLoader(true)))
        viewModelScope.executeUseCaseData({
            getAllCoinsUseCase.buildUseCase()
        }) { result ->
            _allCoins.updateDataState(ResponseData.UIEvents(CoinListUIEvent.ShowLoader(false)))
            when (result) {
                is UseCaseWrapper.Success -> _allCoins.updateDataState(
                    ResponseData.Success(result.value)
                )
                is UseCaseWrapper.Error -> _allCoins.updateFailedDataState(
                    ResponseData.Failed(result.error)
                )
            }
        }
    }


    fun onCoinItemClick(coinId: String?) {
        _allCoins.updateDataState(
            ResponseData.UIEvents(CoinListUIEvent.NavigateCoinDetail(coinId))
        )
    }

    fun fetchCoinDetail(currency: String?) {

        viewModelScope.executeUseCaseData({
//            updateDataState(_allCoins, ResponseData.UIEvents(BaseUIEvents.ShowLoading))
            getCoinsDetailUseCase.buildUseCase(GetCoinDetailParam(currency))
        }) { result ->
//            updateDataState(_allCoins, ResponseData.UIEvents(BaseUIEvents.HideLoading))
//            when (result) {
//                is UseCaseWrapper.Success -> updateDataState(
//                    _allCoins,
//                    ResponseData.Success(result.value)
//                )
//                is UseCaseWrapper.Error -> updateFailedDataState(
//                    _allCoins,
//                    ResponseData.Failed(result.error)
//                )
//            }
        }
    }
}


