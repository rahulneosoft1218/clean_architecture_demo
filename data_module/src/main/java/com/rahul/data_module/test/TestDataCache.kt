package com.rahul.data_module.test

import com.rahul.data_module.source.cache.IAppCache

class TestCache private constructor() : IAppCache {

    companion object {
        @Volatile
        private var testCache: TestCache? = null

        fun getTestCache(): TestCache {

            if (testCache == null) {
                testCache = TestCache()
            }

            return testCache!!
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