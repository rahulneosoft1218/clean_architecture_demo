package com.rahul.domain_module.core

import com.rahul.data_module.source.network.retrofit.ResultWrapper
import com.rahul.data_module.source.exceptions.ApiException
import com.rahul.domain_module.exceptions.DomainExceptions
import com.rahul.domain_module.exceptions.DomainExceptions.Companion.mapDomainException

open class UseCaseBuilder<Result, Response> {

    private fun ResultWrapper<ApiException, Result>.mapResponse(useCaseDataMapper: UseCaseDataMapper<Result, Response>): UseCaseWrapper<DomainExceptions, Response> {

        return when (this) {
            is ResultWrapper.Success -> {
                val mappedResult = useCaseDataMapper.mapResultWithData(this.value)
                UseCaseWrapper.Success(mappedResult)
            }
            is ResultWrapper.Error -> UseCaseWrapper.Error(error.mapDomainException())
        }
    }

    protected suspend fun executeUseCase(
        useCaseDataMapper: UseCaseDataMapper<Result, Response>,
        dataCall: suspend () -> ResultWrapper<ApiException, Result>
    ): UseCaseWrapper<DomainExceptions, Response> {
        return dataCall.invoke().mapResponse(useCaseDataMapper)
    }


}