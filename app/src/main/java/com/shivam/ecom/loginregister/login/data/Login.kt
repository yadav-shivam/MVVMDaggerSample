package com.shivam.ecom.loginregister.login.data

import com.google.gson.annotations.SerializedName

data class LoginRequest(val phonenumber: String,
                        val userpassword: String,
                        val devicetoken: String,
                        val method: String = "login",
                        val transidentity: String)
/*
data class LoginResult (
  @SerializedName("referencenumber") val referenceNumber: String, //582457C0-B616-4C2C-A178-08577B1EB0BD
  @SerializedName("transactionid") val transactionId: String, //GC-00000000000357
  @SerializedName("status") val statusCode: Int, //0
  @SerializedName("statusdiscription") val statusDiscription: String, //Success
  @SerializedName("usertype") val userType: String, //1
  @SerializedName("token") val token: String, //582457C0-B616-4C2C-A178-08577B1EB0BD
  @SerializedName("fullname") val fullName: String, //Qaiser
  @SerializedName("phonenumber") val phoneNumber: String, //0549552176
  @SerializedName("availablecredit") val availableCredit: Double, //500000
  @SerializedName("points") val points: Int? //0
)*/


/*
data class LoginResult(
        @SerializedName("result") val result: List<Result>
)
*/

data class LoginResult(
        @SerializedName("referencenumber") val referencenumber: String, // 123456
        @SerializedName("transactionid") val transactionid: String, // 3037FB00-A68C-4C24-9133-80FAAD38D128
        @SerializedName("status") val status: Int, // 0
        @SerializedName("statusdiscription") val statusdiscription: String, // Success
        @SerializedName("usertype") val userType: String, // 1
        @SerializedName("token") val token: String, // 4855E722-1316-4865-A3DD-B0EE137EB1BE
        @SerializedName("fullname") val fullName: String, // Pak-2
        @SerializedName("phonenumber") val phoneNumber: String, // +923326567008
        @SerializedName("email") val email: String, // +923326567008
        @SerializedName("availablecredit") val availableCredit: Double, // 498376.6
        @SerializedName("points") val points: Int, // 0
        @SerializedName("currencyetails") val currencyetails: List<Currencyetail>,
        @SerializedName("serviceprovider") val serviceprovider: List<Serviceprovider>,
        @SerializedName("paymentmethod") val paymentmethod: MutableList<Paymentmethod>,
        @SerializedName("refcode") val refCode : String //sajdgjh14

)

data class Serviceprovider(
        @SerializedName("code") val code: String,
        @SerializedName("name") val name: String
)

data class Paymentmethod(
        @SerializedName("paymentid") val paymentid: String,
        @SerializedName("paymenttype") val paymenttype: String,
        @SerializedName("image_url") val image_url: String,
        @SerializedName("availableinrefill") val availableinrefill: Boolean,
        @SerializedName("avialableinorder") val availableinreorder: Boolean
)


data class Currencyetail(
        @SerializedName("currency") val currency: String, // SAR
        @SerializedName("isselected") val isselected: String // TRUE
)

