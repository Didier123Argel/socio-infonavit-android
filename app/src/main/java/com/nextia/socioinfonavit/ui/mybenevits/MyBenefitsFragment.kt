package com.nextia.socioinfonavit.ui.mybenevits

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.nextia.socioinfonavit.R
import com.nextia.socioinfonavit.core.exception.Failure
import com.nextia.socioinfonavit.core.extension.observe
import com.nextia.socioinfonavit.core.presentation.BaseFragment
import com.nextia.socioinfonavit.core.presentation.ManuTapListener
import com.nextia.socioinfonavit.databinding.FragmentHomeBinding
import com.nextia.socioinfonavit.ui.MainActivity
import com.nextia.socioinfonavit.ui.adapters.WalletsAdapter
import com.nextia.socioinfonavit.ui.customdialogs.CustomAlertDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyBenefitsFragment : BaseFragment(R.layout.fragment_home) {

    private val viewModel by viewModels<MyBenefitsViewModel>()
    private lateinit var binding : FragmentHomeBinding
    private val walletsAdapter = WalletsAdapter()

    override fun useAppBar(): Boolean = true
    override fun useDrawer(): Boolean = true
    override fun setMenuTapListener(): ManuTapListener = { onMenuTapped(it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.apply {
            observe(homeViewState, ::onStateViewChanged)
            observe(failure, this@MyBenefitsFragment::handleFailure)
        }
    }

    override fun setBinding(view: View) {
        binding = FragmentHomeBinding.bind(view)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            rvWallets.adapter = walletsAdapter
            walletsAdapter.listItems = this@MyBenefitsFragment.viewModel.mBenevits
            splWallets.setOnRefreshListener {
                splWallets.isRefreshing = false
                this@MyBenefitsFragment.viewModel.getBenevits()
            }
        }
    }

    private fun onMenuTapped(item: MainActivity.MenuTags){
        when(item){
            MainActivity.MenuTags.ITEM_LOGOUT -> {
                showDialogLogout()
            }
        }
    }

    private fun showDialogLogout(){
        CustomAlertDialog.createDialogTwoOptions(
            requireActivity(),
            getString(R.string.confirm_dialog_logout),
            getString(R.string.option_accept),
            getString(R.string.option_cancel),
            positiveClicked = {
                viewModel.logout()
            }
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun onStateViewChanged(viewState: MyBenefitsViewState?) {
        when(viewState) {
            is MyBenefitsViewState.Loading -> showLoader(viewState.show)
            is MyBenefitsViewState.LoadingBenevits -> {
                enableSkeleton()
            }
            is MyBenefitsViewState.UpdateData -> {
                disableSkeleton()
                walletsAdapter.notifyDataSetChanged()
            }

            is MyBenefitsViewState.SuccessLogout ->{
                //navController.navigate(HomeFragmentDirections.actionHomeFragmentToLoginFragment())
            }
            else -> {}
        }
    }

    override fun handleFailure(failure: Failure?) {
        disableSkeleton()
        super.handleFailure(failure)
    }

    private fun disableSkeleton(){
        binding.shimmerFrameLayout.stopShimmer()
        binding.shimmerFrameLayout.visibility = View.GONE
        binding.splWallets.visibility = View.VISIBLE
        binding.rvWallets.visibility = View.VISIBLE
    }

    private fun enableSkeleton(){
        binding.shimmerFrameLayout.startShimmer()
        binding.shimmerFrameLayout.visibility = View.VISIBLE
        binding.splWallets.visibility = View.GONE
        binding.rvWallets.visibility = View.GONE
    }

    override fun onResume() {
        super.onResume()
        binding.shimmerFrameLayout.startShimmer()
    }

    override fun onPause() {
        binding.shimmerFrameLayout.stopShimmer()
        super.onPause()
    }
}