package com.shivam.ecom.common.ext

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

inline fun FragmentManager.inTransaction(backStack: String?,
                                         f: FragmentTransaction.() -> Unit) {
  val transaction = beginTransaction()
  transaction.f()
  if (backStack != null) {
    transaction.addToBackStack(backStack)
  }
  transaction.commit()
}

fun FragmentActivity.addFragment(fragment: Fragment, frameId: Int,
                                 addToBackStack: String? = null) {
  supportFragmentManager.inTransaction(addToBackStack) { add(frameId, fragment) }
}

fun FragmentActivity.replaceFragment(fragment: Fragment, frameId: Int,
                                     addToBackStack: String? = null) {
  supportFragmentManager.inTransaction(addToBackStack) { replace(frameId, fragment) }
}

fun Fragment.replaceFragment(fragment: Fragment, frameId: Int,
                                     addToBackStack: String? = null) {
  activity?.supportFragmentManager?.inTransaction(addToBackStack) {
    replace(frameId, fragment)
  }
}

fun FragmentManager.clearBackStack() {
  for (i in 0..backStackEntryCount) {
    popBackStack()
  }

}
