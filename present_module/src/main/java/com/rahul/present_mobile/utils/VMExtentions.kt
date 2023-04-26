package com.rahul.present_mobile.utils

import androidx.lifecycle.MutableLiveData
import com.rahul.domain_module.core.UseCaseWrapper
import com.rahul.domain_module.exceptions.DomainExceptions
import com.rahul.present_mobile.core.ResponseData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

fun <R, UI> MutableLiveData<ResponseData<R, UI>>.updateDataState(
    data: ResponseData<*, *>
) {
    this.value = data as ResponseData<R, UI>
}

fun <R, UI> MutableLiveData<ResponseData<R, UI>>.updateFailedDataState(
    data: ResponseData<R, UI>
) {
    this.value = data
}


fun <R, UI> ResponseData.Init.createNewResponseData(): MutableLiveData<ResponseData<R, UI>> {
    return MutableLiveData<ResponseData<R, UI>>().apply {
        postValue(this@createNewResponseData as ResponseData<R, UI>)
    }
}


fun <T> CoroutineScope.executeUseCaseData(
    useCase: suspend () -> UseCaseWrapper<DomainExceptions, T>,
    result: (UseCaseWrapper<DomainExceptions, T>) -> Unit
): Job {
    return this.launch(Dispatchers.IO) {
        val data = useCase.invoke()
        this.launch(Dispatchers.Main) {
            result.invoke(data)
        }
    }
}