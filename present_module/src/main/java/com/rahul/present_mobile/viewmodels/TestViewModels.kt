package com.rahul.present_mobile.viewmodels

import android.app.Activity
import android.os.Bundle
import android.util.Log
import com.rahul.present_mobile.di.DaggerPresentComponent
import com.rahul.present_mobile.di.PresentComponent
import javax.inject.Inject

abstract class TestViewModels  {



//    @Inject
//    lateinit var coinViewModel: CoinViewModel
//

    abstract fun onCreateComponent(presentComponent: PresentComponent)

    open fun onCreate() {



        val presentComponent: PresentComponent =
            DaggerPresentComponent.factory()
                .create("https://api.coingecko.com/", null)

        onCreateComponent(presentComponent)
//         presentComponent.inject(this)

//        Log.d("Test-VM","OnCreate")
    }

    open fun onTerminate() {
//        coinViewModel.onTerminate()
    }


}