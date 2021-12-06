package com.uk.clearscore.activity

import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.Toolbar
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.uk.clearscore.R
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.containsString
import org.hamcrest.core.IsInstanceOf.instanceOf
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Package com.uk.clearscore.activity in

 * Project ClearScore

 * Created by Maxwell on 06/12/2021
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class DetailActivityTest {

    @get:Rule
    var rule: ActivityScenarioRule<DetailActivity> = ActivityScenarioRule(DetailActivity::class.java)

    @Test
    fun testBackClick() {
        onView(allOf(instanceOf(AppCompatImageButton::class.java), withParent(withId(R.id.toolbar)))).perform(click())
        rule.scenario
            .onActivity { activity: DetailActivity -> Assert.assertTrue(activity.isFinishing) }
    }

    @Test
    fun verifyViewBindings() {
        onView(withId(R.id.toolbar))
            .check(matches(instanceOf(Toolbar::class.java)))

        onView(withId(R.id.score))
            .check(matches(instanceOf(TextView::class.java)))
            .check(matches(withText(containsString("Score"))))

        onView(withId(R.id.status))
            .check(matches(instanceOf(TextView::class.java)))
            .check(matches(withText(containsString("Status"))))

        onView(withId(R.id.max_score))
            .check(matches(instanceOf(TextView::class.java)))
            .check(matches(withText(containsString("MaxScoreValue"))))

        onView(withId(R.id.min_score))
            .check(matches(instanceOf(TextView::class.java)))
            .check(matches(withText(containsString("MinScoreValue"))))

        onView(withId(R.id.percentage))
            .check(matches(instanceOf(TextView::class.java)))
            .check(matches(withText(containsString("PercentageCreditUsed"))))

        onView(withId(R.id.current_short))
            .check(matches(instanceOf(TextView::class.java)))
            .check(matches(withText(containsString("CurrentShortTermDebt"))))

        onView(withId(R.id.current_limit))
            .check(matches(instanceOf(TextView::class.java)))
            .check(matches(withText(containsString("CurrentShortTermCreditLimit"))))

        onView(withId(R.id.next_report))
            .check(matches(instanceOf(TextView::class.java)))
            .check(matches(withText(containsString("DaysUntilNextReport"))))

    }
}