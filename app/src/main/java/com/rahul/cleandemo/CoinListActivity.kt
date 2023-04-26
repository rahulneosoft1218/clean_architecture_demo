package com.rahul.cleandemo

import com.rahul.cleandemo.databinding.ActivityCoinBinding
import com.rahul.cleandemo.di.ActivityComponent
import com.rahul.present_mobile.core.ResponseData
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

        coinViewModel.fetchAllCoin()
        coinViewModel.allCoins.observe(this) { responseData ->
            when (responseData) {
                is ResponseData.Success -> updateData("Item found ${responseData.data.size}")
                is ResponseData.Failed -> updateData(responseData.domainExceptions.message)
                is ResponseData.UIEvents -> observeCoinListEvent(responseData.uiEvents)
                else -> logElseResponse(responseData)
            }
        }


    }

/*
    override fun onCreate(presentComponent: PresentComponent) {
        presentComponent.inject(this)
        coinViewModel = createViewModel(coinViewModelFactory, CoinViewModel::class.java)



        coinViewModel.fetchAllCoin()
        coinViewModel.allCoins.observe(this) { responseData ->
            when (responseData) {
                is ResponseData.Success -> updateData("Item found ${responseData.data.size}")
                is ResponseData.Failed -> updateData(responseData.domainExceptions.message)
                is ResponseData.UIEvents -> observeCoinListEvent(responseData.uiEvents)
                else -> logElseResponse(responseData)
            }
        }

    }
*/

    private fun observeCoinListEvent(uiEvents: CoinListUIEvent) {
        when (uiEvents) {
            is CoinListUIEvent.NavigateCoinDetail -> {
                toastMessage("Coin Id ${uiEvents.id}")
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


    private fun updateData(message: String) {
        mBinding.result = message
       // mBinding.executePendingBindings()
    }

    override fun onNoInternetFound() {
        updateData("No Internet!")
    }


}