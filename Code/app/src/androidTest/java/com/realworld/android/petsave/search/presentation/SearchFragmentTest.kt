/*
 * Copyright (c) 2022 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * This project and source code may use libraries or frameworks that are
 * released under various Open-Source licenses. Use of those libraries and
 * frameworks are governed by their own individual licenses.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.realworld.android.petsave.search.presentation

import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.realworld.android.petsave.R
import com.realworld.android.petsave.RxImmediateSchedulerRule
import com.realworld.android.petsave.TestCoroutineRule
import com.realworld.android.petsave.common.data.FakeRepository
import com.realworld.android.petsave.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class SearchFragmentTest {

  @get:Rule
  var hiltRule = HiltAndroidRule(this)

  @get:Rule
  val rxImmediateSchedulerRule = RxImmediateSchedulerRule()

  @Before
  fun setup() {
    hiltRule.inject()
  }

  @Test
  fun searchFragment_testSearch_success() {
    // Given
    val nameToSearch = FakeRepository().remotelySearchableAnimal.name
    launchFragmentInHiltContainer<SearchFragment>()

    // When
    with(onView(withId(R.id.search))) {
      perform(click())
      perform(typeSearchViewText(nameToSearch))
    }

    // Then
    with(onView(withId(R.id.searchRecyclerView))) {
      check(matches(childCountIs(1)))
      check(matches(hasDescendant(withText(nameToSearch))))
    }
  }

  private fun typeSearchViewText(text: String): ViewAction {
    return object : ViewAction {
      override fun getDescription(): String {
        return "Type in SearchView"
      }

      override fun getConstraints(): Matcher<View> {
        return allOf(isDisplayed(), isAssignableFrom(SearchView::class.java))
      }

      override fun perform(uiController: UiController?, view: View?) {
        (view as SearchView).setQuery(text, false)
      }
    }
  }

  private fun childCountIs(expectedChildCount: Int): Matcher<View> {
    return object: BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
      override fun describeTo(description: Description?) {
        description?.appendText("RecyclerView with item count: $expectedChildCount")
      }

      override fun matchesSafely(item: RecyclerView?): Boolean {
        return item?.adapter?.itemCount == expectedChildCount
      }
    }
  }
}
