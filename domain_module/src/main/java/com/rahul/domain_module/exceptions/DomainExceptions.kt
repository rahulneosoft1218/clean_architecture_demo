package com.rahul.domain_module.exceptions

import com.rahul.data_module.source.exceptions.ApiException

data class DomainExceptions(val domainErrors: DomainErrors, override val message: String) : Throwable(){

    companion object {

        fun Throwable.mapDomainException(): DomainExceptions {

            val apiException: ApiException =
                if (this is ApiException) this else ApiException.UnknownException(error = this.message)


            val message = apiException.errorMsg ?: "Unknown Error"
            return when (apiException) {
                is ApiException.NoInternetConnection -> DomainExceptions(
                    DomainErrors.NO_INTERNET,
                    message
                )
                is ApiException.BadRequestException -> DomainExceptions(
                    DomainErrors.BAD_REQUEST,
                    message
                )
                is ApiException.InternalServerException -> DomainExceptions(
                    DomainErrors.INTERNAL_SERVER,
                    message
                )
                is ApiException.UnknownException -> DomainExceptions(
                    DomainErrors.EXCEPTION,
                    message
                )
            }
        }


    }


}
