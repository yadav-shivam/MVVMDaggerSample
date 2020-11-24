package com.shivam.ecom.common.ext

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.shivam.ecom.buy.products.data.ProductItemResponse
import com.shivam.ecom.loginregister.login.data.Paymentmethod
import com.shivam.ecom.loginregister.login.data.Serviceprovider

inline fun SharedPreferences.edit(preferApply: Boolean = false,
                                  f: SharedPreferences.Editor.() -> Unit) {
    val editor = edit()
    editor.f()
    if (preferApply) editor.apply() else editor.commit()
}

fun <T> SharedPreferences.putObject(key: String, t: T) {
    edit {
        putString(key, Gson().toJson(t))
    }
}

fun SharedPreferences.putStringValue(key: String, t: String) {
    edit {
        putString(key, t)
    }
}

fun <T> SharedPreferences.getObject(key: String, clazz: Class<T>): T? {
    val json = getString(key, "")
    if (json.isNullOrEmpty()) return null

    return Gson().fromJson<T>(json, clazz)
}

fun SharedPreferences.putCart(key: String, list: List<ProductItemResponse>) {
    edit {
        putString(key, Gson().toJson(list))
    }
}

fun SharedPreferences.saveServiceProvider(key: String, list: List<Serviceprovider>) {
    edit {
        putString(key, Gson().toJson(list))
    }
}

fun SharedPreferences.getServiceProvider(key: String): List<Serviceprovider>? {
    val json = getString(key, "")
    if (json.isNullOrEmpty()) return null

    val type = object : TypeToken<List<Serviceprovider>>() {}.type
    return Gson().fromJson<List<Serviceprovider>>(json, type)
}

fun SharedPreferences.savePaymentMethod(key: String, list: MutableList<Paymentmethod>) {
    edit {
        putString(key, Gson().toJson(list))
    }
}

fun SharedPreferences.getPaymentMethod(key: String): MutableList<Paymentmethod>? {
    val json = getString(key, "")
    if (json.isNullOrEmpty()) return null

    val type = object : TypeToken<List<Paymentmethod>>() {}.type
    return Gson().fromJson<MutableList<Paymentmethod>>(json, type)
}

fun SharedPreferences.getCart(key: String): List<ProductItemResponse>? {
    val json = getString(key, "")
    if (json.isNullOrEmpty()) return null

    val type = object : TypeToken<List<ProductItemResponse>>() {}.type
    return Gson().fromJson<List<ProductItemResponse>>(json, type)
}


fun SharedPreferences.removeItem(key: String) {
    edit {
        remove(key)
    }
}