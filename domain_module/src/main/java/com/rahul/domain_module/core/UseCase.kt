package com.rahul.domain_module.core

import com.rahul.domain_module.exceptions.DomainExceptions
import kotlinx.coroutines.CoroutineScope

abstract class UseCase<Result, Response> : UseCaseBuilder<Result, Response>() {

    abstract suspend fun buildUseCase(scope: CoroutineScope): UseCaseWrapper<DomainExceptions, Response>


}