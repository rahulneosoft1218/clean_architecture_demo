package com.rahul.present_mobile.core

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.rahul.present_mobile.di.PresentComponent
import com.rahul.present_mobile.utils.hideKeyBoard
import com.rahul.present_mobile.utils.toastMessage

abstract class BaseActivity<V : ViewDataBinding>(
    private val layoutID: Int
) : AppCompatActivity() {


    lateinit var mBinding: V



    open fun onNoInternetFound() {
        this@BaseActivity.toastMessage("No Internet Found!")
    }

    protected fun logElseResponse(responseData: ResponseData<*, *>) {
        Log.i("responseData", responseData.javaClass.simpleName)
    }

    fun <VM : ViewModel> createViewModel(
        factory: ViewModelProvider.Factory,
        vmClass: Class<VM>
    ): VM {
        return ViewModelProviders.of(this, factory)[vmClass]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this@BaseActivity, layoutID)
        mBinding.lifecycleOwner = this@BaseActivity

    }


    open fun onObserveUIEvents(baseUiEvents: BaseUIEvents) {
        when (baseUiEvents) {
            is BaseUIEvents.HideKeyBoard -> this@BaseActivity.hideKeyBoard()
            is BaseUIEvents.Toast -> this@BaseActivity.toastMessage(baseUiEvents.toastMessage)
            is BaseUIEvents.OnNoInternet -> onNoInternetFound()
        }
    }


}