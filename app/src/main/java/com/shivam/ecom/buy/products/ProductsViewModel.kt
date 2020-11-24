package com.shivam.ecom.buy.products

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.shivam.ecom.buy.products.data.Filterdet
import com.shivam.ecom.common.Resource
import com.shivam.ecom.common.rx.RxAwareViewModel
import com.shivam.ecom.common.storage.UserStorage
import com.shivam.ecom.dashboard.products.data.ProductItemResponse
import javax.inject.Inject

class ProductsViewModel @Inject constructor(private val userStorage: UserStorage
) : RxAwareViewModel() {

    val data: MutableLiveData<Resource<List<ProductItemResponse>>> = MutableLiveData()
    val filterLiveData: MutableLiveData<Resource<List<Filterdet>>> = MutableLiveData()
    val balance: MutableLiveData<String> = MutableLiveData()
    private val selectedFilterInput: MutableLiveData<String> = MutableLiveData()

    val filteredData: LiveData<List<ProductItemResponse>> = Transformations.switchMap(selectedFilterInput) { text ->
        val result = MutableLiveData<List<ProductItemResponse>>()
        if (TextUtils.isEmpty(text)){
         result.postValue(data.value?.data)
        }else {
            val filteredList = data.value?.data?.filter { it.equals(text) }.orEmpty()
            result.postValue(filteredList)
        }
        result
    }

    init {
        balance()
    }

    fun getValues(category: String,filter:String, showLoading: Boolean = true) {
        var list : ArrayList<ProductItemResponse> = ArrayList()
        data.value = Resource.success(list)

    }

    fun balance() {
        val availableCredit = userStorage.get()?.availableCredit
        availableCredit?.let {
            balance.value = availableCredit.toString()
        }
    }

    fun filter(text: String) {
        selectedFilterInput.postValue(text)
    }
}