package com.uk.clearscore.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Package com.uk.clearscore.model in

 * Project ClearScore

 * Created by Maxwell on 06/12/2021
 */
open class Report() : RealmObject() {
    @PrimaryKey var clientRef: String? = null
    var score: Int = 0
    var status: String? = null
    var maxScoreValue: Int = 0
    var minScoreValue: Int = 0
    var percentageCreditUsed: Int = 0
    var currentShortTermDebt: Double = 0.0
    var currentShortTermCreditLimit: Double = 0.0
    var daysUntilNextReport: Int = 0
}