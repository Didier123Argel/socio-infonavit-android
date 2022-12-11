package com.nextia.socioinfonavit.ui.launch

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.nextia.socioinfonavit.R
import com.nextia.socioinfonavit.core.extension.observe
import dagger.hilt.android.AndroidEntryPoint
import com.nextia.socioinfonavit.databinding.FragmentLaunchBinding
import com.nextia.socioinfonavit.core.extension.addTransitionListener
import com.nextia.socioinfonavit.core.presentation.BaseFragment

@AndroidEntryPoint
class LaunchFragment : BaseFragment(R.layout.fragment_launch) {

    private val viewModel by viewModels<LaunchViewModel>()
    private lateinit var binding : FragmentLaunchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBarColor(R.color.red)
        viewModel.apply {
            observe(launchViewState, ::onViewStateChanged)
        }
    }

    override fun setBinding(view: View) {
        binding = FragmentLaunchBinding.bind(view)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            motionLayoutLaunch.addTransitionListener (onTransitionCompleted = { _, _ ->
                progressCircularLaunch.visibility = View.VISIBLE
                viewModel.validateSession()
            })
        }
        isEnabledDrawer(false)
    }

    private fun onViewStateChanged(viewState: LaunchViewState?) {
        when(viewState) {
            is LaunchViewState.NavigateToLogin -> {
                navController.navigate(LaunchFragmentDirections.actionLaunchFragmentToLoginFragment())
            }
            is LaunchViewState.NavigateToHome -> {
                navController.navigate(LaunchFragmentDirections.actionLaunchFragmentToHomeFragment())
            }
            else -> {}
        }
    }
}