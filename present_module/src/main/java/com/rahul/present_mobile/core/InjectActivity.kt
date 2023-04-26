package com.rahul.present_mobile.core

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rahul.present_mobile.PresentApplication
import com.rahul.present_mobile.di.PresentComponent
import com.rahul.present_mobile.viewModelFactories.CoinViewModelFactory
import javax.inject.Inject

abstract class InjectActivity : AppCompatActivity() {
    @Inject
    lateinit var coinViewModelFactory2 : CoinViewModelFactory
    abstract fun onCreate(presentComponent: PresentComponent)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val presentComponent: PresentComponent =
            (application as PresentApplication).presentComponent
//        presentComponent.inject(this)
        onCreate(presentComponent)

    }


}