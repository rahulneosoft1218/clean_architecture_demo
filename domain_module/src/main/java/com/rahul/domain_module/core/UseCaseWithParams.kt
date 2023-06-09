package com.rahul.domain_module.core

import com.rahul.data_module.source.mock.MockDataLoader
import com.rahul.domain_module.exceptions.DomainExceptions
import kotlinx.coroutines.CoroutineScope

abstract class UseCaseWithParams<Params, Result, Response> : UseCaseBuilder<Result, Response>() {

    abstract suspend fun buildUseCase(scope:CoroutineScope,params: Params,mockDataLoader: MockDataLoader?=null): UseCaseWrapper<DomainExceptions, Response>


}