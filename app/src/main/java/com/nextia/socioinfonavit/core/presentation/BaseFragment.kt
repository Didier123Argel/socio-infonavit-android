package com.nextia.socioinfonavit.core.presentation

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.nextia.socioinfonavit.R
import com.nextia.socioinfonavit.core.exception.Failure
import com.nextia.socioinfonavit.core.utils.OnFailure
import com.nextia.socioinfonavit.ui.MainActivity
import com.nextia.socioinfonavit.ui.customdialogs.CustomAlertDialog

typealias ManuTapListener = ((MainActivity.MenuTags) -> Unit)?
abstract class BaseFragment(@LayoutRes val layoutId: Int) : Fragment(layoutId), OnFailure {
    val navController by lazy { findNavController() }
    val baseActivity by lazy { requireActivity() as BaseActivity }

    open fun useAppBar() = false
    open fun useDrawer() = false
    open fun setMenuTapListener(): ManuTapListener = {}
    open fun setToolbar() = BaseActivity.ToolbarContent()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        baseActivity.apply {
            if(useAppBar()) { setToolbarContent(setToolbar()) }
            enableAppBar(useAppBar())
            isEnabledDrawer(useDrawer())
            setItemMenuListener()
        }
        setBinding(view)
    }

    abstract fun setBinding(view: View)

    override fun handleFailure(failure: Failure?) {
        when (failure) {
            is Failure.NetworkConnection -> showDialogError(getString(R.string.error_connection))
            is Failure.ServerError -> notify("Server error")
            is Failure.Unauthorized -> showDialogUnauthorized()
            else -> {}
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

    private fun isEnabledDrawer(enable: Boolean) {
        (baseActivity as? MainActivity)?.isEnabledDrawer(enable)
    }

    private fun setItemMenuListener() {
        (baseActivity as? MainActivity)?.setItemMenuTapped(setMenuTapListener())
    }

    fun setStatusBarColor(color: Int) {
        baseActivity.window.statusBarColor = resources.getColor(color, null)
    }
}