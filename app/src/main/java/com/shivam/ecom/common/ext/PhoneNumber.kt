package com.shivam.ecom.common.ext

import com.google.android.material.textfield.TextInputLayout
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil

fun TextInputLayout.getE164PhoneNumber(code : String): String {

    val mPhoneNumberUtil = PhoneNumberUtil.createInstance(this.context)

    var userEnteredPhoneNumber = code + this.getString()

    userEnteredPhoneNumber = userEnteredPhoneNumber.trim()

    userEnteredPhoneNumber = userEnteredPhoneNumber.replace("-", "")
    userEnteredPhoneNumber = userEnteredPhoneNumber.replace(" ", "")

    val phoneNumber = mPhoneNumberUtil.
            parse(userEnteredPhoneNumber, "SA")

    return mPhoneNumberUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.E164)
}

