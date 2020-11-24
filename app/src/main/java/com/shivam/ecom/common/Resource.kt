package com.shivam.ecom.common

data class Resource<out T>(val status: Status, val data: T?, val throwable: Throwable? = null) {
  companion object {
    fun <T> success(data: T) = Resource(Success, data)

    fun error(throwable: Throwable) = Resource(Failure, null, throwable)

    fun <T> loading(data: T? = null) = Resource(Loading, data)
  }
}

sealed class Status
object Success: Status()
object Failure: Status()
object Loading: Status()