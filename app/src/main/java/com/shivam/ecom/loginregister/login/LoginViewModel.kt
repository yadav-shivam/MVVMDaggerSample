package com.shivam.ecom.loginregister.login

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.shivam.ecom.common.Resource
import com.shivam.ecom.common.rx.RxAwareViewModel
import com.shivam.ecom.common.storage.UserStorage
import com.shivam.ecom.loginregister.login.data.LoginResult
import javax.inject.Inject

class LoginViewModel @Inject constructor(         private val userStorage: UserStorage
) : RxAwareViewModel() {

    val data: MutableLiveData<Resource<LoginResult>> = MutableLiveData()

    fun login(username: String, password: String, context: Context?) {
       /* disposables += repository.login(username, password,
                userStorage.getToken(), context)
                .subscribeOn(rxSchedulers.network())
                .observeOn(rxSchedulers.main())
                .doOnSubscribe { data.value = Resource.loading() }
                .subscribe(
                        {
                            var currencydetails: List<Currencyetail>? = it.currencyetails
                            var currentSelectedCurrency = ""

                            if (currencydetails != null && currencydetails.size > 0)
                                for (currency in currencydetails) {
                                    if (currency.isselected.toLowerCase()
                                                    .equals("true")) {
                                        currentSelectedCurrency = currency.currency
                                        break
                                    }
                                }
                            userStorage.save(AppUser(it.userType,
                                    it.token, it.fullName,
                                    it.phoneNumber, it.email, it.availableCredit, points = it.points ?: 0,
                                    currencydetails = currencydetails, currentSelectedCurrency =
                            currentSelectedCurrency,refCode = it.refCode.orEmpty())
                            )
                            data.value = Resource.success(it)

                            var serviceprovider: List<Serviceprovider>? = it.serviceprovider
                            if (serviceprovider != null) {
                                userStorage.saveServiceprovider(serviceprovider)
                            }
                            Log.wtf("SERVICEPROVIDER", serviceprovider.toString())

                            var paymentmethod: MutableList<Paymentmethod>? = it.paymentmethod
                            if (paymentmethod != null) {
                                userStorage.savePaymentmethod(paymentmethod)
                            }
                            Log.wtf("Paymentmethod", paymentmethod.toString())



                        },
                        { data.value = Resource.error(it) }
                )*/

//        data.value = Resource.success()
    }
}