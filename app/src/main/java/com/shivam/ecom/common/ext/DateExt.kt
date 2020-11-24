package com.shivam.ecom.common.ext

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

object DateConstants {

  val DATE_TIME_TIMEZONE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
  val TIME_FORMAT = "HH:mm a"
  val DAY_MONTH_SHORT_FORMAT = "d'\n'MMM"
  val DAY_MONTH_YEAR_FORMAT = "yyyy-MM-dd"
  val DAY_MONTH_YEAR_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss"
}

fun DateTime.toDateTimeTimeZoneString(): String {
  return DateTimeFormat.forPattern(DateConstants.DATE_TIME_TIMEZONE_FORMAT)?.print(this) ?: ""
}

fun DateTime.toTimeString() = DateTimeFormat.forPattern(DateConstants.TIME_FORMAT).print(this)

fun DateTime.toDayMonthShortString()
  = DateTimeFormat.forPattern(DateConstants.DAY_MONTH_SHORT_FORMAT).print(this)

fun DateTime.toYearMonthDayString()
  = DateTimeFormat.forPattern(DateConstants.DAY_MONTH_YEAR_FORMAT)?.print(this) ?: ""

fun DateTime.toYearMonthDayTimeString()
  = DateTimeFormat.forPattern(DateConstants.DAY_MONTH_YEAR_TIME_FORMAT)?.print(this) ?: ""

@Throws(IllegalArgumentException::class)
fun String.toDateTime(formatter: String = DateConstants.DATE_TIME_TIMEZONE_FORMAT): DateTime {
  return DateTimeFormat.forPattern(formatter).parseDateTime(this)
}