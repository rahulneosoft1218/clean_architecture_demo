package com.rahul.data_module.source.cache

interface IAppCache {

    fun getCacheString(key: String): String
    fun cacheString(key: String, value: String)

    companion object {

        const val DEFAULT_CURRENCY = "default_currency"

    }

}