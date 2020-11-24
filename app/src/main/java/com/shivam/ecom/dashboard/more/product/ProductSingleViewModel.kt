package com.shivam.ecom.dashboard.more.product

import com.shivam.ecom.common.Resource
import com.shivam.ecom.common.livedata.SingleLiveData
import com.shivam.ecom.common.rx.RxAwareViewModel
import com.shivam.ecom.common.rx.RxSchedulers
import com.shivam.ecom.dashboard.products.data.ProductItemResponse
import javax.inject.Inject

class ProductSingleViewModel @Inject constructor(private val rxSchedulers: RxSchedulers
) : RxAwareViewModel() {

    val data: SingleLiveData<Resource<List<ProductItemResponse>>> = SingleLiveData()

    fun products(showLoading: Boolean = true) {
    }


}