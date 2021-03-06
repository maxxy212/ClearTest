package com.uk.clearscore.network

import android.util.Log
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.interfaces.OkHttpResponseAndJSONArrayRequestListener
import com.androidnetworking.interfaces.OkHttpResponseAndJSONObjectRequestListener
import com.androidnetworking.interfaces.OkHttpResponseAndStringRequestListener
import com.uk.clearscore.BuildConfig
import org.json.JSONObject

/**
 * Package com.uk.clearscore.network in

 * Project ClearScore

 * Created by Maxwell on 06/12/2021
 */
object Networking {
    private val TAG = Networking::class.java.simpleName
    private const val API_ENDPOINT = BuildConfig.BASE_URL

    @JvmStatic
    fun getData(endpoint: String,
                listener: OkHttpResponseAndJSONObjectRequestListener) {
        Log.d(TAG, String.format("Getting data from", API_ENDPOINT + endpoint))
        AndroidNetworking.get(API_ENDPOINT + endpoint)
            .setTag(endpoint)
            .setPriority(Priority.MEDIUM)
            .addHeaders("Content-Type", "application/json")
            .build()
            .getAsOkHttpResponseAndJSONObject(listener)
    }

    @JvmStatic
    fun postData(endpoint: String,
                 jsonObject: JSONObject,
                 listener: OkHttpResponseAndJSONObjectRequestListener){
        Log.d(TAG, "postData: Gammeeee" + API_ENDPOINT)
        Log.d(TAG, API_ENDPOINT + endpoint)
        AndroidNetworking.post(API_ENDPOINT + endpoint)
            .addJSONObjectBody(jsonObject)
            .setTag(endpoint)
            .setPriority(Priority.MEDIUM)
            .addHeaders("Content-Type", "application/json")
            .build()
            .getAsOkHttpResponseAndJSONObject(listener)
    }

}