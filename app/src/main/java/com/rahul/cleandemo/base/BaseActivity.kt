package com.rahul.cleandemo.base

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.rahul.cleandemo.CleanApp
import com.rahul.cleandemo.di.AppComponent
import com.rahul.cleandemo.utils.hideKeyBoard
import com.rahul.cleandemo.utils.toastMessage
import com.rahul.domain_module.exceptions.DomainErrors
import com.rahul.domain_module.exceptions.DomainExceptions

abstract class BaseActivity<V : ViewDataBinding>(private val layoutID: Int) : AppCompatActivity() {


    lateinit var mBinding: V

    abstract fun initViewModels(appComponent: AppComponent)
    abstract fun onCreate()


    open fun onNoInternetFound() {
        this@BaseActivity.toastMessage("No Internet Found!")
    }

    protected fun logElseResponse(responseData: ResponseData<*, *>) {
        Log.i("responseData", responseData.javaClass.simpleName)
    }

    fun <VM : ViewModel> createViewModel(
        owner: ViewModelStoreOwner,
        factory: ViewModelProvider.Factory,
        vmClass: Class<VM>,
    ): VM {
        return ViewModelProvider(owner, factory)[vmClass]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewModels((application as CleanApp).appComponent)
        mBinding = DataBindingUtil.setContentView(this@BaseActivity, layoutID)
        mBinding.lifecycleOwner = this@BaseActivity
        if (savedInstanceState == null) {
            onCreate()
        }

    }


    open fun onObserveUIEvents(baseUiEvents: BaseUIEvents) {
        when (baseUiEvents) {
            is BaseUIEvents.HideKeyBoard -> this@BaseActivity.hideKeyBoard()
            is BaseUIEvents.Toast -> this@BaseActivity.toastMessage(baseUiEvents.toastMessage)
            is BaseUIEvents.OnNoInternet -> onNoInternetFound()
        }
    }

    fun mapDomainException(
        domainExceptions: DomainExceptions,
        triggerNoInternet: Boolean = true,
    ): String {
        if (triggerNoInternet && domainExceptions.domainErrors == DomainErrors.NO_INTERNET) {
            onNoInternetFound()
            return domainExceptions.message
        }

        return domainExceptions.message

    }


    inline fun <R, UI> observeResponseData(
        responseData: LiveData<ResponseData<R, UI>>,
        crossinline observeSuccess: (R) -> Unit,
        crossinline observeFailure: (DomainExceptions) -> Unit,
        crossinline observeUIEvents: (UI) -> Unit,
    ) {
        responseData.observe(this) { res ->
            if (res == null) return@observe

            Handler(Looper.getMainLooper()).postDelayed({
                when (res) {
                    is ResponseData.Success -> observeSuccess.invoke(res.data)
                    is ResponseData.Failed -> observeFailure.invoke(res.domainExceptions)
                    is ResponseData.UIResponse -> observeUIEvents(res.uiEvents)
                }
            },500)
        }

    }


}