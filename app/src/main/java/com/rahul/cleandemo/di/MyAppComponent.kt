package com.rahul.cleandemo.di

import com.rahul.cleandemo.CoinListActivity
import com.rahul.present_mobile.di.PresentComponent
import dagger.BindsInstance
import dagger.Component
import okhttp3.Interceptor
import javax.inject.Named
import javax.inject.Singleton

//@Singleton
@Component(modules = [ActivityModule::class], dependencies = [PresentComponent::class])
interface MyAppComponent {

    fun getActivityComponent():ActivityComponent.Factory



}