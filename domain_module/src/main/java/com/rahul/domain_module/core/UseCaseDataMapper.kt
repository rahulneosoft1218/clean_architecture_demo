package com.rahul.domain_module.core

abstract class UseCaseDataMapper<Result, Response> {

    abstract fun mapResultWithData(result: Result?): Response


}