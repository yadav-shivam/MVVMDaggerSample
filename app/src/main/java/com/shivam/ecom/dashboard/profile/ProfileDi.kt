package com.shivam.ecom.dashboard.profile

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ProfileFragmentBuilder {

    @ContributesAndroidInjector
    internal abstract fun profileFragment(): ProfileFragment


}