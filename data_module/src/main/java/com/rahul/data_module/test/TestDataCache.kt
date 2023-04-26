package com.rahul.data_module.test

import com.rahul.data_module.source.cache.IAppCache

class TestDataCache private constructor() : IAppCache {

    companion object {
        @Volatile
        private var testDataCache: TestDataCache? = null

        fun getTestDataCache(): TestDataCache {

            if (testDataCache == null) {
                testDataCache = TestDataCache()
            }

            return testDataCache!!
        }


    }

    private val cacheStrings = HashMap<String, String>()

    override fun getCacheString(key: String): String {
        return cacheStrings[key] ?: ""
    }

    override fun cacheString(key: String, value: String) {
        cacheStrings[key] = value
    }

}