package com.shivam.ecom.buy.thankyou

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module abstract class ThankYouFragmentBuilder {

  @ContributesAndroidInjector
  internal abstract fun thankYouFragment(): ThankYouFragment
}