package com.rahul.cleandemo.base

sealed class BaseUIEvents {

    object HideKeyBoard : BaseUIEvents()
    object OnNoInternet : BaseUIEvents()
    class Toast(val toastMessage: String?) : BaseUIEvents()

}