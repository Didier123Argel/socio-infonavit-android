package com.nextia.socioinfonavit.ui.mybenevits

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.nextia.socioinfonavit.R
import com.nextia.socioinfonavit.core.exception.Failure
import com.nextia.socioinfonavit.core.extension.observe
import com.nextia.socioinfonavit.core.presentation.BaseActivity
import com.nextia.socioinfonavit.core.presentation.BaseFragment
import com.nextia.socioinfonavit.databinding.FragmentMyBenefitsBinding
import com.nextia.socioinfonavit.ui.adapters.WalletsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyBenefitsFragment : BaseFragment(R.layout.fragment_my_benefits) {

    private val viewModel by viewModels<MyBenefitsViewModel>()
    private lateinit var binding : FragmentMyBenefitsBinding
    private val myBenevitsAdapter = WalletsAdapter()

    override fun useAppBar(): Boolean = true
    override fun useDrawer(): Boolean = false
    override fun setToolbar(): BaseActivity.ToolbarContent {
        return BaseActivity.ToolbarContent(
            contentToolbar = R.layout.container_toolbar_my_benefits,
            navigationIcon = R.drawable.ic_arrow_back,
            onClickNavigationIcon = {
                baseActivity.onBackPressedDispatcher.onBackPressed()
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.apply {
            observe(homeViewState, ::onStateViewChanged)
            observe(failure, this@MyBenefitsFragment::handleFailure)
        }
    }

    override fun setBinding(view: View) {
        binding = FragmentMyBenefitsBinding.bind(view)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            rvMyBenevits.adapter = myBenevitsAdapter
            myBenevitsAdapter.listItems = this@MyBenefitsFragment.viewModel.mBenevits
            splMyBenefits.setOnRefreshListener {
                splMyBenefits.isRefreshing = false
                this@MyBenefitsFragment.viewModel.getBenevits()
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun onStateViewChanged(viewState: MyBenefitsViewState?) {
        when(viewState) {
            is MyBenefitsViewState.ShowPlaceHolder -> {
                binding.viewPlaceHolderMyBenefits.isVisible = viewState.show
            }
            is MyBenefitsViewState.LoadingBenevits -> {
                enableSkeleton()
            }
            is MyBenefitsViewState.UpdateData -> {
                disableSkeleton()
                myBenevitsAdapter.notifyDataSetChanged()
            }
            else -> {}
        }
    }

    override fun handleFailure(failure: Failure?) {
        disableSkeleton()
        super.handleFailure(failure)
    }

    private fun disableSkeleton(){
        binding.shimmerFrameLayoutMyBenefits.stopShimmer()
        binding.shimmerFrameLayoutMyBenefits.visibility = View.GONE
        binding.splMyBenefits.visibility = View.VISIBLE
        binding.rvMyBenevits.visibility = View.VISIBLE
    }

    private fun enableSkeleton(){
        binding.shimmerFrameLayoutMyBenefits.startShimmer()
        binding.shimmerFrameLayoutMyBenefits.visibility = View.VISIBLE
        binding.splMyBenefits.visibility = View.GONE
        binding.rvMyBenevits.visibility = View.GONE
    }

    override fun onResume() {
        super.onResume()
        binding.shimmerFrameLayoutMyBenefits.startShimmer()
    }

    override fun onPause() {
        binding.shimmerFrameLayoutMyBenefits.stopShimmer()
        super.onPause()
    }
}