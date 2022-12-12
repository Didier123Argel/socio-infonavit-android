package com.nextia.socioinfonavit.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nextia.socioinfonavit.core.interactor.UseCase
import com.nextia.socioinfonavit.core.presentation.BaseViewModel
import com.nextia.socioinfonavit.data.dto.Benevit
import com.nextia.socioinfonavit.data.dto.BenevitResponse
import com.nextia.socioinfonavit.data.dto.SearchRequest
import com.nextia.socioinfonavit.domain.usecases.GetBenevitsUC
import com.nextia.socioinfonavit.domain.usecases.LogoutUC
import com.nextia.socioinfonavit.domain.usecases.SearchBenevitsUC
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getBenevitsUC: GetBenevitsUC,
    private val logoutUC: LogoutUC,
    private val searchBenevitsUC: SearchBenevitsUC
) : BaseViewModel() {
    private val _homeViewState = MutableLiveData<HomeViewState>()
    val homeViewState : LiveData<HomeViewState> get() = _homeViewState
    val mBenevits = mutableListOf<Pair<String, List<Benevit>>>()

    var searchText = ""
    private var searched = false

    fun getBenevits() {
        searched = false
        _homeViewState.value = HomeViewState.LoadingBenevits
        getBenevitsUC(UseCase.None()){
            it.fold(::handleFailure, ::onBenevitsResponse)
        }
    }

    private fun onBenevitsResponse(response: BenevitResponse) {
        val benevitsResponse = response.locked
            .onEach { it.unlocked = false }
            .plus(response.unlocked
            .onEach { it.unlocked = true })
            .toMutableList()

        benevitsResponse.shuffle()
        mBenevits.clear()
        mBenevits.addAll(Collections.unmodifiableList(benevitsResponse)
            .groupBy { it.wallet.name }
            .map { it.toPair() })

        _homeViewState.value = HomeViewState.ShowPlaceHolder(mBenevits.isEmpty())
        _homeViewState.value = HomeViewState.UpdateData
    }

    fun logout(){
        _homeViewState.value = HomeViewState.Loading(true)
        logoutUC(UseCase.None()){
            _homeViewState.value = HomeViewState.Loading(false)
            it.fold(::handleFailure, ::onSuccessLogout)
        }
    }

    private fun onSuccessLogout(response : Unit) {
        _homeViewState.value = HomeViewState.SuccessLogout(response)
    }

    fun onTappedSearch(){
        if (searchText.trim().isEmpty())
            return

        searchBenevits()
    }

    fun onTappedReset() {
        if (searched)
            getBenevits()
    }

    private fun searchBenevits() {
        searched = true
        _homeViewState.value = HomeViewState.LoadingBenevits
        searchBenevitsUC(SearchRequest(searchText)){
            it.fold(::handleFailure, ::onSearchBenevitsResponse)
        }
    }

    private fun onSearchBenevitsResponse(response: List<Benevit>) {
        mBenevits.clear()
        mBenevits.addAll(
            response.groupBy { it.wallet.name }
            .map { it.toPair() }
        )

        _homeViewState.value = HomeViewState.ShowPlaceHolder(mBenevits.isEmpty())
        _homeViewState.value = HomeViewState.UpdateData
    }
}