package com.nextia.socioinfonavit.ui.customdialogs

import android.app.Activity
import android.app.Dialog
import android.graphics.Point
import android.view.Display
import android.view.View
import android.view.Window
import androidx.databinding.DataBindingUtil
import com.nextia.socioinfonavit.R
import com.nextia.socioinfonavit.databinding.AlertDialogTwoOptionsBinding
import com.nextia.socioinfonavit.databinding.CustomAlertDialogBinding

class CustomAlertDialog {

    companion object {

        var dialogOptions: DialogOptions? = null
        var dialogOption: DialogOption? = null
        var dialogOptionsVerificationCode: DialogOptionsVerificationCode? = null
        var dialogOptionsEmailCode: DialogOptionsEmailCode? = null
        var dialogOptionsDeleteMessage: DialogOptionsDeleteMessage? = null
        var dialog: Dialog? = null
        var display: Display? = null
        var size: Point? = null


        fun createDialog(
            activity: Activity,
            title: String,
            message: String,
            option: String,
            positiveClicked:  (() -> Unit)? = null,

            ) {

            val mDialogView: CustomAlertDialogBinding = DataBindingUtil.inflate(
                activity.layoutInflater,
                R.layout.custom_alert_dialog,
                null,
                false
            )


            mDialogView.tvMessage.text = message
            mDialogView.tvTitle.text = title

            if (option.isNotEmpty()) {
                mDialogView.btAction.text = option
            }

            if (title.isNotEmpty())
                mDialogView.tvTitle.visibility = View.VISIBLE

            mDialogView.btAction.setOnClickListener {
                dialog?.dismiss()
                positiveClicked?.invoke()
            }

            dialog = Dialog(activity, R.style.CustomDialog)
            dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog?.setContentView(mDialogView.root)
            dialog?.setCancelable(true)
            display = activity.windowManager.defaultDisplay
            size = Point()
            display?.getSize(size)
            if (!activity.isFinishing) dialog?.show()

        }

        fun createDialogTwoOptions(
            activity: Activity,
            message: String,
            positiveOption:  String,
            negativeOption:  String,
            positiveClicked:  (() -> Unit)? = null,
            negativeClicked:  (() -> Unit)? = null
        ) {

            this.dialogOptions = dialogOptions

            val mDialogView: AlertDialogTwoOptionsBinding = DataBindingUtil.inflate(
                activity.layoutInflater,
                R.layout.alert_dialog_two_options,
                null,
                false
            )

            mDialogView.tvMessage.text = message
            mDialogView.btNegative.text = negativeOption
            mDialogView.btPositive.text = positiveOption

            mDialogView.btNegative.setOnClickListener {
                dialog?.dismiss()
                negativeClicked?.invoke()
            }

            mDialogView.btPositive.setOnClickListener {
                dialog?.dismiss()
                positiveClicked?.invoke()
            }

            dialog = Dialog(activity, R.style.CustomDialog)
            dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog?.setContentView(mDialogView.root)
            dialog?.setCancelable(false)
            display = activity.windowManager.defaultDisplay
            size = Point()
            display?.getSize(size)
            if (!activity.isFinishing) dialog?.show()

        }
    }

    interface DialogOptions {
        fun positiveOption(id: CustomAlertDialog.DialogTags)
        fun negativeOption(id: CustomAlertDialog.DialogTags)
    }

    interface DialogOptionsVerificationCode {
        fun verifyCode(code: String)
    }

    interface DialogOptionsEmailCode{
        fun verifyCode(email: String, code: String)
    }

    interface DialogOptionsDeleteMessage{
        fun cancel()
        fun deleteForMe()
        fun deleteForAll()
    }


    interface DialogOption{
        fun actionOption(id : CustomAlertDialog.DialogTags)
    }


    enum class DialogTags{
        LOGOUT
    }

}