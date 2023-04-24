package com.rahul.domain_module.di

import com.rahul.data_module.di.DataModule
import com.rahul.data_module.repositories.CoinRepository
import com.rahul.data_module.repositories.DataRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module(includes = [DataModule::class])
abstract class DomainModule  {

 @Singleton
 @Binds
 abstract fun bindCoinRepository(repo :CoinRepository): DataRepository


}