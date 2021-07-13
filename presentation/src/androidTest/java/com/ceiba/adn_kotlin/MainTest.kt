package com.ceiba.adn_kotlin

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.withId

open class MainTest {

    open fun clickButton(idButton: Int): ViewInteraction = onView(withId(idButton)).perform(click())
    open fun editText(idEditText: Int, text: String): ViewInteraction =
        onView(withId(idEditText)).perform(typeText(text), closeSoftKeyboard())
}
