package com.rahul.present_mobile.core

import com.rahul.domain_module.exceptions.DomainExceptions

sealed class ResponseData {

    object Init : ResponseData()
    object ShowLoading : ResponseData()
    object ShowPageLoading : ResponseData()
    object HideLoading : ResponseData()
    object HidePageLoading : ResponseData()
    data class Success<Data>(val data: Data) : ResponseData()
    data class Failed(val domainExceptions: DomainExceptions) : ResponseData()

}