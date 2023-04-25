package com.rahul.present_mobile.di


import com.rahul.domain_module.core.UseCase
import com.rahul.domain_module.di.DomainModule
import com.rahul.domain_module.usecases.GetAllCoinsUseCase
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module(includes = [DomainModule::class])
abstract class PresentDomainModule  {


 @Singleton
 @Binds
 abstract fun bindAllCoinsUseCase(repo : GetAllCoinsUseCase): UseCase<*,*>


}