package com.rahul.data_module.di

import com.rahul.data_module.repositories.CoinRepository
import dagger.Component
import dagger.Subcomponent
import javax.inject.Singleton

@DataScope
@Component(modules = [DataModule::class])
interface DataComponent {

//    @DataScope
    fun getCoinRepository():CoinRepository

}