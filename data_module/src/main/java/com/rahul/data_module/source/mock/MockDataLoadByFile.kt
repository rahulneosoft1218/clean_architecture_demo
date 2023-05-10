package com.rahul.data_module.source.mock

import java.io.File

class MockDataLoadByFile(private val filePath: String) : MockDataLoader() {
    override fun getMockJSONString(): String {
        try {
            return File(filePath).readText()
        } catch (e: Exception) {
            throw  Throwable("Mock Response File Not Found!")
        }
    }


}