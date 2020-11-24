package com.shivam.ecom.common.ext

import android.util.Patterns
import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil

fun TextInputLayout.validateEmpty(error: String): Boolean {
    this.error = null

    if (this.isEmpty()) {
        this.error = error
        return false
    }

    return true
}

fun TextInputLayout.hasMinLength(error: String, minLength: Int = 3): Boolean {
    this.error = null

    if (this.getString().length < minLength) {
        this.error = error
        return false
    }

    return true
}

fun TextInputLayout.hasValidEmail(error: String): Boolean {
    this.error = null

    if (!Patterns.EMAIL_ADDRESS.matcher(this.getString()).matches()) {
        this.error = error
        return false
    }

    return true
}


fun TextInputLayout.hasValidPhoneNumber(code : String = "", error: String): Boolean {

    val mPhoneNumberUtil = PhoneNumberUtil.createInstance(this.context)

    var userEnteredPhoneNumber = code + this.getString()

    userEnteredPhoneNumber = userEnteredPhoneNumber.trim()

    userEnteredPhoneNumber = userEnteredPhoneNumber.replace("-", "")
    userEnteredPhoneNumber = userEnteredPhoneNumber.replace(" ", "")

    val phoneNumber = mPhoneNumberUtil.parse(userEnteredPhoneNumber,
            "SA")

    this.error = null

    if (!mPhoneNumberUtil.isValidNumber(phoneNumber)
    ) {
        this.error = error
        return false
    }

    return true
}

fun TextInputLayout.isEmpty(): Boolean = this.getString().isEmpty()

fun TextInputLayout.getString(): String = this.editText?.text.toString()


fun EditText.validateEmpty(error: String): Boolean {
    this.error = null

    if (this.text.toString().isEmpty()) {
        this.error = error
        return false
    }

    return true
}

fun EditText.validateGreaterThanZero(error: String): Boolean {
    this.error = null
    val q = text.toString().toIntOrNull()

    if(q == null){
        return false
    }
    if (q <= 0) {
        this.error = error
        return false
    }

    return true
}