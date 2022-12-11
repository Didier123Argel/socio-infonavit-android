package com.nextia.socioinfonavit.ui.sigin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nextia.socioinfonavit.core.helpers.Authenticator
import com.nextia.socioinfonavit.core.presentation.BaseViewModel
import com.nextia.socioinfonavit.data.dto.LoginRequest
import com.nextia.socioinfonavit.data.dto.UserResponse
import com.nextia.socioinfonavit.domain.usecases.LogInUC
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val logInUC: LogInUC,
    private val authenticator: Authenticator
) : BaseViewModel() {
    private val _loginViewState = MutableLiveData<LoginViewState>()
    val loginViewState : LiveData<LoginViewState> get() = _loginViewState

    var user: String = ""
    var password: String = ""

    fun onTappedLogin() {
        when {
            user.isEmpty() -> {
                _loginViewState.value = LoginViewState.UserInvalid
                return
            }
            password.isEmpty() -> {
                _loginViewState.value = LoginViewState.PasswordEmpty
                return
            }
        }

        _loginViewState.value = LoginViewState.ShowProgress(true)
        logInUC(LoginRequest(user, password)) {
            _loginViewState.value = LoginViewState.ShowProgress(false)
            it.fold(::handleFailure, ::onLoginResponse)
        }
    }

    private fun onLoginResponse(response: UserResponse) {
        authenticator.user = response
        _loginViewState.value = LoginViewState.GoToHome
    }

    fun onKeysChanged(){
        val isEnable = user.isNotEmpty() && password.isNotEmpty()
        _loginViewState.value = LoginViewState.EnableLogIn(isEnable)
    }

}