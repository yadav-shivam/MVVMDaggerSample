package com.shivam.ecom.buy

import com.shivam.ecom.buy.quantityselection.PaymentFragmentBuilder
import com.shivam.ecom.buy.thankyou.ThankYouFragmentBuilder
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module abstract class BuyProductBuilder {

  @ContributesAndroidInjector(modules = arrayOf(
    BuyProductModule::class,
    PaymentFragmentBuilder::class,
    ThankYouFragmentBuilder::class
  ))
  internal abstract fun buyProductActivity(): BuyProductActivity
}

@Module class BuyProductModule {

}