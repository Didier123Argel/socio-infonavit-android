package com.nextia.socioinfonavit.ui.launch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.nextia.socioinfonavit.R
import com.nextia.socioinfonavit.core.extension.observe
import dagger.hilt.android.AndroidEntryPoint
import com.nextia.socioinfonavit.databinding.FragmentLaunchBinding
import com.nextia.socioinfonavit.core.extension.addTransitionListener
import com.nextia.socioinfonavit.core.presentation.BaseFragment
import com.nextia.socioinfonavit.ui.MainActivity

@AndroidEntryPoint
class LaunchFragment : BaseFragment(R.layout.fragment_launch) {

    private val viewModel by viewModels<LaunchViewModel>()
    private lateinit var binding : FragmentLaunchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.apply {
            observe(launchViewState, ::onViewStateChanged)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_launch, container, false)
        return binding.root
    }

    override fun setBinding(view: View) {
        binding.apply {
            lifecycleOwner = viewLifecycleOwner

            motionLayoutLaunch.addTransitionListener (onTransitionCompleted = { _, _ ->
                binding.progressCircularLaunch.visibility = View.VISIBLE
                viewModel.validateSession()
            })
            (activity as? MainActivity)?.isEnabledDrawer(false)
        }
    }

    private fun onViewStateChanged(viewState: LaunchViewState?) {
        when(viewState) {
            is LaunchViewState.NavigateToLogin -> {
                //navController.navigate(LaunchFragmentDirections.actionLaunchFragmentToLoginFragment())
            }
            is LaunchViewState.NavigateToHome -> {
                //navController.navigate(LaunchFragmentDirections.actionLaunchFragmentToHomeFragment())
            }
            else -> {}
        }
    }
}