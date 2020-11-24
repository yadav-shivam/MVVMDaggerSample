package com.shivam.ecom.more.profile

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module abstract class MyProfileActivityBuilder {

  @ContributesAndroidInjector
  internal abstract fun myProfileActivity(): MyProfileActivity
}
