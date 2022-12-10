package com.nextia.socioinfonavit.ui.sigin

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.nextia.socioinfonavit.R
import com.nextia.socioinfonavit.core.extension.observe
import com.nextia.socioinfonavit.core.presentation.BaseFragment
import com.nextia.socioinfonavit.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment(R.layout.fragment_login) {

    private val viewModel by viewModels<LoginViewModel>()
    private lateinit var binding : FragmentLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.apply {
            observe(loginViewState, ::onViewStateChanged)
            observe(failure, this@LoginFragment::handleFailure)
        }
    }

    override fun setBinding(view: View) {
        binding.apply {
            lifecycleOwner = viewLifecycleOwner

            mBtnLogin.setOnClickListener {
                hideKeyboard()
                viewModel.onTappedLogin()
            }
            binding.etEmail.addTextChangedListener { viewModel.onKeysChanged() }
            binding.etPassword.addTextChangedListener { viewModel.onKeysChanged() }
            binding.etPassword.setOnEditorActionListener { _, actionId, _ ->
                val result: Int = actionId and EditorInfo.IME_MASK_ACTION
                if (result == EditorInfo.IME_ACTION_DONE){
                    hideKeyboard()
                    viewModel.onTappedLogin()
                }
                true
            }
            isEnabledDrawer(false)
            showToolbar(false)
        }
    }

    private fun onViewStateChanged(viewState: LoginViewState?) {
        when(viewState) {
            is LoginViewState.EnableLogIn -> enableLogIn(viewState.enable)
            is LoginViewState.EmailInvalid -> notify(getString(R.string.txt_email_invalid))
            is LoginViewState.PasswordEmpty -> notify(getString(R.string.txt_empty_password))
            //is LoginViewState.GoToHome -> navController.navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
            is LoginViewState.ShowProgress -> showLoader(viewState.show)
            else -> {}
        }
    }

    private fun enableLogIn(enable: Boolean) {
        binding.mBtnLogin.isEnabled = enable
    }
}