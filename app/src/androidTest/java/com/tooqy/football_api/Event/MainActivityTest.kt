package com.tooqy.football_api.Event

import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.pressBack
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.tooqy.football_api.MainActivity
import com.tooqy.football_api.R.id.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @Rule
    @JvmField
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testRecyclerViewBehaviour() {
        sleep_for_awhile()
        Espresso.onView(ViewMatchers.withId(main_list_view_last))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        sleep_for_awhile()

        Espresso.onView(ViewMatchers.withId(main_list_view_last)).perform(
                RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(3))
        Espresso.onView(ViewMatchers.withId(main_list_view_last)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(3, click()))

        sleep_for_awhile(3000)

        pressBack()
    }

    @Test
    fun testAppBehaviour() {
        // Click Prev. Match
        Espresso.onView(ViewMatchers.withId(nav_last_match))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(nav_last_match)).perform(click())

        sleep_for_awhile()

        // Cek Tab Next Match
        Espresso.onView(ViewMatchers.withId(nav_next_match))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(nav_next_match)).perform(click())

        sleep_for_awhile()

        // Cek Tab Favorite
        Espresso.onView(ViewMatchers.withId(nav_favorite))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(nav_favorite)).perform(click())

        sleep_for_awhile()

        pressBack()

        sleep_for_awhile()

    }


    @Test
    fun testAppFavorite() {
        // Cek Tab Next Match
        Espresso.onView(ViewMatchers.withId(nav_last_match))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(nav_last_match)).perform(click())

        sleep_for_awhile(1000)

        // Display List Last Match
        Espresso.onView(ViewMatchers.withId(main_list_view_last)).perform(
                RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(4))
        Espresso.onView(ViewMatchers.withId(main_list_view_last)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(4, click()))

        sleep_for_awhile()

        // Click Favorite Star
        Espresso.onView(ViewMatchers.withId(add_to_favorite))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(add_to_favorite)).perform(ViewActions.click())

        sleep_for_awhile()
        // Back to main page
        Espresso.pressBack()
        sleep_for_awhile()

        // Cek / Display Tab Favorite
        Espresso.onView(ViewMatchers.withId(nav_favorite))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(nav_favorite)).perform(click())

        sleep_for_awhile()

        pressBack()

    }

    private fun sleep_for_awhile(timetosleep: Long = 2000) {
        try {
            Thread.sleep(timetosleep)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}