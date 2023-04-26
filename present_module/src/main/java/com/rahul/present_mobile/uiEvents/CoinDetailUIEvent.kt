package com.rahul.present_mobile.uiEvents

import com.rahul.present_mobile.core.BaseUIEvents

sealed class CoinDetailUIEvent {

    data class ShowLoader(val status: Boolean,val dataMessage : String = "") : CoinDetailUIEvent()
    data class UIEvents(val baseUIEvents: BaseUIEvents) : CoinDetailUIEvent()

}