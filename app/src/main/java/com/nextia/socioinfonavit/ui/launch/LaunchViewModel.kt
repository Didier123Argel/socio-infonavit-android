package com.nextia.socioinfonavit.ui.launch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nextia.socioinfonavit.core.helpers.Authenticator
import com.nextia.socioinfonavit.core.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LaunchViewModel @Inject constructor(
    private val authenticator: Authenticator
) : BaseViewModel() {

    private val _launchViewState = MutableLiveData<LaunchViewState>()
    val launchViewState : LiveData<LaunchViewState> get() = _launchViewState

    fun validateSession(){
        if (authenticator.isAuthenticated)
            _launchViewState.value = LaunchViewState.NavigateToHome
            else _launchViewState.value = LaunchViewState.NavigateToLogin
    }

}