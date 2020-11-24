package com.shivam.ecom.dashboard

import com.shivam.ecom.dashboard.products.ProductsFragmentBuilder
import com.shivam.ecom.dashboard.profile.ProfileFragmentBuilder
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module abstract class DashboardActivityBuilder {

  @ContributesAndroidInjector(modules = arrayOf(
    DashboardActivityModule::class,
          ProfileFragmentBuilder::class,
          ProductsFragmentBuilder::class
  ))
  internal abstract fun dashboardActivity(): DashboardActivity
}

@Module
class DashboardActivityModule {

}