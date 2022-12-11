package com.nextia.socioinfonavit.ui.home

sealed class HomeViewState {
    object UpdateData : HomeViewState()
    object LoadingBenevits : HomeViewState()
    class Loading(val show: Boolean) : HomeViewState()
    class SuccessLogout(val success: Unit) : HomeViewState()
}