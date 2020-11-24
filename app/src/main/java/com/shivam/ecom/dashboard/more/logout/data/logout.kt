package com.shivam.ecom.dashboard.more.logout.data

import com.google.gson.annotations.SerializedName


data class LogoutRequest(
        @SerializedName("devicetoken") val devicetoken: String // E885274B-1C8B-45C5-AF89-9E418F8BF9CE
        , @SerializedName("method") val method: String = "logout", // logout
        @SerializedName("transidentity") var transidentity: String
)


data class LogoutResponse(
        @SerializedName("result") val result: List<ResultLogoutResponse>
)

data class ResultLogoutResponse(
        @SerializedName("referencenumber") val referencenumber: String, // A6BB6864-C3C1-4BEE-BB2C-5BC7F20FE345
        @SerializedName("transactionid") val transactionid: String, // GC-00000000000179
        @SerializedName("status") val status: Int, // 0
        @SerializedName("statusdiscription") val statusdiscription: String // Success
)
