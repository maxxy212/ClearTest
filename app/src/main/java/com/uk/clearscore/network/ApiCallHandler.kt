package com.uk.clearscore.network

/**
 * Package com.uk.clearscore.network in

 * Project ClearScore

 * Created by Maxwell on 06/12/2021
 */
abstract class ApiCallHandler {
    protected abstract fun done()

    open fun success(data: Any) { done() }

    open fun failed(title: String, reason: String) { done() }

    fun logout() { done() }
}