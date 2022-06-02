package com.uk.clearscore.network

import android.content.Context
import android.util.Log
import com.androidnetworking.error.ANError
import com.uk.clearscore.BuildConfig
import io.mockk.*
import io.mockk.impl.annotations.MockK
import junit.framework.Assert.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.json.JSONException
import org.json.JSONObject
import org.junit.After
import org.junit.Before
import org.junit.Test


/**
 * Package com.uk.clearscore in

 * Project ClearScore

 * Created by Maxwell on 10/12/2021
 */
class ApiReaderTest {

    @MockK
    lateinit var response: Response
    @MockK
    lateinit var jsonObject: JSONObject
    @MockK
    lateinit var context: Context
    @MockK
    lateinit var callHandler: ApiCallHandler
    @MockK
    lateinit var error: ANError

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        every { jsonObject.optString("message") } returns "Dummy"
        mockkConstructor(ApiReader::class)
    }

    @Test
    fun testNetworkErrorMsg() {
        val mock = spyk(ApiReader(getAnError()), recordPrivateCalls = true)
        every { context.getString(any()) } returns "oops test error"
        val result = mock.getNetworkErrorMsg(context)
        assertTrue(result.isNotEmpty())
    }

    @Test
    fun apiIsFailed() {
        val mock = spyk(ApiReader(getFailedResponse(), jsonObject), recordPrivateCalls = true)
        assertTrue(mock.isFailed)
    }

    @Test
    fun apiIsSuccess() {
        val reader = spyk(ApiReader(get200Response(), jsonObject))
        assertTrue(reader.isSuccess)
    }

    @Test
    fun apiIsBadRequest() {
        val api = spyk(ApiReader(getBadRequest(), jsonObject))
        assertTrue(api.isBadRequest)
    }

    @Test
    fun apiIsAuthorizationError() {
        val api = spyk(ApiReader(getAuthorizationError(), jsonObject))
        assertTrue(api.isAuthorizationError)
    }

    @Test
    fun apiIsSytemError() {
        val api = spyk(ApiReader(getIsSystemError(), jsonObject))
        assertTrue(api.isSystemError)
    }

    @Test
    fun testGetJsonFromJSONObject() {
        val api = spyk(ApiReader(get200Response(), jsonObject))
        every { api.data.getJSONObject(any()) } returns getJsonData()
        val result: JSONObject = api.getJsonObjectFromData("dummy")
        assertNotNull(result)
    }

    @Test
    fun testHandleErrorCall() {
        val api = spyk(ApiReader(getAnError()))
        justRun { api.handleError(context, any(), any())  }
        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0
        api.handleError(context, callHandler, "TAG")
        verify { api.handleError(context, callHandler, "TAG") }
        confirmVerified(api)
    }

    @After
    fun afterTest() {
        unmockkAll()
    }

    fun get200Response() : Response {
        val mockRequest = Request.Builder()
            .url(BuildConfig.BASE_URL + "endpoint.json")
            .build()
        val response = Response.Builder()
            .request(mockRequest)
            .protocol(Protocol.HTTP_2)
            .code(200)
            .message("")
            .body(ResponseBody.create(
                "application/json; charset=utf-8".toMediaTypeOrNull(),
                "{}"
            ))
            .build()

        return response
    }

    fun getFailedResponse() : Response {
        val mockRequest = Request.Builder()
            .url(BuildConfig.BASE_URL + "endpoint.json")
            .build()
        val response = Response.Builder()
            .request(mockRequest)
            .protocol(Protocol.HTTP_2)
            .code(400)
            .message("")
            .body(ResponseBody.create(
                "application/json; charset=utf-8".toMediaTypeOrNull(),
                "{}"
            ))
            .build()

        return response
    }

    fun getAnError() : ANError {
        val mockRequest = Request.Builder()
            .url(BuildConfig.BASE_URL + "endpoint.json")
            .build()
        val response = Response.Builder()
            .request(mockRequest)
            .protocol(Protocol.HTTP_2)
            .code(500)
            .message("Error")
            .body(ResponseBody.create(
                "application/json; charset=utf-8".toMediaTypeOrNull(),
                "{}"
            ))
            .build()

        val anError = ANError(response)
        anError.errorDetail = "connectionError"
        anError.errorCode = 500
        anError.errorBody = "[]"
       return anError
    }

    fun getBadRequest() : Response {
        val mockRequest = Request.Builder()
            .url(BuildConfig.BASE_URL + "endpoint.json")
            .build()
        val response = Response.Builder()
            .request(mockRequest)
            .protocol(Protocol.HTTP_2)
            .code(404)
            .message("")
            .body(ResponseBody.create(
                "application/json; charset=utf-8".toMediaTypeOrNull(),
                "{}"
            ))
            .build()

        return response
    }

    fun getAuthorizationError() : Response {
        val mockRequest = Request.Builder()
            .url(BuildConfig.BASE_URL + "endpoint.json")
            .build()
        val response = Response.Builder()
            .request(mockRequest)
            .protocol(Protocol.HTTP_2)
            .code(403)
            .message("")
            .body(ResponseBody.create(
                "application/json; charset=utf-8".toMediaTypeOrNull(),
                "{}"
            ))
            .build()

        return response
    }

    fun getIsSystemError() : Response {
        val mockRequest = Request.Builder()
            .url(BuildConfig.BASE_URL + "endpoint.json")
            .build()
        val response = Response.Builder()
            .request(mockRequest)
            .protocol(Protocol.HTTP_2)
            .code(500)
            .message("")
            .body(ResponseBody.create(
                "application/json; charset=utf-8".toMediaTypeOrNull(),
                "{}"
            ))
            .build()

        return response
    }

    fun getJsonData() : JSONObject {
        val obj = JSONObject()
        val nestedObj = JSONObject()
        try {
            nestedObj.put("dummy", "answer")
            obj.put("id", "1")
            obj.put("data", nestedObj)
        }catch (e : JSONException) { e.printStackTrace() }

        return obj
    }

}