package com.shivam.ecom.common.file

import android.content.Context
import android.util.Log
import androidx.annotation.RawRes
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.StringWriter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FileLoader @Inject constructor(private val context: Context, private val gson: Gson) {

  companion object {
    val TAG: String = FileLoader::class.java.simpleName
  }

  fun <T> loadFile(clazz: Class<T>, @RawRes id: Int): T {
    return gson.fromJson<T>(getFileFromRes(id), clazz)
  }

  private fun getFileFromRes(@RawRes id: Int): String {
    val resourceReader = context.resources.openRawResource(id)

    val writer = StringWriter()

    try {
      val reader = BufferedReader(InputStreamReader(resourceReader, "UTF-8"))
      var line = reader.readLine()
      while (line != null) {
        writer.write(line)
        line = reader.readLine()
      }
    } catch (e: Exception) {
      Log.e(TAG, "Unhandled exception while using JSONResourceReader", e)
    } finally {
      try {
        resourceReader.close()
      } catch (e: Exception) {
        Log.e(TAG, "Unhandled exception while using JSONResourceReader", e)
      }
    }

    return writer.toString()
  }
}