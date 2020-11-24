package com.shivam.ecom.loginregister.register

import androidx.lifecycle.ViewModel
import com.shivam.ecom.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap


@Module
abstract class RegisterFragmentBuilder {


    @ContributesAndroidInjector()
    internal abstract fun registerFragment(): RegisterFragment

    @Binds
    @IntoMap
    @ViewModelKey(RegisterViewModel::class)
    internal abstract fun bindRegisterViewModel(viewModel: RegisterViewModel): ViewModel
}