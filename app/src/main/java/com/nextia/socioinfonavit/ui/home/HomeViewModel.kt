package com.nextia.socioinfonavit.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nextia.socioinfonavit.core.interactor.UseCase
import com.nextia.socioinfonavit.core.presentation.BaseViewModel
import com.nextia.socioinfonavit.data.dto.Benevit
import com.nextia.socioinfonavit.data.dto.BenevitResponse
import com.nextia.socioinfonavit.data.dto.Wallet
import com.nextia.socioinfonavit.domain.usecases.GetBenevitsUC
import com.nextia.socioinfonavit.domain.usecases.GetWalletsUC
import com.nextia.socioinfonavit.domain.usecases.LogoutUC
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getWalletsUC: GetWalletsUC,
    private val getBenevitsUC: GetBenevitsUC,
    private val logoutUC: LogoutUC
) : BaseViewModel() {
    private val _homeViewState = MutableLiveData<HomeViewState>()
    val homeViewState : LiveData<HomeViewState> get() = _homeViewState
    val mWallets = mutableListOf<Wallet>()

    init {
        getWallets()
    }

    fun getWallets(){
        mWallets.clear()
        _homeViewState.value = HomeViewState.LoadingWallets
        getWalletsUC(UseCase.None()){
            it.fold(::handleFailure, ::onWalletsResponse)
        }
    }

    private fun onWalletsResponse(wallets: List<Wallet>) {
        mWallets.addAll(wallets)
        getBenevitsUC(UseCase.None()){
            it.fold(::handleFailure, ::onBenevitsResponse)
        }
    }

    private fun onBenevitsResponse(response: BenevitResponse) {
        for (wallet in mWallets){
            val benevits = mutableListOf<Benevit>()
            val unLocked = response.unlocked.filter { it.wallet.id == wallet.id }.map{it.isLocked = false; it}
            val locked = response.locked.filter { it.wallet.id == wallet.id }.map{it.isLocked = true; it}
            benevits.addAll(unLocked)
            benevits.addAll(locked)

            wallet.benevit = benevits
        }

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
}