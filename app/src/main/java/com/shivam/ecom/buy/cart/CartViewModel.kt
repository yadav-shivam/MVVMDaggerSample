package com.shivam.ecom.buy.cart

import androidx.lifecycle.MutableLiveData
import com.shivam.ecom.buy.products.data.ProductItemResponse
import com.shivam.ecom.buy.quantityselection.data.CreateOrderItem
import com.shivam.ecom.buy.quantityselection.data.CreateOrderResult
import com.shivam.ecom.common.Resource
import com.shivam.ecom.common.livedata.SingleLiveData
import com.shivam.ecom.common.rx.RxAwareViewModel
import com.shivam.ecom.common.storage.UserStorage
import javax.inject.Inject

class CartViewModel @Inject constructor(                                        private val userStorage: UserStorage
) : RxAwareViewModel() {

    val balance: MutableLiveData<String> = MutableLiveData()

    var cart: MutableLiveData<List<ProductItemResponse>>? = MutableLiveData()

    var total: MutableLiveData<Double> = MutableLiveData()

    val data2: SingleLiveData<Resource<String>> = SingleLiveData()
    val data3: SingleLiveData<Resource<String>> = SingleLiveData()
    val data: SingleLiveData<Resource<List<CreateOrderResult>>> = SingleLiveData()



    init {
        balance()
    }

    fun checkout(refCode :String) {

        cart?.value?.let {

            if (it.size > 0) {
                val list = mutableListOf<CreateOrderItem>()


                for (item in it) {

                    val orderItem = CreateOrderItem(item.sku, item.quantityCart,item.customAmount)
                    list.add(orderItem)

                }
            }
        }
    }

    fun confirmCheckout(transactionId: String, fortId: String, paymentMethod: String) {

        data2.value = Resource.success(transactionId)
    }

    fun loadCart() {
        val items: MutableList<ProductItemResponse>? = userStorage.getCart()
                as MutableList<ProductItemResponse>?
        if (items != null)
            for (item in items) {

                val index = items.indexOf(item)

                val total = item.cost * item.quantityCart
                item.totalPrice = total
                items.set(index, item)
            }
        cart?.value = items
    }

    fun updateCart(updatedList: MutableList<ProductItemResponse>) {
        userStorage.updateCart(updatedList)
        cart?.value = updatedList

    }

    fun calculateTotal() {
        var totalCaptureResult = 0.0

        for (item in cart?.value!!) {
            totalCaptureResult = totalCaptureResult + item.cost * item.quantityCart * item.customAmount
        }

        total.value = totalCaptureResult
    }

    fun addInTotal(v: Double) {

        total.value = total.value?.let { it + v }

    }

    fun removeFromTotal(v: Double) {
        total.value = total.value?.let { it - v }

    }


    fun balance() {
        val availableCredit = userStorage.get()?.availableCredit
        availableCredit?.let {
            balance.value = availableCredit.toString()
        }
    }
}