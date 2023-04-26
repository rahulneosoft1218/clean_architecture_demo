package com.rahul.present_mobile.viewmodels

import com.rahul.domain_module.core.UseCaseWrapper
import com.rahul.domain_module.params.GetCoinDetailParam
import com.rahul.domain_module.responses.GetAllCoinResponse
import com.rahul.domain_module.usecases.GetAllCoinsUseCase
import com.rahul.domain_module.usecases.GetCoinsDetailUseCase
import com.rahul.present_mobile.core.BaseViewModel
import com.rahul.present_mobile.core.ResponseData
import com.rahul.present_mobile.uiEvents.CoinDetailUIEvent
import com.rahul.present_mobile.uiEvents.CoinListUIEvent
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

        updateDataState(_allCoins, ResponseData.UIEvents(CoinListUIEvent.ShowLoader(true)))
        executeUseCaseData({
            getAllCoinsUseCase.buildUseCase()
        }) { result ->
            updateDataState(_allCoins, ResponseData.UIEvents(CoinListUIEvent.ShowLoader(false)))
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
            ResponseData.UIEvents(CoinListUIEvent.NavigateCoinDetail(coinId))
        )
    }

    fun fetchCoinDetail(currency: String?) {

        executeUseCaseData({
            updateDataState(
                _coinDetail,
                ResponseData.UIEvents(CoinDetailUIEvent.ShowLoader(true, currency ?: ""))
            )
            getCoinsDetailUseCase.buildUseCase(GetCoinDetailParam(currency))
        }) { result ->
            updateDataState(_coinDetail, ResponseData.UIEvents(CoinDetailUIEvent.ShowLoader(false)))
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


