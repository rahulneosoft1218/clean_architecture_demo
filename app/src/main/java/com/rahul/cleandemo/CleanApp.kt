package com.rahul.cleandemo

import android.app.Application
import com.rahul.cleandemo.base.AppCache
import com.rahul.cleandemo.config.AppOkhttpConfig
import com.rahul.cleandemo.di.AppComponent
import com.rahul.cleandemo.di.DaggerAppComponent

class CleanApp : Application() {

    lateinit var appComponent: AppComponent
    lateinit var appCache: AppCache


    override fun onCreate() {
        super.onCreate()
        appCache = AppCache.getAppCache(this)
        appComponent = DaggerAppComponent.factory()
            .create(
                this.applicationContext,
                baseUrl = "https://api.coingecko.com/",
                okhttpConfiguration = AppOkhttpConfig(this),
                appCache = appCache
            )

    }

}