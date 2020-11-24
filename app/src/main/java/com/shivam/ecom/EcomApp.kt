package com.shivam.ecom

import android.content.res.Configuration
import com.shivam.ecom.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class EcomApp : DaggerApplication() {


  override fun onCreate() {
    super.onCreate()

  }

  override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
    return DaggerAppComponent.builder().create(this)
  }



  override fun onConfigurationChanged(newConfig: Configuration?) {
    super.onConfigurationChanged(newConfig)
  }

}