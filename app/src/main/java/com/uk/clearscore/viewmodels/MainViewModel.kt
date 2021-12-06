package com.uk.clearscore.viewmodels

import androidx.lifecycle.ViewModel
import com.uk.clearscore.model.Report
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
}

class Repository @Inject constructor() {
    fun realm() = Realm.getDefaultInstance()
}