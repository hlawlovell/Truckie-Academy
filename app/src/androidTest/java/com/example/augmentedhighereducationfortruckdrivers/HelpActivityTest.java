package com.example.augmentedhighereducationfortruckdrivers;

import android.content.Intent;
import android.view.View;

import androidx.test.rule.ActivityTestRule;

import org.hamcrest.core.IsNot;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayingAtLeast;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.isSelected;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

public class HelpActivityTest {

    @Rule
    public ActivityTestRule<HelpActivity> hActivityTestRule = new ActivityTestRule<HelpActivity>(HelpActivity.class, false, false);

    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    private HelpActivity hActivity = null;

    @Before
    public void setUp() throws Exception {
        hActivity = hActivityTestRule.getActivity();
    }

    @Test
    public void testLaunch() {
        Intent i = new Intent();
        hActivityTestRule.launchActivity(i);
    }

    @Test
    public void buttonIsClickable() {
        Intent i = new Intent();
        hActivityTestRule.launchActivity(i);
        onView(withId(R.id.node_name_view1)).check(matches(isClickable()));
    }

    @Test
    public void buttonIsEnabled() {
        Intent i = new Intent();
        hActivityTestRule.launchActivity(i);
        onView(withId(R.id.node_name_view1)).check(matches(isEnabled()));
    }

    @Test
    public void buttonIsDisplayed() {
        Intent i = new Intent();
        hActivityTestRule.launchActivity(i);
        onView(withId(R.id.node_name_view1)).check(matches(isDisplayed()));
    }

    @Test
    public void buttonIsCompletelyDisplayed() {
        Intent i = new Intent();
        hActivityTestRule.launchActivity(i);
        onView(withId(R.id.node_name_view1)).check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void testWelcomeBack() {
        Intent i = new Intent();
        hActivityTestRule.launchActivity(i);
        assertEquals(HelpActivity.text1, "Welcome back.");
    }

    @Test
    public void testWhetherToolBarIsDisplayedAtLeast1() {
        Intent intent = new Intent();
        hActivityTestRule.launchActivity(intent);
        onView(withId(R.id.node_name_view1)).check(matches(isDisplayingAtLeast(1)));
    }

    @Test
    public void testWhetherToolBarIsDisplayedAtLeast2() {
        Intent intent = new Intent();
        hActivityTestRule.launchActivity(intent);
        onView(withId(R.id.node_name_view1)).check(matches(isDisplayingAtLeast(2)));
    }

    @Test
    public void testWhetherToolBarIsDisplayedAtLeast10() {
        Intent intent = new Intent();
        hActivityTestRule.launchActivity(intent);
        onView(withId(R.id.node_name_view1)).check(matches(isDisplayingAtLeast(10)));
    }

    @Test
    public void buttonIsNotSelectable() {
        Intent i = new Intent();
        hActivityTestRule.launchActivity(i);
        onView(withId(R.id.node_name_view1)).check(matches(not(isSelected())));
    }

    @Test
    public void testButtonIsNotEmpty() {
        Intent i = new Intent();
        hActivityTestRule.launchActivity(i);
        onView(withId(R.id.node_name_view1)).check(matches(not(withText(""))));
    }

    @Test
    public void textViewIsNotClickable() {
        Intent i = new Intent();
        hActivityTestRule.launchActivity(i);
        onView(withId(R.id.text_1)).check(matches(not(isClickable())));
    }

    @Test
    public void testViewIsEnabled() {
        Intent i = new Intent();
        hActivityTestRule.launchActivity(i);
        onView(withId(R.id.text_1)).check(matches(isEnabled()));
    }

    @Test
    public void textViewIsDisplayed() {
        Intent i = new Intent();
        hActivityTestRule.launchActivity(i);
        onView(withId(R.id.text_1)).check(matches(isDisplayed()));
    }

    @Test
    public void textViewIsCompletelyDisplayed() {
        Intent i = new Intent();
        hActivityTestRule.launchActivity(i);
        onView(withId(R.id.text_1)).check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void testTextViewWhetherToolBarIsDisplayedAtLeast1() {
        Intent intent = new Intent();
        hActivityTestRule.launchActivity(intent);
        onView(withId(R.id.text_1)).check(matches(isDisplayingAtLeast(1)));
    }

    @Test
    public void testTextViewWhetherToolBarIsDisplayedAtLeast2() {
        Intent intent = new Intent();
        hActivityTestRule.launchActivity(intent);
        onView(withId(R.id.text_1)).check(matches(isDisplayingAtLeast(2)));
    }

    @Test
    public void testTextViewWhetherToolBarIsDisplayedAtLeast10() {
        Intent intent = new Intent();
        hActivityTestRule.launchActivity(intent);
        onView(withId(R.id.text_1)).check(matches(isDisplayingAtLeast(10)));
    }

    @Test
    public void testTextViewIsSelectable() {
        Intent i = new Intent();
        hActivityTestRule.launchActivity(i);
        onView(withId(R.id.text_1)).check(matches(isSelected()));
    }

    @Test
    public void testTextViewIsNotEmpty() {
        Intent i = new Intent();
        hActivityTestRule.launchActivity(i);
        onView(withId(R.id.text_1)).check(matches(not(withText(""))));
    }

    @After
    public void tearDown() throws Exception {
        hActivity = null;
    }
}