package com.rahul.domain_module.core

import com.rahul.domain_module.exceptions.DomainExceptions

abstract class UseCaseWithParams<Params, Result, Response> : UseCaseBuilder<Result, Response>() {

    abstract suspend fun buildUseCase(params: Params): UseCaseWrapper<DomainExceptions, Response>


}