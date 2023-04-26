package com.rahul.cleandemo

import com.rahul.cleandemo.base.BaseActivity
import com.rahul.cleandemo.base.ResponseData
import com.rahul.cleandemo.databinding.ActivityCoinBinding
import com.rahul.cleandemo.di.AppComponent
import com.rahul.cleandemo.uiEvents.CoinDetailUIEvent
import com.rahul.cleandemo.uiEvents.CoinListUIEvent
import com.rahul.cleandemo.viewModelFactories.CoinViewModelFactory
import com.rahul.cleandemo.viewModels.CoinViewModel
import javax.inject.Inject

class CoinListActivity @Inject constructor() :
    BaseActivity<ActivityCoinBinding>(layoutID = R.layout.activity_coin) {


    @Inject
    lateinit var coinViewModelFactory: CoinViewModelFactory

    private lateinit var coinViewModel: CoinViewModel

    override fun initViewModels(appComponent: AppComponent) {
        appComponent.inject(this)
        coinViewModel = createViewModel(this, coinViewModelFactory, CoinViewModel::class.java)
    }

    override fun onCreate() {

        coinViewModel.fetchAllCoin()
        coinViewModel.allCoins.observe(this) { responseData ->
            when (responseData) {
                is ResponseData.Success -> {
                    val size = responseData.data.size
                    if (size > 0) {
                        mBinding.itemId = responseData.data[0].id
                    }
                    updateData("Item found ${responseData.data.size}")
                }
                is ResponseData.Failed -> updateData(mapDomainException(responseData.domainExceptions))
                is ResponseData.UIResponse -> observeCoinListEvent(responseData.uiEvents)
                else -> logElseResponse(responseData)
            }
        }

        coinViewModel.coinDetail.observe(this) { responseData ->
            when (responseData) {
                is ResponseData.Success -> updateData(responseData.data)
                is ResponseData.Failed -> updateData(mapDomainException(responseData.domainExceptions))
                is ResponseData.UIResponse -> observeCoinDetailEvent(responseData.uiEvents)
                else -> logElseResponse(responseData)
            }
        }
    }

    private fun observeCoinDetailEvent(uiEvents: CoinDetailUIEvent) {
        when (uiEvents) {

            is CoinDetailUIEvent.ShowLoader -> {
                if (uiEvents.status) {
                    updateData("Loading ${uiEvents.dataMessage}....Please Wait")
                } else {
                    updateData("Loader Hide")
                }
            }
            is CoinDetailUIEvent.UIEvents -> onObserveUIEvents(uiEvents.baseUIEvents)
        }
    }


    private fun observeCoinListEvent(uiEvents: CoinListUIEvent) {
        when (uiEvents) {
            is CoinListUIEvent.NavigateCoinDetail -> {
                navigateToCoinDetailScreen(uiEvents.id)
            }
            is CoinListUIEvent.ShowLoader -> {
                if (uiEvents.status) {
                    updateData("Loading....Please Wait")
                } else {
                    updateData("Loader Hide")
                }
            }
            is CoinListUIEvent.UIEvents -> onObserveUIEvents(uiEvents.baseUIEvents)
        }
    }

    private fun navigateToCoinDetailScreen(id: String?) {
        coinViewModel.fetchCoinDetail(id)
    }


    private fun updateData(message: String) {
        mBinding.result = message
        // mBinding.executePendingBindings()
    }

    override fun onNoInternetFound() {
        updateData("No Internet!")
    }


}