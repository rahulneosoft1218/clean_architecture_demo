package com.rahul.present_mobile.core

sealed class BaseUIEvents {

    object HideKeyBoard : BaseUIEvents()
    object OnNoInternet : BaseUIEvents()
    class Toast(val toastMessage: String?) : BaseUIEvents()

}