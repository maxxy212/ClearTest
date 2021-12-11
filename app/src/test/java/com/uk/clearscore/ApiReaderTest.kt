package com.uk.clearscore

import android.content.Context
import android.text.TextUtils
import android.widget.TextView
import com.androidnetworking.error.ANError
import com.uk.clearscore.network.*
import io.mockk.*
import io.mockk.impl.annotations.MockK
import junit.framework.Assert.*
import okhttp3.*
import org.hamcrest.MatcherAssert.assertThat
import org.json.JSONException
import org.json.JSONObject
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
        mockkStatic(TextUtils::class)
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
        every { api.handleError(context, getAnError(), any(), any()) } returns Unit
//        api.handleError(context, getAnError(), callHandler, "TAG")
//        verify { api.handleError(context, getAnError(), callHandler, "TAG") }
    }

    @Test
    fun stringChecker_is_empty() {
        val stringCheck = spyk(StringChecker(), recordPrivateCalls = true)
        every { TextUtils.isEmpty(any()) } returns true
        assertTrue(stringCheck.isTextEmpty("test"))
    }

    @Test
    fun testObj() {
        mockkObject(ExampleObject)
        every { ExampleObject.add(4, 5) } returns 9
    }

    @Test
    fun testDynamic() {
        val mock = spyk(Car(), recordPrivateCalls = true)
        every { mock["accelerate"]() } returns "going not so fast"
        assertEquals("going not so fast", mock.drive())

        verifySequence {
            mock.drive()
            mock["accelerate"]()
        }
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
                MediaType.parse("application/json; charset=utf-8"),
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
                MediaType.parse("application/json; charset=utf-8"),
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
                MediaType.parse("application/json; charset=utf-8"),
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
                MediaType.parse("application/json; charset=utf-8"),
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
                MediaType.parse("application/json; charset=utf-8"),
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
                MediaType.parse("application/json; charset=utf-8"),
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

    //    @Test
//    fun apiIsSuccess() {
//        //given
//        //apiReader = mockk()
//        //apiReader = mockk()
//        //mockkClass(ApiReader::class)
//        mockkConstructor(ApiReader::class, recordPrivateCalls = true)
//        every { anyConstructed<ApiReader>() getProperty "code" } propertyType Int::class returns 200
//        val result = ApiReader(response, jsonObject)
//        assertTrue(result.isSuccess)
//    }

//    @Test
//    fun testPrivate() {
//        val mock = spyk(ApiReader(response, jsonObject), recordPrivateCalls = true)
////        every { mock getProperty "code" } returns 200
////        verify { mock getProperty "code"}
//        every { mock setProperty "code" value 200 } just runs
//        verify { mock setProperty "code" value 200 }
//    }
}