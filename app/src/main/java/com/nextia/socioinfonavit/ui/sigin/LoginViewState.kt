package com.nextia.socioinfonavit.ui.sigin

sealed class LoginViewState {
    object UserInvalid : LoginViewState()
    object PasswordEmpty : LoginViewState()
    object GoToHome : LoginViewState()
    class EnableLogIn(val enable: Boolean) : LoginViewState()
    class ShowProgress(val show: Boolean) : LoginViewState()
}