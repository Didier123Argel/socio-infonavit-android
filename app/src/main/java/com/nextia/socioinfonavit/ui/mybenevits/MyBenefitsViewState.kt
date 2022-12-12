package com.nextia.socioinfonavit.ui.mybenevits

sealed class MyBenefitsViewState {
    object UpdateData : MyBenefitsViewState()
    object LoadingBenevits : MyBenefitsViewState()
    class Loading(val show: Boolean) : MyBenefitsViewState()
    class SuccessLogout(val success: Unit) : MyBenefitsViewState()
}