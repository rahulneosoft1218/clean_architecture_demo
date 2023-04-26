package com.rahul.present_mobile.core

import android.provider.CalendarContract.Events
import com.rahul.domain_module.exceptions.DomainExceptions

sealed class ResponseData<Data, Events> {

    object Init : ResponseData<Nothing, Nothing>()
    data class Success<Data>(val data: Data) : ResponseData<Data, Nothing>()
    data class Failed<Data, Events>(val domainExceptions: DomainExceptions) : ResponseData<Data, Events>()
    data class UIEvents<Events>(val uiEvents: Events) : ResponseData<Nothing, Events>()

}