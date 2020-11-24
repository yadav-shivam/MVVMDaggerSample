package com.shivam.ecom

import com.shivam.ecom.common.ext.flattenToList
import com.shivam.ecom.dashboard.myorders.data.MyOrdersItem
import org.joda.time.DateTime
import org.junit.Assert
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
  @Test
  fun addition_isCorrect() {

    val map = HashMap<String, List<MyOrdersItem>>()

    map.put("key1", arrayListOf(
      MyOrdersItem("transId", DateTime.now(), 1, 10, "categoryName", ""),
      MyOrdersItem("transId", DateTime.now(), 1, 10, "categoryName", ""),
      MyOrdersItem("transId", DateTime.now(), 1, 10, "categoryName", "")
    ))

    map.put("key2", arrayListOf(
      MyOrdersItem("transId", DateTime.now().plusDays(1), 1, 10, "categoryName", ""),
      MyOrdersItem("transId", DateTime.now().plusDays(1), 1, 10, "categoryName", ""),
      MyOrdersItem("transId", DateTime.now().plusDays(1), 1, 10, "categoryName", "")
    ))

    map.put("key3", arrayListOf(
      MyOrdersItem("transId", DateTime.now().minusDays(1), 1, 10, "categoryName", ""),
      MyOrdersItem("transId", DateTime.now().minusDays(1), 1, 10, "categoryName", ""),
      MyOrdersItem("transId", DateTime.now().minusDays(1), 1, 10, "categoryName", "")
    ))

    val list = map.flattenToList()
    Assert.assertEquals(list.size, 9)
  }
}
