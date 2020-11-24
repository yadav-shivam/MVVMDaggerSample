package com.shivam.ecom.loginregister

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class LoginRegisterFragmentBuilder {


    @ContributesAndroidInjector()
    internal abstract fun loginRegisterFragment(): LoginRegisterFragment

}