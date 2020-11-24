package com.shivam.ecom.buy.quantityselection

import androidx.lifecycle.MutableLiveData
import com.shivam.ecom.buy.quantityselection.data.CreateOrderItem
import com.shivam.ecom.buy.quantityselection.data.CreateOrderResult
import com.shivam.ecom.common.Resource
import com.shivam.ecom.common.livedata.SingleLiveData
import com.shivam.ecom.common.rx.RxAwareViewModel
import com.shivam.ecom.common.storage.UserStorage
import javax.inject.Inject
import kotlin.properties.Delegates

class PaymentViewModel @Inject constructor(
                                           private val userStorage: UserStorage) : RxAwareViewModel() {

    private var totalAmount: Double = 0.0
    var price: Double by Delegates.observable(0.0) { _, old, new ->
        if (old == new) return@observable

        updateQuantityAndTotalAmount()
    }

    var quantity: Int by Delegates.observable(1) { _, old, new ->
        if (old == new) return@observable

        if (new <= 0) {
            quantity = 1
        }

        updateQuantityAndTotalAmount()
    }

    var amount: Int by Delegates.observable(1) { _, old, new ->
        if (old == new) return@observable

        if (new <= 0) {
            amount = 1
        }

        updateQuantityAndTotalAmount()
    }

    val quantityData: MutableLiveData<Int> = MutableLiveData()
    val totalAmountData: MutableLiveData<Double> = MutableLiveData()
    val customAmountData: MutableLiveData<Int> = MutableLiveData()
    val balance: MutableLiveData<String> = MutableLiveData()
    val data: SingleLiveData<Resource<List<CreateOrderResult>>> = SingleLiveData()
    val data2: SingleLiveData<Resource<String>> = SingleLiveData()
    val data3: SingleLiveData<Resource<String>> = SingleLiveData()


    init {
        updateQuantityAndTotalAmount()
        balance()
    }

    private fun updateQuantityAndTotalAmount() {
        totalAmount = quantity * price * amount

        val appUser = userStorage.get()

        val credit = userStorage.get()?.availableCredit ?: 0.0
//        if (totalAmount > credit && appUser?.token != null) {
//            quantity--
//            return
//        }

        quantityData.value = quantity
        customAmountData.value = amount
        totalAmountData.value = totalAmount
    }

    fun checkout(item: CreateOrderItem) {
        data.value = Resource.success(ArrayList<CreateOrderResult>())

    }

    fun confirmCheckout(transactionId: String, fortId: String, paymentMethod: String) {
        data2.value = Resource.success(transactionId)
    }

    fun balance() {
        balance.value = userStorage.get()?.availableCredit.toString()
    }

    fun cancelOrder(transactionId: String, reason: String) {
        data3.value = Resource.success(transactionId)
    }
}