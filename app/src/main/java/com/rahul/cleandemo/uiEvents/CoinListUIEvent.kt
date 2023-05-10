package com.rahul.cleandemo.uiEvents

import com.rahul.cleandemo.base.BaseUIEvents


sealed class CoinListUIEvent {

     data class ShowLoader(val status: Boolean) : CoinListUIEvent()
     data class UIEvents(val baseUIEvents: BaseUIEvents) : CoinListUIEvent()

}