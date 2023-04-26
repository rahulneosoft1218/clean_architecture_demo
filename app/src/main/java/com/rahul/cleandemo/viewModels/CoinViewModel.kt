package com.rahul.cleandemo.viewModels

import androidx.lifecycle.viewModelScope
import com.rahul.cleandemo.base.BaseViewModel
import com.rahul.cleandemo.base.ResponseData
import com.rahul.cleandemo.uiEvents.CoinDetailUIEvent
import com.rahul.domain_module.core.UseCaseWrapper
import com.rahul.domain_module.params.GetCoinDetailParam
import com.rahul.domain_module.responses.GetAllCoinResponse
import com.rahul.domain_module.usecases.GetAllCoinsUseCase
import com.rahul.domain_module.usecases.GetCoinsDetailUseCase
import com.rahul.cleandemo.uiEvents.CoinListUIEvent
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CoinViewModel @Inject constructor(
    private val getAllCoinsUseCase: GetAllCoinsUseCase,
    private val getCoinsDetailUseCase: GetCoinsDetailUseCase
) : BaseViewModel() {

    private val _allCoins = createNewResponseData<List<GetAllCoinResponse>, CoinListUIEvent>()
    val allCoins = _allCoins


    private val _coinDetail = createNewResponseData<String, CoinDetailUIEvent>()
    val coinDetail = _coinDetail

    fun fetchAllCoin() {

        updateDataState(_allCoins, ResponseData.UIResponse(CoinListUIEvent.ShowLoader(true)))
        executeUseCaseData({
            getAllCoinsUseCase.buildUseCase(viewModelScope)
        }) { result ->
            updateDataState(_allCoins, ResponseData.UIResponse(CoinListUIEvent.ShowLoader(false)))
            when (result) {
                is UseCaseWrapper.Success -> updateDataState(
                    _allCoins,
                    ResponseData.Success(result.value)
                )
                is UseCaseWrapper.Error -> updateFailedDataState(
                    _allCoins,
                    ResponseData.Failed(result.error)
                )
            }
        }
    }


    fun onCoinItemClick(coinId: String?) {
        updateDataState(
            _allCoins,
            ResponseData.UIResponse(CoinListUIEvent.NavigateCoinDetail(coinId))
        )
    }

    fun fetchCoinDetail(currency: String?) {

        executeUseCaseData({
            updateDataState(
                _coinDetail,
                ResponseData.UIResponse(CoinDetailUIEvent.ShowLoader(true, currency ?: ""))
            )
            getCoinsDetailUseCase.buildUseCase(viewModelScope, GetCoinDetailParam(currency))
        }) { result ->
            updateDataState(_coinDetail, ResponseData.UIResponse(CoinDetailUIEvent.ShowLoader(false)))
            when (result) {
                is UseCaseWrapper.Success -> updateDataState(
                    _coinDetail,
                    ResponseData.Success(result.value)
                )
                is UseCaseWrapper.Error -> updateFailedDataState(
                    _coinDetail,
                    ResponseData.Failed(result.error)
                )
            }
        }
    }
}


