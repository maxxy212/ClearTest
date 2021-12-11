package com.uk.clearscore.viewmodels

import androidx.lifecycle.ViewModel
import com.uk.clearscore.databinding.ActivityMainBinding
import com.uk.clearscore.model.Report
import com.uk.clearscore.network.ApiCallHandler
import com.uk.clearscore.network.api.CreditCall
import com.uk.clearscore.utility.DialogUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import io.realm.Realm
import io.realm.kotlin.where
import javax.inject.Inject

/**
 * Package com.uk.clearscore.viewmodels in

 * Project ClearScore

 * Created by Maxwell on 06/12/2021
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    repository: Repository) : ViewModel() {

    var report: Report? = repository.realm().where<Report>().findFirst()

    fun getCreditScore(ui: DialogUtil, creditCall: CreditCall, binding: ActivityMainBinding) {
        ui.showNonCloseableProgress()
        creditCall.getCreditScore(object : ApiCallHandler() {
            override fun done() {
                ui.dismissProgress()
            }

            override fun success(data: Any) {
                super.success(data)
                binding.report = Realm.getDefaultInstance().where<Report>().findFirst()!!
            }

            override fun failed(title: String, reason: String) {
                super.failed(title, reason)
                ui.showErrorDialog(title, reason)
            }

        })
    }
}

class Repository @Inject constructor() {
    fun realm() = Realm.getDefaultInstance()
}