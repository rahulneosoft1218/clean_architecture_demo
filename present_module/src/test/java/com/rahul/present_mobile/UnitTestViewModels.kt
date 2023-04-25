package com.rahul.present_mobile


import com.rahul.present_mobile.core.ResponseData
import com.rahul.present_mobile.di.DaggerPresentComponent
import com.rahul.present_mobile.di.PresentComponent
import com.rahul.present_mobile.viewmodels.CoinViewModel
import com.rahul.present_mobile.viewmodels.TestViewModels
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import javax.inject.Inject

abstract class UnitTestViewModels : TestViewModels() {




    @Before
    override fun onCreate() {

        super.onCreate()

    }


    @After
    override fun onTerminate() {
       super.onTerminate()
    }


}