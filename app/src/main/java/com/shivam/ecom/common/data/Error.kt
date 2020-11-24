package com.shivam.ecom.common.data

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
  @SerializedName("result") val result: List<ErrorResult>
)

data class ErrorResult(
  @SerializedName("status") val status: Int, //206
  @SerializedName("statusdiscription") val statusdiscription: String //Token Expired.
)