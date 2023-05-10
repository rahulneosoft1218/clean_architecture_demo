package com.rahul.cleandemo.base

import androidx.lifecycle.*
import com.rahul.domain_module.core.UseCaseWrapper
import com.rahul.domain_module.exceptions.DomainExceptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {


    private val jobs = ArrayList<Job>()

    fun <R, UI> updateDataState(
        state: MutableLiveData<ResponseData<R, UI>>,
        data: R,
    ) {
        state.postValue(ResponseData.Success(data) as ResponseData<R, UI>)
    }


    @Suppress("UNCHECKED_CAST")
    fun <R, UI> updateUIState(
        state: MutableLiveData<ResponseData<R, UI>>,
        uiEvents: UI,
    ) {
        state.postValue(ResponseData.UIResponse(uiEvents) as ResponseData<R, UI>)
    }

    fun <R, UI> updateFailedDataState(
        state: MutableLiveData<ResponseData<R, UI>>,
        data: DomainExceptions,
    ) {
        state.postValue(ResponseData.Failed(data))
    }


    fun <R, UI> createResponseData() = MutableLiveData<ResponseData<R, UI>>(null)

    fun <R, UI> MutableLiveData<ResponseData<R, UI>>.mapResponseData(): LiveData<ResponseData<R, UI>> =
        this





    protected fun <T> executeUseCaseData(
        useCase: suspend () -> UseCaseWrapper<DomainExceptions, T>,
        result: (UseCaseWrapper<DomainExceptions, T>) -> Unit,
    ) {
        val job = viewModelScope.launch(Dispatchers.IO) {
            result.invoke(useCase.invoke())
        }
        jobs.add(job)
    }



    override fun onCleared() {
        jobs.forEach {
            if (it.isActive || !it.isCancelled) {
                it.cancel()
            }
        }
        super.onCleared()
    }

    open fun onPullToRefresh(refreshId : Int){

    }


}