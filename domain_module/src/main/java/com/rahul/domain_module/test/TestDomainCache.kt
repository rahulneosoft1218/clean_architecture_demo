package com.rahul.domain_module.test

import com.rahul.data_module.source.cache.IAppCache

class TestDomainCache private constructor() : IAppCache {

    companion object {
        @Volatile
        private var testDataCache: TestDomainCache? = null

        fun getTestDomainCache(): TestDomainCache {

            if (testDataCache == null) {
                testDataCache = TestDomainCache()
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