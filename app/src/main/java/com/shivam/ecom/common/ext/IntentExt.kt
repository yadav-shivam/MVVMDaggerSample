package com.shivam.ecom.common.ext

import android.content.Context
import android.content.Intent
import com.shivam.ecom.R

fun Context.shareText(text: String, title: Int = R.string.share_intent_title) {
  val intent = Intent()
  intent.action = Intent.ACTION_SEND
  intent.putExtra(Intent.EXTRA_TEXT, text)
  intent.type = "text/plain"
  intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
  intent.resolveActivity(packageManager)?.let {
    startActivity(Intent.createChooser(intent, getString(title)))
  }
}