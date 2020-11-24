package com.shivam.ecom.buy.products.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ProductRequest(val category: String,val filter: String, val method: String = "ListProduct",
                          var transidentity: String)

data class ProductResult(
        @SerializedName("referencenumber") val referenceNumber: String,
        @SerializedName("transactionid") val transactionId: String,
        @SerializedName("status") val statusCode: Int,
        @SerializedName("statusdiscription") val statusDescription: String,
        @SerializedName("availablecredit") val availablecredit: String,
        @SerializedName("points") val points: String,
        @SerializedName("productdet") val products: List<ProductItemResponse>,
        @SerializedName("filterdet") val filterdet : List<Filterdet>
)

data class ProductItemResponse(
        @SerializedName("category") val category: String, //Apple iTun
        @SerializedName("sku") val sku: String, //GC000005
        @SerializedName("skuname") val skuName: String, //Itunes Gift Card 100 $ Digital Voucher
        @SerializedName("qty") var quantity: Int, //2429
        @SerializedName("cost") val cost: Double, //400
        @SerializedName("price") val price: Double, //449
        @SerializedName("productimgurl") val imageUrl: Int, //https://portal.viaarabia.com/img/via1.png
        @SerializedName("customamount") val CustomAmount: Boolean,
        @SerializedName("filter") val filter: String,



        @Expose(serialize = false, deserialize = false)
        var quantityCart: Int,

        @Expose(serialize = false, deserialize = false)
        var totalPrice: Double,

        @Expose(serialize = false, deserialize = false)
        var customAmount: Int

) : Parcelable {
        constructor(parcel: Parcel) : this(
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readInt(),
                parcel.readDouble(),
                parcel.readDouble(),
                parcel.readInt(),
                parcel.readByte() != 0.toByte(),
                parcel.readString(),
                parcel.readInt(),
                parcel.readDouble(),
                parcel.readInt()) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
                parcel.writeString(category)
                parcel.writeString(sku)
                parcel.writeString(skuName)
                parcel.writeInt(quantity)
                parcel.writeDouble(cost)
                parcel.writeDouble(price)
                parcel.writeInt(imageUrl)
                parcel.writeByte(if (CustomAmount) 1 else 0)
                parcel.writeString(filter)
                parcel.writeInt(quantityCart)
                parcel.writeDouble(totalPrice)
                parcel.writeInt(customAmount)
        }

        override fun describeContents(): Int {
                return 0
        }

        companion object CREATOR : Parcelable.Creator<ProductItemResponse> {
                override fun createFromParcel(parcel: Parcel): ProductItemResponse {
                        return ProductItemResponse(parcel)
                }

                override fun newArray(size: Int): Array<ProductItemResponse?> {
                        return arrayOfNulls(size)
                }
        }
}

data class Filterdet (

        @SerializedName("filter") val filter : String,
        @SerializedName("filterimgurl") val filterimgurl : String,

        @Expose(serialize = false, deserialize = false)
        var isSelect: Boolean
)

data class CartHeader(
        val name:String ,val quantity: Int
)

