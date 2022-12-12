package com.nextia.socioinfonavit.ui.mybenevits

sealed class MyBenefitsViewState {
    object UpdateData : MyBenefitsViewState()
    object LoadingBenevits : MyBenefitsViewState()
    class ShowPlaceHolder(val show: Boolean) : MyBenefitsViewState()
}