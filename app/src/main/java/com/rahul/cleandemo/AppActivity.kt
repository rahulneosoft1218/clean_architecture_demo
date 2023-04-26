package com.rahul.cleandemo

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.rahul.cleandemo.di.ActivityComponent
import com.rahul.cleandemo.di.MyAppComponent
import com.rahul.present_mobile.core.BaseActivity

abstract class AppActivity<V:ViewDataBinding>(layoutID: Int) : BaseActivity<V>(layoutID) {


    abstract fun onActivityCreated(activityComponent: ActivityComponent)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val app = (application as CleanApp)
        val appComponent = (application as CleanApp).appComponent
        val activityComponent = appComponent.getActivityComponent().create(app.baseUrl,app.getExtraInterceptors())
        onActivityCreated(activityComponent)
    }



}