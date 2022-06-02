package com.uk.clearscore.network.api

import android.content.Context
import android.util.Log
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.OkHttpResponseAndJSONObjectRequestListener
import com.uk.clearscore.R
import com.uk.clearscore.model.Report
import com.uk.clearscore.network.ApiCallHandler
import com.uk.clearscore.network.ApiReader
import com.uk.clearscore.network.Networking.getData
import dagger.hilt.android.qualifiers.ApplicationContext
import io.realm.Realm
import okhttp3.Response
import org.json.JSONObject
import javax.inject.Inject

/**
 * Package com.uk.clearscore.network.api in

 * Project ClearScore

 * Created by Maxwell on 06/12/2021
 */
class CreditCall @Inject constructor(@param:ApplicationContext private val context: Context) {

    private val TAG = CreditCall::class.java.simpleName
    private val GET_CREDIT = "endpoint.json"

    fun getCreditScore(callHandler: ApiCallHandler) {
        getData(GET_CREDIT, object : OkHttpResponseAndJSONObjectRequestListener{
            override fun onResponse(okHttpResponse: Response, response: JSONObject) {
                val apiReader = ApiReader(okHttpResponse, response)
                Log.d(TAG, "onResponse: ${apiReader.data}")
                if (apiReader.isSuccess) {
                    try {
                        Realm.getDefaultInstance().use { realm ->
                            realm.executeTransaction {
                                val report = it.createOrUpdateObjectFromJson(Report::class.java, apiReader.getJsonObjectFromData("creditReportInfo"))
                                callHandler.success(report)
                            }
                        }
                    } catch (e: Exception) {
                        callHandler.failed(context.getString(R.string.data_base_err), e.message ?: "Something went wrong")
                    }
                }else {
                    callHandler.failed(context.getString(R.string.server_error), context.getString(R.string.error_needs_structure))
                }
            }

            override fun onError(anError: ANError) {
                val apiReader = ApiReader(anError)
                apiReader.handleError(context, callHandler, TAG)
            }

        })
    }
}