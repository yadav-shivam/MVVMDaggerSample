package com.shivam.ecom.dashboard.products.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ProductsRequest(val method: String = "ListCategory",
                           val transidentity: String)

data class ProductsResult
(
        @SerializedName("referencenumber") val referenceNumber: String,
        @SerializedName("transactionid") val transactionId: String,
        @SerializedName("status") val statusCode: Int,
        @SerializedName("statusdiscription") val statusDescription: String,
        @SerializedName("availablecredit") val availablecredit: String,
        @SerializedName("points") val points: String,
        @SerializedName("catdetails") val products: List<ProductItemResponse>
)

data class ProductItemResponse(
        val description: String,
        val name: String,
        val imageUrl: Int,
        var price: Int,
        var quantityCart: Int
): Serializable {

}