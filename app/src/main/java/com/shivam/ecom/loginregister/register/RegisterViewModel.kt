package com.shivam.ecom.loginregister.register

import android.content.Context
import com.shivam.ecom.common.Resource
import com.shivam.ecom.common.data.AppUser
import com.shivam.ecom.common.livedata.SingleLiveData
import com.shivam.ecom.common.rx.RxAwareViewModel
import com.shivam.ecom.loginregister.register.data.RegisterResponseItemResult
import javax.inject.Inject

class RegisterViewModel @Inject constructor(
) : RxAwareViewModel() {

    val data: SingleLiveData<Resource<List<RegisterResponseItemResult>>> = SingleLiveData()

    fun register(appUser: AppUser, context: Context?) {

    }

}