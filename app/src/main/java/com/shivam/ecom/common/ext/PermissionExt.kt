package com.shivam.ecom.common.ext

import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.httpmangafruit.cardless.common.ext.showDialog
import com.shivam.ecom.BaseActivity

object PermissionHelper {
  fun onRequestPermissionsResult(grantResults: IntArray): Boolean {
    return grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED
  }
}

fun BaseActivity.requestPermission(permission: String, requestCode: Int,
                                   @StringRes permissionRationalResId: Int): Boolean {
  if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
    return true
  }

  if (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED) {
    return true
  }

  if (shouldShowRequestPermissionRationale(permission)) {
    showDialog(context = this, message = getString(permissionRationalResId),
      callback = DialogInterface.OnClickListener { dialog, _ ->
        dialog.dismiss()
        requestPermissions(arrayOf(permission), requestCode)
      }).show()
  } else {
    requestPermissions(arrayOf(permission), requestCode)
  }

  return false
}

fun Fragment.requestPermission(permission: String, requestCode: Int,
                               @StringRes permissionRationalResId: Int): Boolean {
  if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
    return true
  }

  if (ContextCompat.checkSelfPermission(context!!, permission) == PackageManager.PERMISSION_GRANTED) {
    return true
  }

  if (shouldShowRequestPermissionRationale(permission)) {
    showDialog(context = context!!, message = getString(permissionRationalResId),
      callback = DialogInterface.OnClickListener { dialog, _ ->
        dialog.dismiss()
        requestPermissions(arrayOf(permission), requestCode)
      }).show()
  } else {
    requestPermissions(arrayOf(permission), requestCode)
  }

  return false
}