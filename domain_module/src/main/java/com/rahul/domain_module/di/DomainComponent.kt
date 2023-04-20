package com.rahul.domain_module.di

import com.rahul.data_module.di.DataComponent
import com.rahul.domain_module.usecases.GetAllCoinsUseCase
import com.rahul.domain_module.usecases.GetCoinsDetailUseCase
import dagger.Component

@DomainScope
@Component(dependencies = [DataComponent::class])
interface DomainComponent {


    @DomainScope
    fun getAllCoinUseCase(): GetAllCoinsUseCase

    @DomainScope
    fun getCoinDetailUseCase(): GetCoinsDetailUseCase

}