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

    fun updateDataState(state: MutableLiveData<ResponseData>, data: ResponseData) {
        state.value = data
    }

    fun createNewResponseData() = MutableLiveData<ResponseData>(ResponseData.Init)


    protected fun <T> executeUseCaseData(
        useCase: suspend () -> UseCaseWrapper<DomainExceptions, T>,
        result: (UseCaseWrapper<DomainExceptions, T>) -> Unit
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


}