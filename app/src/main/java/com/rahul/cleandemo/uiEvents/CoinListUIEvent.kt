package com.rahul.cleandemo.uiEvents

import com.rahul.cleandemo.base.BaseUIEvents


sealed class CoinListUIEvent {

    data class NavigateCoinDetail(val id: String?) : CoinListUIEvent()
    data class ShowLoader(val status: Boolean) : CoinListUIEvent()
    data class UIEvents(val baseUIEvents: BaseUIEvents) : CoinListUIEvent()

}