package com.rahul.cleandemo.features

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.rahul.cleandemo.BR

class CoinListUIModel : BaseObservable() {

    @get:Bindable
    var refreshFlag: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.refreshFlag)
        }

    val pullToRefreshId = 1
}