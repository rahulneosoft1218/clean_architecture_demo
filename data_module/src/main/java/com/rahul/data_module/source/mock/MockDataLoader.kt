package com.rahul.data_module.source.mock

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.delay

abstract class MockDataLoader {


    abstract fun getMockJSONString(): String




}