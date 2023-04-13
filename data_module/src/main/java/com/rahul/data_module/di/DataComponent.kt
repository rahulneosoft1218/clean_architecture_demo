package com.rahul.data_module.di

import com.rahul.data_module.repositories.CoinRepository
import com.rahul.data_module.source.ApiService
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class])
interface DataComponent {

    fun getCoinRepository():CoinRepository

//    fun injectTest(test: CoinRepositoryTest)

}