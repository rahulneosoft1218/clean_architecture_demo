package com.rahul.present_mobile.di

import com.rahul.domain_module.di.DomainComponent
import com.rahul.present_mobile.viewmodels.CoinViewModel
import dagger.Component

@VMScope
@Component(dependencies = [DomainComponent::class])
interface ViewModelComponent {

    @VMScope
    fun getCoinViewModel(): CoinViewModel

}