package com.nextia.socioinfonavit.ui

import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED
import androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_UNLOCKED
import com.kaopiz.kprogresshud.KProgressHUD
import com.nextia.socioinfonavit.GraphMainDirections
import com.nextia.socioinfonavit.R
import com.nextia.socioinfonavit.core.presentation.BaseActivity
import com.nextia.socioinfonavit.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity(R.layout.activity_main) {
    private var progressHud : KProgressHUD? = null
    private var actionBarDrawerToggle: ActionBarDrawerToggle? = null
    private var mListener : ((MenuTags) -> Unit)? = null
    private lateinit var binding: ActivityMainBinding

    override fun setBinding(@LayoutRes layoutId: Int) {
        binding = DataBindingUtil.setContentView(this, layoutId)
        binding.apply {
            lifecycleOwner = this@MainActivity

            drawerLayout.closeDrawer(mainNavViewStart)
            actionBarDrawerToggle =
                object : ActionBarDrawerToggle(this@MainActivity,
                    drawerLayout, R.string.drawer_opened, R.string.drawer_closed) {
                    override fun onDrawerOpened(drawerView: View) {
                        super.onDrawerOpened(drawerView)
                        invalidateOptionsMenu()
                    }
                    override fun onDrawerClosed(drawerView: View) {
                        super.onDrawerClosed(drawerView)
                        invalidateOptionsMenu()
                    }
                }
            drawerLayout.addDrawerListener(actionBarDrawerToggle as ActionBarDrawerToggle)
            mainAppBar.baseToolbar.setNavigationOnClickListener {
                drawerLayout.openDrawer(mainNavViewStart)
            }
            includeMenu.tvLogOut.setOnClickListener { mListener?.invoke(MenuTags.ITEM_LOGOUT) }
            includeMenu.llContainerMyBenefit.setOnClickListener {
                baseNavController.navigate(GraphMainDirections.actionGlobalMyBenefitsFragment())
                mListener?.invoke(MenuTags.ITEM_MY_BENEFITS)
            }
        }
    }

    fun setItemMenuTapped(listener: ((MenuTags) -> Unit)?) {
        mListener = listener
    }

    override fun showProgress(show: Boolean) {
        if (!show)
            progressHud?.dismiss()
        else {
            progressHud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .setBackgroundColor(getColor(R.color.red))
            progressHud?.show()
        }
    }

    override fun openDrawer() {
        binding.drawerLayout.openDrawer(binding.mainNavViewStart)
    }

    override fun showDrawer(show: Boolean) {
        if (show)
            actionBarDrawerToggle?.onDrawerOpened(binding.mainNavViewStart)
        else actionBarDrawerToggle?.onDrawerClosed(binding.mainNavViewStart)

    }

    override fun enableAppBar(enable: Boolean) {
        binding.mainAppBar.baseAppbar.isVisible = enable
    }

    fun isEnabledDrawer(enable: Boolean) {
        if (enable)
            binding.drawerLayout.setDrawerLockMode(LOCK_MODE_UNLOCKED)
        else binding.drawerLayout.setDrawerLockMode(LOCK_MODE_LOCKED_CLOSED)
    }

    override fun setToolbarContent(content: ToolbarContent) {
        binding.mainAppBar.baseToolbar.apply {
            removeAllViews()
            val mToolbarHome = DataBindingUtil.inflate<ViewDataBinding>(
                layoutInflater,
                content.contentToolbar,
                null,
                false
            )
            addView(
                mToolbarHome.root
            )

            navigationIcon = content.navigationIcon?.let {
                setNavigationOnClickListener {
                    content.onClickNavigationIcon()
                }
                AppCompatResources.getDrawable(context, it)
            }

            content.menu?.let {
                menu.clear()
                inflateMenu(it)

                menu.getItem(0).actionView?.setOnClickListener {
                    content.onMenuItemClicked()
                } ?: menu.getItem(0).setOnMenuItemClickListener {
                    content.onMenuItemClicked()
                    true
                }

            } ?: menu.clear()
        }
    }


    enum class MenuTags {
        ITEM_LOGOUT,
        ITEM_MY_BENEFITS
    }

}