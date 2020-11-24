package com.shivam.ecom.common.data
import com.google.gson.annotations.SerializedName

data class ApiResponse<out T>(
		@SerializedName("result") val result: List<T>
)