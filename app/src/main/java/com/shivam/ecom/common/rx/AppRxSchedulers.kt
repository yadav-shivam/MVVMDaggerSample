package com.shivam.ecom.common.rx

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface RxSchedulers {
  fun main(): Scheduler

  fun network(): Scheduler

  fun disk(): Scheduler
}

class DefaultRxSchedulers : RxSchedulers {

  override fun main(): Scheduler = AndroidSchedulers.mainThread()

  override fun network(): Scheduler = Schedulers.io()

  override fun disk(): Scheduler = Schedulers.single()
}