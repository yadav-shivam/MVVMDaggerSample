package com.shivam.ecom.common.typeadapter

import com.google.gson.*
import com.google.gson.reflect.TypeToken
import com.shivam.ecom.common.ext.toDateTime
import org.joda.time.DateTime
import java.lang.reflect.Type

class DateTimeTypeConverter : JsonSerializer<DateTime>, JsonDeserializer<DateTime> {

  companion object {
    val TYPE: Type = TypeToken.get(DateTime::class.java).type
  }

  override fun serialize(src: DateTime?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
    return JsonPrimitive(src?.toDateTimeTimeZoneString())
  }

  override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): DateTime? {
    return try {
      json?.asString?.toDateTime()
    } catch (e: Exception) {
      null
    }
  }
}