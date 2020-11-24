package com.shivam.ecom.loginregister.register.data

import com.google.gson.annotations.SerializedName


data class RegisterRequest(
        @SerializedName("method") val method: String = "register", // register
        @SerializedName("name") val name: String, // qjamal
        @SerializedName("email") val email: String, // mibrahim@viaarabia.com
        @SerializedName("phonenumber") val phonenumber: String, // 0549552176
        @SerializedName("userpassword") var userpassword: String, // 1324654
        @SerializedName("transidentity") var transidentity: String
)


data class RegisterResponse(
        @SerializedName("result") val result: List<RegisterResponseItemResult>
)

data class RegisterResponseItemResult(
        @SerializedName("referencenumber") val referencenumber: String, // A6BB6864-C3C1-4BEE-BB2C-5BC7F20FE345
        @SerializedName("transactionid") val transactionid: String, // GC-00000000000179
        @SerializedName("status") val status: Int, // 0
        @SerializedName("statusdiscription") val statusdiscription: String // Success
)
