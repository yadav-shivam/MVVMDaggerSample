package com.shivam.ecom.common.rx

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

open class RxAwareViewModel : ViewModel() {

  val disposables = CompositeDisposable()

  override fun onCleared() {
    super.onCleared()
    disposables.clear()
  }
}