package com.shivam.ecom.common.ext

import android.os.Parcel
import android.os.Parcelable

inline fun <reified T : Parcelable> createParcel(
  crossinline fromParcel: (Parcel) -> T): Parcelable.Creator<T> {
  return object : Parcelable.Creator<T> {
    override fun createFromParcel(source: Parcel): T = fromParcel(source)
    override fun newArray(size: Int): Array<out T?> = arrayOfNulls(size)
  }
}