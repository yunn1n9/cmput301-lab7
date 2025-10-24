package com.example.androiduitesting;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ShowActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> scenario =
            new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setUp() {
        Intents.init();
    }

    @After
    public void tearDown() {
        Intents.release();
    }

    private void addCity(String name) {
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(ViewActions.typeText(name));
        onView(withId(R.id.button_confirm)).perform(click());
    }

    private void openFirstCity() {
        onData(is(instanceOf(String.class)))
                .inAdapterView(withId(R.id.city_list))
                .atPosition(0)
                .perform(click());
    }

    @Test
    public void testSwitchActivity() {
        addCity("Edmonton");
        openFirstCity();

        intended(hasComponent(ShowActivity.class.getName()));
        onView(withId(R.id.text_city_name)).check(matches(isDisplayed()));
    }

    @Test
    public void testCityNameConsistent() {
        addCity("Vancouver");
        openFirstCity();
        onView(withId(R.id.text_city_name)).check(matches(withText("Vancouver")));
    }

    @Test
    public void testBackButton() {
        addCity("Calgary");
        openFirstCity();

        // Click on the back button
        onView(withId(R.id.button_back)).perform(click());

        // ShowActivity view should disappear
        onView(withId(R.id.text_city_name)).check(doesNotExist());
        // MainActivity UI (e.g. Add button) should be visible again
        onView(withId(R.id.button_add)).check(matches(isDisplayed()));
    }
}
