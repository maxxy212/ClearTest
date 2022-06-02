package com.uk.clearscore.utility

import android.app.Activity
import android.content.Context
import android.text.TextUtils
import com.uk.clearscore.utility.DialogUtil
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 * Package com.uk.clearscore in

 * Project ClearScore

 * Created by Maxwell on 10/12/2021
 */
class DialogUtilTest {

    @MockK
    lateinit var activity: Activity

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @Test
    fun testDismissDialogReturn() {
        val presenter = spyk(DialogUtil(activity))
        every { presenter.dismissProgress() } returns Unit
    }

    @Test
    fun testshowErrorDialogReturn() {
        val presenter = spyk(DialogUtil(activity))
        every { presenter.showErrorDialog("", "") } returns Unit
    }

    @Test
    fun testshowNonCloseableDialogReturn() {
        val presenter = spyk(DialogUtil(activity))
        every { presenter.showNonCloseableProgress("") } returns Unit
    }

    @After
    fun afterTest() {
        unmockkAll()
    }
}