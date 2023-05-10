package com.rahul.cleandemo.viewModels

import androidx.lifecycle.viewModelScope
import com.rahul.cleandemo.base.BaseViewModel
import com.rahul.cleandemo.uiEvents.CoinListUIEvent
import com.rahul.data_module.source.mock.MockDataLoader
import com.rahul.domain_module.core.UseCaseWrapper
import com.rahul.domain_module.params.GetAllCoinsParam
import com.rahul.domain_module.responses.GetAllCoinResponse
import com.rahul.domain_module.usecases.GetAllCoinsUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CoinViewModel @Inject constructor(
    private val getAllCoinsUseCase: GetAllCoinsUseCase,
) : BaseViewModel() {

    private var allCoinsMockDataLoader : MockDataLoader?=null
    fun setAllCoinsMockDataLoader(mockDataLoader: MockDataLoader) {
        this.allCoinsMockDataLoader = allCoinsMockDataLoader
     }

    private val _allCoins =
        createResponseData<List<GetAllCoinResponse>, CoinListUIEvent>()
    val allCoins = _allCoins.mapResponseData()


    fun fetchAllCoin(page: Int = 1) {


        updateUIState(_allCoins, CoinListUIEvent.ShowLoader(true))
        executeUseCaseData({
            getAllCoinsUseCase.buildUseCase(viewModelScope, GetAllCoinsParam(page),allCoinsMockDataLoader)
        }) { result ->
            when (result) {
                is UseCaseWrapper.Success -> {
                    updateDataState(_allCoins, result.value)

                }
                is UseCaseWrapper.Error -> {
                    updateFailedDataState(_allCoins, result.error)
                }
            }




        }
    }

    override fun onPullToRefresh(refreshId: Int) {
        if (refreshId == 1) {
            fetchAllCoin(1)
        }
    }
}


