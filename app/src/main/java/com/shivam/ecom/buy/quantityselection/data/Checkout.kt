package com.shivam.ecom.buy.quantityselection.data

import com.google.gson.annotations.SerializedName

data class CreateOrderRequest(@SerializedName("itemdet") val items: List<CreateOrderItem>, val method: String = "CreateOrder",
                              val transidentity: String, val refcode:String)

data class CreateOrderItem(val sku: String, val qty: Int, val amount: Int)

data class CreateOrderResult(
    @SerializedName("referencenumber") val referenceNumber: String,
    @SerializedName("transactionid") val transactionId: String,
    @SerializedName("status") val statusCode: Int,
    @SerializedName("statusdiscription") val statusDescription: String)

data class ConfirmOrderRequest(
    @SerializedName("transactionid") val transactionId: String,
    @SerializedName("paymenttype") val paymentType: String = "My Balance",
    @SerializedName("paymentref") val paymentRef: String = "Pay123456",
    @SerializedName("method") val method: String = "ConfirmOrder",
    @SerializedName("transidentity") val transidentity: String
)

data class ConfirmOrderResult(
    @SerializedName("referencenumber") val referenceNumber: String,
    @SerializedName("transactionid") val transactionId: String,
    @SerializedName("balance") val balance: String,
    @SerializedName("status") val statusCode: Int,
    @SerializedName("points") val points: String,
    @SerializedName("statusdiscription") val statusDescription: String,
    @SerializedName("itemdet") val items: List<ConfirmOrderItem>
)

data class ConfirmOrderItem(
    @SerializedName("sku") val sku: String, //GC000004
    @SerializedName("skuname") val skuname: String, //Itunes Gift Card 50 $ Digital Voucher
    @SerializedName("price") val price: Double, //239
    @SerializedName("cost") val cost: Double, //230
    @SerializedName("serial") val serial: String, //SB7924131XDK295DCP11
    @SerializedName("code") val code: String //null
)

data class CancelOrderRequest(
        @SerializedName("transactionid") val transactionId: String,
        @SerializedName("reason") val reason: String,
        @SerializedName("method") val method: String = "CancelOrder",
        @SerializedName("transidentity") val transidentity: String
)

data class CancelOrderResult(
        @SerializedName("referencenumber") val referenceNumber: String,
        @SerializedName("transactionid") val transactionId: String,
        @SerializedName("status") val statusCode: Int,
        @SerializedName("statusdiscription") val statusDescription: String
)