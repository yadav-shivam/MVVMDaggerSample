package com.shivam.ecom

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.akexorcist.localizationactivity.core.LanguageSetting
import dagger.android.support.DaggerAppCompatActivity


abstract class BaseActivity: DaggerAppCompatActivity(), LifecycleOwner {
  private var lifecycleRegistry: LifecycleRegistry? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    lifecycleRegistry = LifecycleRegistry(this)

  }

  override fun setContentView(layoutResID: Int) {
    super.setContentView(layoutResID)
    val v = findViewById<View>(android.R.id.content)
    if (v != null) {
      val locale = LanguageSetting.getLanguage(this)
      v!!.layoutDirection = TextUtils.getLayoutDirectionFromLocale(locale)
    }
  }

  override fun onStart() {
    super.onStart()

  }

  override fun onResume() {
    super.onResume()
  }


  open fun enableBackButton(): Boolean = false

  override fun onDestroy() {
    super.onDestroy()
    lifecycleRegistry = null
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    return when (item?.itemId) {
      android.R.id.home -> {
        onBackPressed()
        return true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }

  override fun getApplicationContext(): Context {
    return super.getApplicationContext()
  }

  override fun getResources(): Resources {
    return super.getResources()
  }
}