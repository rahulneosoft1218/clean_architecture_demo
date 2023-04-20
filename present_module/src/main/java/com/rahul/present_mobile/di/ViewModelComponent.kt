package com.rahul.present_mobile.di

import com.rahul.domain_module.di.DomainComponent
import com.rahul.present_mobile.viewmodels.GetAllCoinViewModel
import dagger.Component
import javax.inject.Singleton

@VMScope
@Component(dependencies = [DomainComponent::class])
interface ViewModelComponent {

    @VMScope
    fun getCoinViewModel(): GetAllCoinViewModel

}