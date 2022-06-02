package com.uk.clearscore.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.uk.clearscore.activity.DetailActivity
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
class MainViewModel constructor(private val creditCall: CreditCall,
                                private val ui: DialogUtil, private val realm: Realm) : ViewModel() {

    private val TAG = MainViewModel::class.java.simpleName
    var reportMutableGenerated = MutableLiveData<Report>()

    @Suppress("UNCHECKED_CAST")
    class MainViewFactory(private val creditCall: CreditCall,
                          private val ui: DialogUtil, private val realm: Realm) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(creditCall, ui, realm) as T
        }

    }

    fun getCreditScore() {
        ui.showNonCloseableProgress()
        creditCall.getCreditScore(object : ApiCallHandler() {
            override fun done() {
                ui.dismissProgress()
            }

            override fun success(data: Any) {
                super.success(data)
                Log.d(TAG, "success: ${data as Report}")
                reportMutableGenerated.value = data
            }

            override fun failed(title: String, reason: String) {
                super.failed(title, reason)
                ui.showErrorDialog(title, reason)
            }
        })
    }

    fun getReport() : Report {
        return realm.where<Report>().findFirst()!!
    }

    fun goToDetails() {
        DetailActivity.start(ui.activity)
    }
}