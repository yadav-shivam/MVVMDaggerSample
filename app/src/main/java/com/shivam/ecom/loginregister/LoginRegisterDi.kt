package com.shivam.ecom.loginregister

import com.shivam.ecom.loginregister.login.LoginFragmentBuilder
import com.shivam.ecom.loginregister.register.RegisterFragmentBuilder
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class LoginRegisterActivityBuilder {

    @ContributesAndroidInjector(modules = arrayOf(
            LoginRegisterActivityModule::class,
            LoginFragmentBuilder::class,
            LoginRegisterFragmentBuilder::class,
            RegisterFragmentBuilder::class))
    internal abstract fun loginRegisterActivity(): LoginRegisterActivity
}

@Module
class
LoginRegisterActivityModule {
}