package com.uk.clearscore.network.api

import androidx.test.espresso.IdlingRegistry
import com.androidnetworking.AndroidNetworking
import com.jakewharton.espresso.OkHttp3IdlingResource
import com.uk.clearscore.BuildConfig
import com.uk.clearscore.FileReader
import com.uk.clearscore.OkHttpProvider
import io.mockk.MockKAnnotations
import io.mockk.unmockkAll
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.util.concurrent.TimeUnit


/**
 * Package com.uk.clearscore.network.api in

 * Project ClearScore

 * Created by Maxwell on 02/06/2022
 */
class CreditCallTest {

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        mockWebServer = MockWebServer()
        mockWebServer.start()
        IdlingRegistry.getInstance().register(
            OkHttp3IdlingResource.create(
                "okhttp",
                OkHttpProvider.getOkHttpClient()
            ))
    }

    @Test
    fun testSuccessfulResponse() {
        mockWebServer.url(BuildConfig.BASE_URL + "endpoint.json")
        AndroidNetworking.get(mockWebServer.url(BuildConfig.BASE_URL + "auth/userLogin").toString())
            .build()
        mockWebServer.dispatcher = object : Dispatcher() {
            @Throws(InterruptedException::class)
            override fun dispatch(request: RecordedRequest): MockResponse {
                return MockResponse()
                    .setResponseCode(200)
                    .setBody(FileReader.readStringFromFile("success_response.json")!!)
            }
        }
    }

    @Test
    fun testFailedResponse() {
        mockWebServer.url(BuildConfig.BASE_URL + "endpoint.json")
        mockWebServer.dispatcher = object : Dispatcher() {
            @Throws(InterruptedException::class)
            override fun dispatch(request: RecordedRequest): MockResponse {
                return MockResponse().throttleBody(1024, 5, TimeUnit.SECONDS)
            }
        }
    }

    @After
    fun afterTest() {
        unmockkAll()
        mockWebServer.shutdown()
    }
}