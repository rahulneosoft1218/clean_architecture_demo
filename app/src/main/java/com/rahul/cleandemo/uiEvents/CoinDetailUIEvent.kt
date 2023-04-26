package com.rahul.cleandemo.uiEvents

import com.rahul.cleandemo.base.BaseUIEvents


sealed class CoinDetailUIEvent {

    data class ShowLoader(val status: Boolean,val dataMessage : String = "") : CoinDetailUIEvent()
    data class UIEvents(val baseUIEvents: BaseUIEvents) : CoinDetailUIEvent()

}