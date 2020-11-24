package com.shivam.ecom.common.data

data class AppUser(val userType: String,
                   val token: String,
                   val fullName: String,
                   val phoneNumber: String,
                   val email: String,
                   val availableCredit: Double,
                   val currency: String = "INR",
                   val language: String = "ar",
                   val currentSelectedCurrency: String = "INR"
)


/*
data class Currencyetail(
        @SerializedName("currency") val currency: String, // SAR
        @SerializedName("isselected") val isselected: String // TRUE
)*/
