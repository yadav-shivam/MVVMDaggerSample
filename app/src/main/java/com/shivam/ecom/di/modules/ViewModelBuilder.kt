package com.shivam.ecom.di.modules

import androidx.lifecycle.ViewModelProvider
import com.shivam.ecom.di.CViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelBuilder {

  @Binds
  abstract fun bindViewModelFactory(factory: CViewModelFactory): ViewModelProvider.Factory

}