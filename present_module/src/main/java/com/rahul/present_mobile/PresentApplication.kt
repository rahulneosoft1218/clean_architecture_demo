package com.rahul.present_mobile

import android.app.Application
import com.rahul.present_mobile.core.CheckInternetError
 import com.rahul.present_mobile.di.DaggerPresentComponent
import com.rahul.present_mobile.di.PresentComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.DaggerApplication
import okhttp3.Interceptor
import javax.inject.Inject

abstract class PresentApplication(val baseUrl : String) : Application() {

////"https://api.coingecko.com/"
////    @Inject
////    lateinit var androidInjector: DispatchingAndroidInjector<Any>
//
//    override fun androidInjector(): AndroidInjector<out DaggerApplication> = DaggerAppComponent.factory().create(this)
//
////    override fun androidInjector(): AndroidInjector<Any> {
////        return DaggerAppComponent.factory().create(this)
////    }

    private  val extraInterceptor = ArrayList<Interceptor>()


    internal lateinit var presentComponent: PresentComponent

    abstract fun onCreate(presentComponent: PresentComponent)

    override fun onCreate() {
        super.onCreate()

        extraInterceptor.apply {
            add(CheckInternetError(this@PresentApplication.applicationContext))
        }
        val presentComponent = DaggerPresentComponent.factory()
            .create(baseUrl, extraInterceptor)
        onCreate(presentComponent)

    }

    fun getExtraInterceptors() = extraInterceptor

}