package com.shivam.ecom.common.ext

fun <K, V> Map<K, List<V>>.flattenToList(): List<V> {
  val list = arrayListOf<V>()
  this.values.forEach { list.addAll(it) }
  return list
}

fun ArrayList<String>.fillToHundred():ArrayList<String>{
  for (i in 1 until 100){
    this.add(i.toString())
  }
  return this
}