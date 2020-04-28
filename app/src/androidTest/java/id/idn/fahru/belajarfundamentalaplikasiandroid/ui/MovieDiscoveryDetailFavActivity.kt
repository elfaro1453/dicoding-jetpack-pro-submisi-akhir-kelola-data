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
class MovieDiscoveryDetailFavActivity {
    /*
    * Test Main Activity Movie Fragment then Detail Movie Activity
    * Bookmark a movie then pressback
    * Go to Favourite Activity Movie Fragment then see if the movie has been bookmarked
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
    fun movieDiscoveryDetailFavActivity() {
        var movieTitle: String
        val lastIndex = 19
        val randomNumber = Random.nextInt(0, lastIndex)

        Espresso.onView(withText(R.string.label_movie)).perform(ViewActions.click())
        Espresso.onView(
            AllOf.allOf(
                withId(R.id.swipeContainer),
                isCompletelyDisplayed()
            )
        )
        Espresso.onView(withId(R.id.rvMovieFragment))
            .check(matches(isDisplayed()))
        Espresso.onView(
            AllOf.allOf(
                withId(R.id.swipeContainer),
                IsNot.not(isDisplayed())
            )
        )
        Espresso.onView(withId(R.id.rvMovieFragment)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                randomNumber
            )
        ).also {
            movieTitle = HelperUtils.getTitleRecyclerView(it, randomNumber)
        }.perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                randomNumber,
                ViewActions.click()
            )
        )
        Espresso.onView(withId(R.id.titleMovie)).check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.titleMovie)).check(matches(withText(movieTitle)))
        Espresso.onView(withId(R.id.textStatus)).perform(NestedScrollViewExtension())
        Espresso.onView(withId(R.id.textStatus)).check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.textStatus)).check(matches(IsNot.not(withText(""))))
        Espresso.onView(withId(R.id.textReleaseDate)).check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.textReleaseDate)).check(matches(IsNot.not(withText(""))))
        Espresso.onView(withId(R.id.textRevenue)).perform(NestedScrollViewExtension())
        Espresso.onView(withId(R.id.textRevenue)).check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.textRevenue)).check(matches(IsNot.not(withText(""))))
        Espresso.onView(withId(R.id.textBudget)).check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.textBudget)).check(matches(IsNot.not(withText(""))))
        Espresso.onView(withId(R.id.textOverview)).perform(NestedScrollViewExtension())
        Espresso.onView(withId(R.id.textOverview)).check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.textOverview)).check(matches(IsNot.not(withText(""))))
        Espresso.onView(withText(R.string.bookmark)).perform(ViewActions.click())

        Espresso.pressBack()

        Espresso.onView(withId(R.id.bookmark_button))
            .check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.bookmark_button))
            .perform(ViewActions.click())
        Espresso.onView(withText(R.string.label_movie)).perform(ViewActions.click())
        Espresso.onView(withText(movieTitle)).check(matches(isDisplayed()))
        Espresso.onView(withText(movieTitle)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.titleMovie))
            .check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.titleMovie))
            .check(matches(withText(movieTitle)))
        Espresso.onView(withId(R.id.titleMovie)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.titleMovie)).check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.titleMovie)).check(matches(withText(movieTitle)))
        Espresso.onView(withId(R.id.textStatus)).perform(NestedScrollViewExtension())
        Espresso.onView(withId(R.id.textStatus)).check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.textStatus)).check(matches(IsNot.not(withText(""))))
        Espresso.onView(withId(R.id.textReleaseDate)).check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.textReleaseDate)).check(matches(IsNot.not(withText(""))))
        Espresso.onView(withId(R.id.textRevenue)).perform(NestedScrollViewExtension())
        Espresso.onView(withId(R.id.textRevenue)).check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.textRevenue)).check(matches(IsNot.not(withText(""))))
        Espresso.onView(withId(R.id.textBudget)).check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.textBudget)).check(matches(IsNot.not(withText(""))))
        Espresso.onView(withId(R.id.textOverview)).perform(NestedScrollViewExtension())
        Espresso.onView(withId(R.id.textOverview)).check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.textOverview)).check(matches(IsNot.not(withText(""))))
    }
}