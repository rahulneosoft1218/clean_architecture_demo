package com.rahul.present_mobile.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast


fun Context?.toastMessage(message: String?) {
    this?.let {
        Toast.makeText(it, message, Toast.LENGTH_SHORT).show()
    }
}


fun Activity?.hideKeyBoard() {
    if (this == null) return

    val imm: InputMethodManager =
        this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    if (imm.isAcceptingText) {
        //Software Keyboard was shown" , hide it
        val view: View? = this.currentFocus
        if (view != null) {
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

}


@SuppressLint("MissingPermission")
fun Context?.checkInternet(): Boolean {
    if (this == null) return false
    var result = false
    val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    cm?.run {
        cm.getNetworkCapabilities(cm.activeNetwork)?.run {
            result = when {
                hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        }
    }
    return result
}

