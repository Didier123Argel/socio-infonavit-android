package com.nextia.socioinfonavit.core.presentation

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.util.AttributeSet
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity(@LayoutRes val layoutId: Int) : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBinding(layoutId)
    }

    internal fun hideKeyBoard(){
        val view = this.findViewById<View>(android.R.id.content)
        if (view != null) {
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
    abstract fun setBinding(@LayoutRes layoutId: Int)
    abstract fun showProgress(show: Boolean)
    abstract fun enableAppBar(enable: Boolean)
    abstract fun showDrawer(show: Boolean)

}