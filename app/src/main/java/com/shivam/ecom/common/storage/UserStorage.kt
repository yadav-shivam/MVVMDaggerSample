package com.shivam.ecom.common.storage

import android.content.SharedPreferences
import com.shivam.ecom.buy.products.data.ProductItemResponse
import com.shivam.ecom.common.data.AppUser
import com.shivam.ecom.common.ext.*
import com.shivam.ecom.loginregister.login.data.Paymentmethod
import com.shivam.ecom.loginregister.login.data.Serviceprovider
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserStorage @Inject constructor(private val prefs: SharedPreferences) {

    companion object {
        private val PREF_USER: String = "PREF_USER"
        private val PREF_LIST_GRID: String = "PREF_LIST_OR_GRID"
        private val PREF_CART: String = "PREF_CART"
        private val PREF_TOKEN: String = "PREF_TOKEN"
        private val PREF_GUEST_CURRENCY = "PREF_GUEST_CURRENCY"
        private val PREF_SERVICEPROVIDER = "PREF_SERVICEPROVIDER"
        private val PREF_PAYMENTMETHOD = "PREF_PAYMENTMETHOD"
        private val PREF_CAPTCHA_TOKEN = "PREF_CAPTCHA_TOKEN"
        private val PREF_FIRST_TIME= "PREF_FIRST_TIME"

        val LIST: String = "list"
        val GRID: String = "grid"


    }

    fun save(user: AppUser) = prefs.putObject(PREF_USER, user)

    fun remove() = prefs.edit { remove(PREF_USER) }

    fun get(): AppUser? = prefs.getObject(PREF_USER, AppUser::class.java)

    fun getViewType(): String = prefs.getString(PREF_LIST_GRID, GRID)

    fun setViewType(viewType: String): Unit = prefs.putStringValue(PREF_LIST_GRID, viewType)

    fun toggleViewType() {

        val viewType = prefs.getString(PREF_LIST_GRID, GRID)
        if (viewType == GRID) {
            setViewType(LIST)
        } else {
            setViewType(GRID)

        }

    }

    fun updateBalance(balance: Double) {
        val appUser = get()
        appUser?.let {
            save(appUser.copy(availableCredit = balance))
        }
    }


    fun saveIntoCart(selectValueResponse: ProductItemResponse) {

        var list: MutableList<ProductItemResponse>? = getCart()
                as MutableList<ProductItemResponse>?

        /*Check for null */
        if (list == null || list.isEmpty()) {
            list = ArrayList<ProductItemResponse>()
        }

        var isAlreadyExist = false
        var indexOfItem = 0

        if (list.size > 0)
            for (item in list) {
                if (item.sku == selectValueResponse.sku) {
                    isAlreadyExist = true
                    indexOfItem = list.indexOf(item)
                    break
                }
            }
        if (isAlreadyExist) {

            val item = list.get(indexOfItem)
            item.quantityCart = item.quantityCart + selectValueResponse.quantityCart
            list.set(indexOfItem, item)

        } else {
            list.add(selectValueResponse)

        }


        prefs.putCart(PREF_CART, list)

    }

    fun updateCart(list: List<ProductItemResponse>) {

        prefs.putCart(PREF_CART, list)

    }

    fun removeCart() {

        prefs.removeItem(PREF_CART)

    }

    fun getCart(): List<ProductItemResponse>? {
        val list: List<ProductItemResponse>? = prefs.getCart(PREF_CART)
        if (list == null || list.isEmpty()) return null
        else return list
    }

    fun saveToken(token: String): Unit = prefs.putStringValue(PREF_TOKEN, token)

    fun getToken(): String = prefs.getString(PREF_TOKEN, "")

    fun saveCaptchaToken(token: String): Unit = prefs.putStringValue(PREF_CAPTCHA_TOKEN, token)

    fun getCaptchaToken(): String = prefs.getString(PREF_CAPTCHA_TOKEN, "")

    fun saveGuestCurrency(currency: String): Unit = prefs.putStringValue(PREF_GUEST_CURRENCY, currency)

    fun getGuestCurrency(): String = prefs.getString(PREF_GUEST_CURRENCY,"SAR")

    fun saveFirstTime(isFirstTime:String): Unit = prefs.putStringValue(PREF_FIRST_TIME,isFirstTime)

    fun isFirstTime(): Boolean = prefs.getString(PREF_FIRST_TIME,"true") == "true"

    fun isCartEmpty(): Boolean {
        val list: List<ProductItemResponse>? = prefs.getCart(PREF_CART)
        if (list == null || list.isEmpty() || list.size <= 0) return true
        else return false
    }

    fun saveServiceprovider(list: List<Serviceprovider>){
        prefs.saveServiceProvider(PREF_SERVICEPROVIDER,list)
    }

    fun getServiceprovider(): List<Serviceprovider>? {
        val list: List<Serviceprovider>? = prefs.getServiceProvider(PREF_SERVICEPROVIDER)
        if (list == null || list.isEmpty()) return null
        else return list
    }

    fun savePaymentmethod(list: MutableList<Paymentmethod>){
        prefs.savePaymentMethod(PREF_PAYMENTMETHOD,list)
    }

    fun getPaymentmethod(): MutableList<Paymentmethod>? {
        val list: MutableList<Paymentmethod>? = prefs.getPaymentMethod(PREF_PAYMENTMETHOD)
        if (list == null || list.isEmpty()) return null
        else return list
    }

    fun getRandomString(sizeOfRandomString: Int): String {
        val ALLOWED_CHARACTERS = "0123456789qwertyuiopasdfghjklzxcvbnm"

        val random = Random()
        val sb = StringBuilder(sizeOfRandomString)
        for (i in 0 until sizeOfRandomString)
            sb.append(ALLOWED_CHARACTERS[random.nextInt(ALLOWED_CHARACTERS.length)])
        return sb.toString()
    }

}