package com.nextia.socioinfonavit.core.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nextia.socioinfonavit.core.exception.Failure

abstract class BaseViewModel: ViewModel() {
    var failure: MutableLiveData<Failure> = MutableLiveData()

    protected fun handleFailure(failure: Failure){
        this.failure.value = failure
    }
}