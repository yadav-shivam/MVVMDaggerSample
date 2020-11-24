package com.shivam.ecom.loginregister.login

import androidx.lifecycle.ViewModel
import com.shivam.ecom.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module abstract class LoginFragmentBuilder {

  @ContributesAndroidInjector()
  internal abstract fun loginFragment(): LoginFragment

  @Binds
  @IntoMap
  @ViewModelKey(LoginViewModel::class)
  internal abstract fun loginViewModel(viewModel: LoginViewModel): ViewModel
}