package com.nextia.socioinfonavit.ui.mybenevits

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nextia.socioinfonavit.core.interactor.UseCase
import com.nextia.socioinfonavit.core.presentation.BaseViewModel
import com.nextia.socioinfonavit.data.dto.Benevit
import com.nextia.socioinfonavit.data.dto.BenevitResponse
import com.nextia.socioinfonavit.domain.usecases.GetBenevitsUC
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MyBenefitsViewModel @Inject constructor(
    private val getBenevitsUC: GetBenevitsUC
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
        val benevitsResponse = response.unlocked
            .onEach { it.unlocked = true }
            .toMutableList()
        benevitsResponse.shuffle()
        mBenevits.addAll(Collections.unmodifiableList(benevitsResponse)
            .groupBy { it.wallet.name }
            .map { it.toPair() })
        _homeViewState.value = MyBenefitsViewState.ShowPlaceHolder(mBenevits.isEmpty())
        _homeViewState.value = MyBenefitsViewState.UpdateData
    }
}