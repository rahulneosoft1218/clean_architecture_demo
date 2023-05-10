package com.rahul.data_module.source.mock

import java.io.InputStream
import java.nio.charset.StandardCharsets

class MockDataLoadByInputStream(private val iStream: InputStream?) : MockDataLoader() {
    override fun getMockJSONString(): String {
        try {
            val size: Int = iStream?.available() ?: 0
            val buffer = ByteArray(size)
            iStream?.read(buffer)
            iStream?.close()
            return String(buffer, StandardCharsets.UTF_8)
        } catch (e: Exception) {
            throw  Throwable("Mock Response InputStream Not Found!")
        }
    }


}