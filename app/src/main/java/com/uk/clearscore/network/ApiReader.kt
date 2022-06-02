package com.uk.clearscore.network

import android.content.Context
import android.text.TextUtils
import android.util.Log
import androidx.annotation.VisibleForTesting
import com.androidnetworking.error.ANError
import com.uk.clearscore.R
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

/**
 * Package com.uk.clearscore.network in

 * Project ClearScore

 * Created by Maxwell on 06/12/2021
 */
class ApiReader {
    private val connect_error = "connectionError"
    private val parse_error = "parseError"
    private val request_error = "requestCancelledError"

    lateinit var data: JSONObject
    private lateinit var anError: ANError
    @VisibleForTesting
    private val code: Int

    constructor(okHttpResponse: Response, js: JSONObject) {
        data = js
        this.code = okHttpResponse.code()
    }

    constructor(anError: ANError) {
        this.anError = anError
        this.code = anError.errorCode
    }

    fun getNetworkErrorMsg(context: Context): String {
        return when (anError.errorDetail) {
            connect_error -> context.getString(R.string.conn_error)
            parse_error -> context.getString(R.string.parse_error)
            request_error -> context.getString(R.string.req_error)
            else -> anError.errorDetail
        }
    }

    val isSuccess: Boolean
        get() {
        val OK_RESPONSE = 200
        val CREATED_RESPONSE = 201
        val DUPLICATE_RESPONSE = 202
        return code == OK_RESPONSE || code == CREATED_RESPONSE || code == DUPLICATE_RESPONSE
        }

    val isFailed: Boolean
        get() {
            val VALIDATION_ERROR = 400
            return code == VALIDATION_ERROR
        }

    val isBadRequest: Boolean
        get() {
            val BAD_REQUEST = 404
            return code == BAD_REQUEST
        }

    val isAuthorizationError: Boolean
        get() {
            val UNAUTHORIZED_ERROR = 401
            val FORBIDDEN_ERROR = 403
            return code == UNAUTHORIZED_ERROR || code == FORBIDDEN_ERROR
        }

    val isSystemError: Boolean
        get() {
            val NOT_FOUND_ERROR = 500
            val SYSTEM_ERROR = 501
            return code == SYSTEM_ERROR || code == NOT_FOUND_ERROR
        }

    fun getJsonObjectFromData(key: String): JSONObject {
        var obj = JSONObject()
        try {
            obj = data.getJSONObject(key)
        } catch (jsEx: JSONException) {
            jsEx.printStackTrace()
        }
        return obj
    }

    fun handleError(context: Context, callHandler: ApiCallHandler, TAG: String?) {
        anError.printStackTrace()
        Log.d(TAG, "onError: " + anError.errorCode)
        if (isAuthorizationError) {
            callHandler.logout()
        }
        else if (isSystemError) {
            callHandler.failed(anError.response.message(), "Something went wrong on our server")
        }
        else {
            val error = getNetworkErrorMsg(context)
            callHandler.failed(if (anError.errorDetail == parse_error) "Cannot Apply" else "Network Error", error)
        }
    }

    override fun toString(): String {
        return "ApiReader{" +
                ", jsonObject=" + data.toString() +
                ", code=" + code +
                '}'
    }
}