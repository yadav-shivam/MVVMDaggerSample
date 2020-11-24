package com.shivam.ecom

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment

abstract class BaseFragment : DaggerFragment() {

  override fun onAttach(context: Context) {
    AndroidSupportInjection.inject(this)
    super.onAttach(context)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    (activity as? AppCompatActivity)?.supportActionBar?.apply {
      setDisplayHomeAsUpEnabled(isUpEnabled())
      title = getString(title())
    }
  }


  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    if (item?.itemId == android.R.id.home) {
      activity?.onBackPressed()
      return true
    }
    return super.onOptionsItemSelected(item)
  }

  protected open fun isUpEnabled(): Boolean = false

  @StringRes
  protected open fun title(): Int = R.string.app_name
}