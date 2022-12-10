package com.nextia.socioinfonavit.ui.launch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.nextia.socioinfonavit.R
import com.nextia.socioinfonavit.core.extension.observe
import dagger.hilt.android.AndroidEntryPoint
import com.nextia.socioinfonavit.databinding.FragmentLaunchBinding
import com.nextia.socioinfonavit.core.extension.addTransitionListener
import com.nextia.socioinfonavit.ui.MainActivity

@AndroidEntryPoint
class LaunchFragment : Fragment() {

    private val viewModel by viewModels<LaunchViewModel>()
    private lateinit var binding : FragmentLaunchBinding
    private val navController by lazy { findNavController() }

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
        setUpView()
        return binding.root
    }


    private fun setUpView() {
        binding.motionLayoutLaunch.addTransitionListener (onTransitionCompleted = { _, _ ->
            binding.progressCircularLaunch.visibility = View.VISIBLE
            viewModel.validateSession()
        })
        (activity as? MainActivity)?.isEnabledDrawer(false)
    }

    private fun onViewStateChanged(viewState: LaunchViewState?) {
        when(viewState) {
            is LaunchViewState.NavigateToLogin -> {
                navController.navigate(LaunchFragmentDirections.actionLaunchFragmentToLoginFragment())
            }
            is LaunchViewState.NavigateToHome -> {
                navController.navigate(LaunchFragmentDirections.actionLaunchFragmentToHomeFragment())
            }
        }
    }
}