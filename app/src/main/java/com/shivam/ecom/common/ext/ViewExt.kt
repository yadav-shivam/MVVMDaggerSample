package com.shivam.ecom.common.ext

import android.app.Activity
import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.shivam.ecom.R


fun ImageView.load(url: String?) {
   /* Glide.with(context)
            .load(url)
            .crossFade()
            .placeholder(R.drawable.ic_placeholder)
            .error(R.drawable.ic_placeholder)
            .into(this)*/

    Glide.with(this)
            .load(url)
            .apply(RequestOptions()
                    .placeholder(R.drawable.ic_placeholder))
            .into(this)
}

fun ImageView.load(src:Int) {
    Glide.with(this)
            .load(src)
            .apply(RequestOptions()
                    .placeholder(R.drawable.ic_placeholder))
            .into(this)
}

fun getColoredString(text: CharSequence, color: Int): Spannable? {
    if (TextUtils.isEmpty(text)) {
        return null
    }

    val spannableString = SpannableString(text)
    spannableString.setSpan(ForegroundColorSpan(color), 0, text.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

    return spannableString
}

fun hideKeyboard(activity: Activity) {
    val view = activity.currentFocus

    view?.let {
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}