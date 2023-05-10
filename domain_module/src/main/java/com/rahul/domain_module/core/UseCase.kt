package com.rahul.domain_module.core

import com.rahul.data_module.source.mock.MockDataLoader
import com.rahul.domain_module.exceptions.DomainExceptions
import kotlinx.coroutines.CoroutineScope

abstract class UseCase<Result, Response> : UseCaseBuilder<Result, Response>() {

    abstract suspend fun buildUseCase(scope: CoroutineScope,mockDataLoader: MockDataLoader?=null): UseCaseWrapper<DomainExceptions, Response>


}