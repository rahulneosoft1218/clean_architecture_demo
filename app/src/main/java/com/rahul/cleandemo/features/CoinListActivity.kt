package com.rahul.cleandemo.features

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import com.rahul.cleandemo.R
import com.rahul.cleandemo.base.BaseActivity
import com.rahul.cleandemo.base.BaseUIEvents
import com.rahul.cleandemo.base.RvAdapter
import com.rahul.cleandemo.databinding.ActivityCoinBinding
import com.rahul.cleandemo.databinding.ItemCoinsBinding
import com.rahul.cleandemo.di.AppComponent
import com.rahul.cleandemo.uiEvents.CoinListUIEvent
import com.rahul.cleandemo.viewModelFactories.CoinViewModelFactory
import com.rahul.cleandemo.viewModels.CoinViewModel
import com.rahul.data_module.source.mock.MockDataLoadByInputStream
import com.rahul.data_module.source.mock.MockDataLoader
import com.rahul.domain_module.exceptions.DomainExceptions
import com.rahul.domain_module.responses.GetAllCoinResponse
import javax.inject.Inject


class CoinListActivity @Inject constructor() :
    BaseActivity<ActivityCoinBinding>(layoutID = R.layout.activity_coin) {


    private val coinAdapter =
        object : RvAdapter<GetAllCoinResponse, ItemCoinsBinding>(R.layout.item_coins) {

            override fun onBindItem(data: GetAllCoinResponse, rvItemBinding: ItemCoinsBinding) {
                rvItemBinding.model = CoinItemData(data.id,
                    data.image ?: "",
                    data.name ?: "")
            }

        }

    @Inject
    lateinit var coinViewModelFactory: CoinViewModelFactory

    private lateinit var coinViewModel: CoinViewModel

    override fun initViewModels(appComponent: AppComponent) {
        appComponent.inject(this)
        coinViewModel = createViewModel(this, coinViewModelFactory, CoinViewModel::class.java)
    }

    override fun onCreate() {

        mBinding.model = CoinListUIModel()
        mBinding.viewModel = coinViewModel

        mBinding.rv.apply {

            val drawable = ContextCompat.getDrawable(
                this.context,
                R.drawable.rv_divider
            )
            val itemDecorator = DividerItemDecoration(
                this.context,
                DividerItemDecoration.VERTICAL
            )
            itemDecorator.setDrawable(drawable!!)
            setHasFixedSize(true)
            addItemDecoration(itemDecorator)
            coinAdapter.setup(this)
        }

        coinViewModel.setAllCoinsMockDataLoader(MockDataLoadByInputStream(this.assets.open("AllCoins.json")))
        coinViewModel.fetchAllCoin()
        observeResponseData(coinViewModel.allCoins,
            ::observeCoinListData,
            ::observeCoinListError,
            ::observeCoinListEvent)

    }

    private fun observeCoinListData(data: List<GetAllCoinResponse>) {
        coinAdapter.newItems(data)
        observeCoinListEvent(CoinListUIEvent.ShowLoader(false))
    }

    private fun observeCoinListError(domainExceptions: DomainExceptions) {
        onObserveUIEvents(BaseUIEvents.Toast(mapDomainException(
            domainExceptions)))
        observeCoinListEvent(CoinListUIEvent.ShowLoader(false))
    }

    private fun observeCoinListEvent(uiEvents: CoinListUIEvent) {
        when (uiEvents) {
            is CoinListUIEvent.ShowLoader -> {
                mBinding.model?.refreshFlag = uiEvents.status
            }
            is CoinListUIEvent.UIEvents -> onObserveUIEvents(uiEvents.baseUIEvents)
        }
    }

/*
    private fun observeCoinDetailEvent(uiEvents: CoinDetailUIEvent) {
        when (uiEvents) {

            is CoinDetailUIEvent.ShowLoader -> {
                if (uiEvents.status) {
                    //updateData("Loading ${uiEvents.dataMessage}....Please Wait")
                } else {
                    //updateData("Loader Hide")
                }
            }
            is CoinDetailUIEvent.UIEvents -> onObserveUIEvents(uiEvents.baseUIEvents)
        }
    }
*/


    private fun navigateToCoinDetailScreen(id: String?) {
//        coinViewModel.fetchCoinDetail(id)
    }


}