package com.uk.clearscore.activity

import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.uk.clearscore.BuildConfig
import com.uk.clearscore.R
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.Matchers.instanceOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


/**
 * Package com.uk.clearscore.activity in

 * Project ClearScore

 * Created by Maxwell on 06/12/2021
 */

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {

    private lateinit var mockWebServer: MockWebServer
    @get:Rule
    var rule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    @Before
    @Throws(IOException::class, InterruptedException::class)
    fun onSetup() {
        Intents.init()
        mockWebServer = MockWebServer()
        mockWebServer.start()
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        Intents.release()
        mockWebServer.shutdown()
    }

    @Test
    fun verifyDonutClickToDetailActvity() {
        // Clicks on the doghnut root view to go to detail activity
        onView(withId(R.id.view_wrapper)).perform(click())

        //Verifies that the intended DetailActivity is launched
        intended(hasComponent(DetailActivityTest::class.java.getName()))
    }

    @Test
    fun verifyViewBindings() {
        onView(withId(R.id.view_wrapper))
            .check(matches(instanceOf(RelativeLayout::class.java)))

        onView(withId(R.id.circularProgressbar))
            .check(matches(instanceOf(ProgressBar::class.java)))

        onView(withText(R.string.your_score))
            .check(matches(instanceOf(TextView::class.java)))

        onView(withId(R.id.score))
            .check(matches(instanceOf(TextView::class.java)))

        onView(withId(R.id.max_score))
            .check(matches(instanceOf(TextView::class.java)))

        onView(withId(R.id.inner_circle))
            .check(matches(instanceOf(LinearLayout::class.java)))
    }

    @Test
    fun callCreditAPI() {
        mockWebServer.url(BuildConfig.BASE_URL + "endpoint.json")
        mockWebServer.enqueue(MockResponse().setBody("[]").setResponseCode(200))
    }

}