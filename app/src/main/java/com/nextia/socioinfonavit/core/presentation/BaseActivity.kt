package com.nextia.socioinfonavit.core.presentation

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity(){

    internal fun hideKeyBoard(){
        val view = this.findViewById<View>(android.R.id.content)
        if (view != null) {
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    abstract fun showProgress(show: Boolean)

}