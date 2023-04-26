package com.rahul.present_mobile.core

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rahul.domain_module.core.UseCaseWrapper
import com.rahul.domain_module.exceptions.DomainExceptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    private val jobs = ArrayList<Job>()

    fun <R, UI> updateDataState(
        state: MutableLiveData<ResponseData<R, UI>>,
        data: ResponseData<*, *>
    ) {
        state.postValue(data as ResponseData<R, UI>)
    }

    fun <R, UI> updateFailedDataState(
        state: MutableLiveData<ResponseData<R, UI>>,
        data: ResponseData<R, UI>
    ) {
        state.postValue(data)
    }


    fun <R, UI> createNewResponseData() =
        MutableLiveData<ResponseData<R, UI>>().apply { postValue(ResponseData.Init as ResponseData<R, UI>) }


    protected fun <T> executeUseCaseData(
        useCase: suspend () -> UseCaseWrapper<DomainExceptions, T>,
        result: (UseCaseWrapper<DomainExceptions, T>) -> Unit
    ) {
        val job = viewModelScope.launch(Dispatchers.IO) {
            result.invoke(useCase.invoke())
        }
        jobs.add(job)
    }

//    fun  <R,E>  checkFailedResponse(
//        state: MutableLiveData<ResponseData <R,E>>,
//        domainExceptions: DomainExceptions
//    ) {
//        if (domainExceptions.domainErrors.ordinal == DomainErrors.NO_INTERNET.ordinal) {
//            updateDataState(state, ResponseData.UIEvents(UIEvents.OnNoInternet  as ResponseData.UIEvents<E>))
//            updateDataState(state, ResponseData.UIEvents<E>(UIEvents.OnNoInternet))
//        } else {
//            updateDataState(
//                state, ResponseData.UIEvents<E>(UIEvents.ShowError(domainExceptions.message))
//            )
//        }
//    }


    override fun onCleared() {
        jobs.forEach {
            if (it.isActive || !it.isCancelled) {
                it.cancel()
            }
        }
        super.onCleared()
    }


}