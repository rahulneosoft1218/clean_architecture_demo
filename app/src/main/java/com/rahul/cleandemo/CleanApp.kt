package com.rahul.cleandemo

import android.util.Log
import com.rahul.cleandemo.di.DaggerMyAppComponent
import com.rahul.cleandemo.di.MyAppComponent
import com.rahul.present_mobile.PresentApplication
import com.rahul.present_mobile.di.PresentComponent
import okhttp3.Interceptor

class CleanApp : PresentApplication("https://api.coingecko.com/") {

    lateinit var appComponent: MyAppComponent



    override fun onCreate(presentComponent: PresentComponent) {
        appComponent = DaggerMyAppComponent.builder()
            .presentComponent(presentComponent)
            .build()

        Log.i("CleanApp", "OnCreate")
    }


}