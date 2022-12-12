package com.nextia.socioinfonavit.ui.mybenevits

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nextia.socioinfonavit.core.interactor.UseCase
import com.nextia.socioinfonavit.core.presentation.BaseViewModel
import com.nextia.socioinfonavit.data.dto.Benevit
import com.nextia.socioinfonavit.data.dto.BenevitResponse
import com.nextia.socioinfonavit.domain.usecases.GetBenevitsUC
import com.nextia.socioinfonavit.domain.usecases.LogoutUC
import com.nextia.socioinfonavit.ui.adapters.TYPE_UNLOCKED
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MyBenefitsViewModel @Inject constructor(
    private val getBenevitsUC: GetBenevitsUC,
    private val logoutUC: LogoutUC
) : BaseViewModel() {
    private val _homeViewState = MutableLiveData<MyBenefitsViewState>()
    val homeViewState : LiveData<MyBenefitsViewState> get() = _homeViewState
    val mBenevits = mutableListOf<Pair<String, List<Benevit>>>()
    init {
        getBenevits()
    }

    fun getBenevits() {
        _homeViewState.value = MyBenefitsViewState.LoadingBenevits
        getBenevitsUC(UseCase.None()){
            it.fold(::handleFailure, ::onBenevitsResponse)
        }
    }

    private fun onBenevitsResponse(response: BenevitResponse) {
        val benevitsResponse = response.locked.plus(response.unlocked.onEach { it.typeLocket = TYPE_UNLOCKED }).toMutableList()
        benevitsResponse.shuffle()
        mBenevits.addAll(Collections.unmodifiableList(benevitsResponse)
            .groupBy { it.wallet.name }
            .map { it.toPair() })
        _homeViewState.value = MyBenefitsViewState.UpdateData
    }

    fun logout(){
        _homeViewState.value = MyBenefitsViewState.Loading(true)
        logoutUC(UseCase.None()){
            _homeViewState.value = MyBenefitsViewState.Loading(false)
            it.fold(::handleFailure, ::onSuccessLogout)
        }
    }

    private fun onSuccessLogout(response : Unit) {
        _homeViewState.value = MyBenefitsViewState.SuccessLogout(response)
    }
}