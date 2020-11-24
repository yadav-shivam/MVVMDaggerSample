package com.shivam.ecom.dashboard.products

import androidx.lifecycle.MutableLiveData
import com.shivam.ecom.R
import com.shivam.ecom.common.rx.RxAwareViewModel
import com.shivam.ecom.common.storage.UserStorage
import com.shivam.ecom.dashboard.products.data.ProductItemResponse
import javax.inject.Inject

class ProductsViewModel
@Inject constructor(private val userStorage: UserStorage
) : RxAwareViewModel() {

    val data: MutableLiveData<List<ProductItemResponse>> = MutableLiveData()
    val balance: MutableLiveData<String> = MutableLiveData()

    private val searchInput: MutableLiveData<String> = MutableLiveData()


    init {
        products()
        balance()
    }

    fun products(showLoading: Boolean = true) {
    var list: ArrayList<ProductItemResponse> = java.util.ArrayList<ProductItemResponse>()
    list.add(ProductItemResponse("Coloured gel pen","Pen", R.drawable.pen,100,1))
    list.add(ProductItemResponse("Multi Colour  3 Set of shirt","Shirt",R.drawable.shirt,700,1))
    list.add(ProductItemResponse("A pair of Bata shoes ","shoes",R.drawable.shoes,500,1))
    list.add(ProductItemResponse("Dell 32 inch Screen laptop","laptop",R.drawable.laptop,60000,1))
    list.add(ProductItemResponse("Study table","table",R.drawable.table,1500,1))
    list.add(ProductItemResponse("6 - Sitting dining table  ","Test 3",R.drawable.dning_table,16000,1))
        data.value=list
    }

    fun filter(text: String) {
        searchInput.postValue(text)
    }

    fun balance() {
        val availableCredit = userStorage.get()?.availableCredit
        availableCredit?.let {
            balance.value = availableCredit.toString()
        }
    }
}