package com.rahul.data_module.source.mock

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.delay
import java.io.File

class MockData {


    companion object {

        @kotlin.jvm.Throws
        internal suspend fun <R> mockResponse(fileName: String, delayTime: Long = 2000L): R {
            try {
                delay(delayTime)
                val jsonFileString = File(fileName).readText()
                return Gson().fromJson(jsonFileString,
                    object : TypeToken<R>() {}.type)
            } catch (e: Exception) {
                throw  Throwable("Mock Response File Not Found!")
            }
        }
    }

}