package com.httpmangafruit.cardless.common.ext

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.shivam.ecom.R
import io.fabric.sdk.android.services.common.SafeToast

fun showDialog(context: Context,
               title: String = "",
               message: String,
               buttonText: Int = R.string.common_ok): AlertDialog {
  val builder = AlertDialog.Builder(context)
  builder.setTitle(title)
  builder.setMessage(message)
  builder.setPositiveButton(buttonText) { dialog, _ -> dialog.dismiss() }
  return builder.create()
}

fun showDialog(context: Context,
               title: String = "",
               message: String,
               buttonText: Int = R.string.common_ok,
               callback: DialogInterface.OnClickListener,
               negativeButtonText: Int = R.string.common_cancel,
               cancelCallback: DialogInterface.OnClickListener? = null): AlertDialog {
  val builder = AlertDialog.Builder(context)

  builder.setTitle(title)
  builder.setMessage(message)

  builder.setPositiveButton(buttonText, callback)

  cancelCallback?.let {
    builder.setNegativeButton(negativeButtonText, cancelCallback)
  }

  return builder.create()
}

fun createProgressDialog(context: Context, message: Int = R.string.common_loading): ProgressDialog {
  val dialog = ProgressDialog(context)
  dialog.setMessage(context.getString(message))
  dialog.setCancelable(false)
  return dialog
}

fun showToast(context: Context, message: Int = R.string.error_general, duration: Int = Toast.LENGTH_SHORT) {
  SafeToast.makeText(context, message, duration).show()
}


fun showToastException(context: Context, message:String, duration: Int = Toast.LENGTH_SHORT) {
  SafeToast.makeText(context, message, duration).show()
}
fun showSnackBar(view: View, message: Int = R.string.error_general) {
  Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
}