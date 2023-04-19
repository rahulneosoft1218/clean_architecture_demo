package com.rahul.domain_module.di

import com.rahul.data_module.di.DataModule
import com.rahul.data_module.repositories.CoinRepository
import com.rahul.domain_module.usecases.GetAllCoinsUseCase
import com.rahul.domain_module.usecases.GetCoinsDetailUseCase
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class])
interface DomainComponent {

    fun getAllCoinUseCase():GetAllCoinsUseCase
    fun getCoinDetailUseCase():GetCoinsDetailUseCase

}