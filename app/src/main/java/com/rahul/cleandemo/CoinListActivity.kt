package com.rahul.cleandemo

import com.rahul.cleandemo.databinding.ActivityCoinBinding
import com.rahul.cleandemo.di.ActivityComponent
import com.rahul.present_mobile.core.ResponseData
import com.rahul.present_mobile.uiEvents.CoinDetailUIEvent
import com.rahul.present_mobile.uiEvents.CoinListUIEvent
import com.rahul.present_mobile.utils.toastMessage
import com.rahul.present_mobile.viewModelFactories.CoinViewModelFactory
import com.rahul.present_mobile.viewmodels.CoinViewModel
import javax.inject.Inject

class CoinListActivity : AppActivity<ActivityCoinBinding>(
    R.layout.activity_coin
) {


    @Inject
    lateinit var coinViewModelFactory: CoinViewModelFactory

    private lateinit var coinViewModel: CoinViewModel


    override fun onActivityCreated(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
        coinViewModel = createViewModel(coinViewModelFactory, CoinViewModel::class.java)
        mBinding.viewModel = coinViewModel


        coinViewModel.fetchAllCoin()
        coinViewModel.allCoins.observe(this) { responseData ->
            when (responseData) {
                is ResponseData.Success -> {
                    val size = responseData.data.size
                    if (size>0){
                        mBinding.itemId = responseData.data[0].id
                    }
                    updateData("Item found ${responseData.data.size}")}
                is ResponseData.Failed -> updateData(responseData.domainExceptions.message)
                is ResponseData.UIEvents -> observeCoinListEvent(responseData.uiEvents)
                else -> logElseResponse(responseData)
            }
        }

        coinViewModel.coinDetail.observe(this) { responseData ->
            when (responseData) {
                is ResponseData.Success -> updateData(responseData.data)
                is ResponseData.Failed -> updateData(responseData.domainExceptions.message)
                is ResponseData.UIEvents -> observeCoinDetailEvent(responseData.uiEvents)
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
            is CoinListUIEvent.NavigateCoinDetail ->{
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