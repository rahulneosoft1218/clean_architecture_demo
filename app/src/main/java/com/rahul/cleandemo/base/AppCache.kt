package com.rahul.cleandemo.base

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.rahul.data_module.source.cache.IAppCache


class AppCache private constructor(application: Application) : IAppCache {


    companion object {

        @Volatile
        private var appCache: AppCache? = null

        fun getAppCache(application: Application): AppCache {

            if (appCache == null) {
                //synchronized block to remove overhead
                synchronized(AppCache::class.java) {
                    if (appCache == null) {
                        // if instance is null, initialize
                        appCache = AppCache(application)
                    }
                }
            }

            return appCache!!
        }

    }

    private val prefs: SharedPreferences =
        application.getSharedPreferences("AppCache", Context.MODE_PRIVATE)


    override fun getCacheString(key: String): String {
        return prefs.getString(key, "")!!
    }

    override fun cacheString(key: String, value: String) {
        prefs.edit().putString(key, value).apply()
    }

}