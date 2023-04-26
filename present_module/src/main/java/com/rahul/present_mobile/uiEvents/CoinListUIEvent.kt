package com.rahul.present_mobile.uiEvents

import com.rahul.present_mobile.core.BaseUIEvents

sealed class CoinListUIEvent {

    data class NavigateCoinDetail(val id: String?) : CoinListUIEvent()
    data class ShowLoader(val status: Boolean) : CoinListUIEvent()
    data class UIEvents(val baseUIEvents: BaseUIEvents) : CoinListUIEvent()

}