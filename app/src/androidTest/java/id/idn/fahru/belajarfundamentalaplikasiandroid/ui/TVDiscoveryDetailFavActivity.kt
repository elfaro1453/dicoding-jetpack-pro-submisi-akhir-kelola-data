package id.idn.fahru.belajarfundamentalaplikasiandroid.ui

import NestedScrollViewExtension
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import androidx.test.rule.ActivityTestRule
import id.idn.fahru.belajarfundamentalaplikasiandroid.MainActivity
import id.idn.fahru.belajarfundamentalaplikasiandroid.R
import id.idn.fahru.belajarfundamentalaplikasiandroid.utils.DataBindingIdlingResource
import id.idn.fahru.belajarfundamentalaplikasiandroid.utils.HelperUtils
import org.hamcrest.core.AllOf
import org.hamcrest.core.IsNot
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.random.Random

@MediumTest
@RunWith(AndroidJUnit4::class)
class TVDiscoveryDetailFavActivity {
    /*
    * Test Main Activity TV Fragment then Detail TV Activity
    * Bookmark a TV Show then pressback
    * Go to Favourite Activity Tv Fragment then see if the tv show has been bookmarked
    * */
    @get:Rule
    var activityTestRule = ActivityTestRule(MainActivity::class.java)

    private val idlingResourceDataBinding = DataBindingIdlingResource(activityTestRule)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(idlingResourceDataBinding)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(idlingResourceDataBinding)
    }

    @Test
    fun tvVDiscoveryDetailFavActivity() {
        var tvTitle: String
        val lastIndex = 19
        val randomNumber = Random.nextInt(0, lastIndex)

        Espresso.onView(withText(R.string.label_tv)).perform(ViewActions.click())
        Espresso.onView(
            AllOf.allOf(
                withId(R.id.swipeContainer),
                isCompletelyDisplayed()
            )
        )
        Espresso.onView(withId(R.id.rvTvFragment))
            .check(matches(isDisplayed()))
        Espresso.onView(
            AllOf.allOf(
                withId(R.id.swipeContainer),
                IsNot.not(isDisplayed())
            )
        )
        Espresso.onView(withId(R.id.rvTvFragment)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                randomNumber
            )
        ).also {
            tvTitle = HelperUtils.getTitleRecyclerView(it, randomNumber)
        }.perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                randomNumber,
                ViewActions.click()
            )
        )
        Espresso.onView(withId(R.id.titleTv))
            .check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.titleTv))
            .check(matches(withText(tvTitle)))
        Espresso.onView(withId(R.id.textStatus)).perform(NestedScrollViewExtension())
        Espresso.onView(withId(R.id.textStatus))
            .check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.textStatus))
            .check(matches(IsNot.not(withText(""))))
        Espresso.onView(withId(R.id.firstAirDate))
            .check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.firstAirDate))
            .check(matches(IsNot.not(withText(""))))
        Espresso.onView(withId(R.id.numberOfSeasons)).perform(NestedScrollViewExtension())
        Espresso.onView(withId(R.id.numberOfSeasons))
            .check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.numberOfSeasons))
            .check(matches(IsNot.not(withText(""))))
        Espresso.onView(withId(R.id.numberOfEpisodes))
            .check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.numberOfEpisodes))
            .check(matches(IsNot.not(withText(""))))
        Espresso.onView(withId(R.id.textOverview)).perform(NestedScrollViewExtension())
        Espresso.onView(withId(R.id.textOverview))
            .check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.textOverview))
            .check(matches(IsNot.not(withText(""))))
        Espresso.onView(withText(R.string.bookmark)).perform(ViewActions.click())

        Espresso.pressBack()

        Espresso.onView(withId(R.id.bookmark_button))
            .check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.bookmark_button))
            .perform(ViewActions.click())
        Espresso.onView(withText(R.string.label_tv)).perform(ViewActions.click())
        Espresso.onView(withText(tvTitle)).check(matches(isDisplayed()))
        Espresso.onView(withText(tvTitle)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.titleTv)).check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.titleTv))
            .check(matches(withText(tvTitle)))
        Espresso.onView(withId(R.id.titleTv)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.titleTv))
            .check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.titleTv))
            .check(matches(withText(tvTitle)))
        Espresso.onView(withId(R.id.textStatus)).perform(NestedScrollViewExtension())
        Espresso.onView(withId(R.id.textStatus))
            .check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.textStatus))
            .check(matches(IsNot.not(withText(""))))
        Espresso.onView(withId(R.id.firstAirDate))
            .check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.firstAirDate))
            .check(matches(IsNot.not(withText(""))))
        Espresso.onView(withId(R.id.numberOfSeasons)).perform(NestedScrollViewExtension())
        Espresso.onView(withId(R.id.numberOfSeasons))
            .check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.numberOfSeasons))
            .check(matches(IsNot.not(withText(""))))
        Espresso.onView(withId(R.id.numberOfEpisodes))
            .check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.numberOfEpisodes))
            .check(matches(IsNot.not(withText(""))))
        Espresso.onView(withId(R.id.textOverview)).perform(NestedScrollViewExtension())
        Espresso.onView(withId(R.id.textOverview))
            .check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.textOverview))
            .check(matches(IsNot.not(withText(""))))
    }
}