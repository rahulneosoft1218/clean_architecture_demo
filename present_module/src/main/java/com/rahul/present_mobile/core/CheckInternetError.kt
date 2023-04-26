package com.rahul.present_mobile.core

import android.content.Context
import com.rahul.domain_module.exceptions.DomainErrors
import com.rahul.domain_module.exceptions.DomainExceptions
import com.rahul.present_mobile.utils.checkInternet
import okhttp3.Interceptor
import okhttp3.Response

class CheckInternetError(private val applicationContext: Context?) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        if (!applicationContext.checkInternet()) {
            throw DomainExceptions(DomainErrors.NO_INTERNET, "No Internet Found!")
        }

        return chain.proceed(chain.request())


    }

}
