package com.nextia.socioinfonavit.ui.launch

sealed class LaunchViewState {
    object NavigateToLogin : LaunchViewState()
    object NavigateToHome : LaunchViewState()
}