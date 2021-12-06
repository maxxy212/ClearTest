package com.uk.clearscore.utility

import android.app.Activity
import com.gdacciaro.iOSDialog.iOSDialogBuilder
import com.kaopiz.kprogresshud.KProgressHUD
import com.uk.clearscore.R
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

/**
 * Package com.uk.clearscore.utility in

 * Project ClearScore

 * Created by Maxwell on 06/12/2021
 */
@ActivityScoped
class DialogUtil @Inject constructor(private val activity: Activity) {

    private var hud: KProgressHUD? = null

    fun showErrorDialog(title: String?, message: String?) {
        if (!activity.isFinishing())
            iOSDialogBuilder(activity)
                .setTitle(title)
                .setSubtitle(message)
                .setBoldPositiveLabel(false)
                .setCancelable(false)
                .setPositiveListener(activity.getString(R.string.ok)) { dialog ->
                    dialog.dismiss()
                }
                .build()
                .show()
    }

    fun showNonCloseableProgress(title: String? = null) {
        if (!activity.isFinishing()) {
            if (title == null || title.isEmpty()){
                hud = KProgressHUD.create(activity)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel(activity.getString(R.string.load))
                    .setCancellable(false)
                    .setAnimationSpeed(2)
                    .setDimAmount(0.5f)
                    .show()
            } else {
                hud = KProgressHUD.create(activity)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setDetailsLabel(title)
                    .setCancellable(false)
                    .setAnimationSpeed(2)
                    .setDimAmount(0.5f)
                    .show()
            }
        }
    }

    fun dismissProgress() {
        hud?.dismiss()
        hud = null
    }
}