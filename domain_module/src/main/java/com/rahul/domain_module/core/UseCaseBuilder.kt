package com.rahul.domain_module.core

import com.rahul.data_module.source.ResultWrapper
import com.rahul.data_module.source.exceptions.ApiException
import com.rahul.domain_module.exceptions.DomainErrors
import com.rahul.domain_module.exceptions.DomainExceptions

open class UseCaseBuilder<Result, Response> {

    protected fun ResultWrapper<ApiException, Result>.mapResponse(useCaseDataMapper: UseCaseDataMapper<Result, Response>): ResultWrapper<DomainExceptions, Response> {

        return when (this) {
            is ResultWrapper.Success -> ResultWrapper.Success(
                useCaseDataMapper.mapResultWithData(
                    this.value
                )
            )
            is ResultWrapper.Error -> mapToDomainException(this.error)
        }
    }

    private fun mapToDomainException(error: ApiException): ResultWrapper<DomainExceptions, Response> {
        val message = error.errorMsg ?: "Unknown Error"
        val domainException: DomainExceptions = when (error) {
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
            is ApiException.UnknownException -> DomainExceptions(DomainErrors.EXCEPTION, message)
        }

        return ResultWrapper.Error(domainException)
    }


}