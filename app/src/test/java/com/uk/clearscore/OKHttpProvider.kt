package com.uk.clearscore

import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

/**
 * Package com.uk.clearscore in

 * Project ClearScore

 * Created by Maxwell on 02/06/2022
 */
object OkHttpProvider {

    // Timeout for the network requests
    private val REQUEST_TIMEOUT = 3L

    private var okHttpClient: OkHttpClient? = null

    fun getOkHttpClient(): OkHttpClient {
        return if (okHttpClient == null) {
            val okHttpClient = OkHttpClient.Builder()
                .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .build()
            this.okHttpClient = okHttpClient
            okHttpClient
        } else {
            okHttpClient!!
        }
    }
}