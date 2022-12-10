package com.nextia.socioinfonavit.core.presentation

import android.content.Intent
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.nextia.socioinfonavit.R
import com.nextia.socioinfonavit.core.exception.Failure
import com.nextia.socioinfonavit.core.utils.CustomAlertDialog
import com.nextia.socioinfonavit.core.utils.OnFailure
import com.nextia.socioinfonavit.ui.MainActivity

abstract class BaseFragment : Fragment(), OnFailure {
    val navController by lazy { findNavController() }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun handleFailure(failure: Failure?) {
        when (failure) {
            is Failure.NetworkConnection -> showDialogError(getString(R.string.error_connection))
            is Failure.ServerError -> notify("Server error")
            is Failure.Unauthorized -> showDialogUnauthorized()
        }
    }

    private fun showDialogError(message: String){
        CustomAlertDialog.createDialog(
            requireActivity(),
            "",
            message,
            getString(R.string.option_accept),
            positiveClicked = {}
        )
    }

    private fun showDialogUnauthorized(){
        CustomAlertDialog.createDialog(
            requireActivity(),
            getString(R.string.title_error_logout),
            getString(R.string.message_error_logout),
            getString(R.string.option_accept),
            positiveClicked = {}
        )
    }

    internal fun notify(@StringRes message: Int){
        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT)
            .apply { setBackgroundTint(context.getColor(
                R.color.red)) }.show()
    }

    internal fun notify(message: String){
        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT)
            .apply { setBackgroundTint(context.getColor(
                R.color.red)) }.show()
    }

    internal fun hideKeyboard() {
        (activity as? BaseActivity)?.hideKeyBoard()
    }


    open fun showLoader(show: Boolean) {
        (activity as? MainActivity)?.showProgress(show)
    }
}