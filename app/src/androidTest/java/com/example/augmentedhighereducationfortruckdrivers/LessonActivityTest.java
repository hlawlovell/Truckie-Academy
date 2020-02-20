package com.example.augmentedhighereducationfortruckdrivers;

import android.content.Intent;
import android.view.View;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayingAtLeast;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.isFocusable;
import static androidx.test.espresso.matcher.ViewMatchers.isJavascriptEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.isNotChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.isSelected;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

public class LessonActivityTest {

    @Rule
    public ActivityTestRule<LessonActivity> lActivityTestRule = new ActivityTestRule<LessonActivity>(LessonActivity.class, false, false);

    private LessonActivity lActivity = null;

    @Before
    public void setUp() throws Exception {
        lActivity = lActivityTestRule.getActivity();
    }

    @Test
    public void testLaunch() {
        Intent i = new Intent();
        lActivityTestRule.launchActivity(i);
    }

    @Test
    public void testFILE_REQUEST_CODE() {
        Intent i = new Intent();
        lActivityTestRule.launchActivity(i);
        assertEquals(lActivity.FILE_REQUEST_CODE, 1);
    }

    @Test
    public void testREQUEST_CODE_ASK_PERMISSIONS() {
        Intent i = new Intent();
        lActivityTestRule.launchActivity(i);
        assertEquals(lActivity.REQUEST_CODE_ASK_PERMISSIONS, 123);
    }

    @Test
    public void testWhetherToolBarIsDisplayed() {
        Intent i = new Intent();
        lActivityTestRule.launchActivity(i);
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()));
    }

    @Test
    public void testWhetherToolBarIsEnabled() {
        Intent intent = new Intent();
        lActivityTestRule.launchActivity(intent);
        onView(withId(R.id.toolbar)).check(matches(isEnabled()));
    }

    @Test
    public void testWhetherToolBarIsCompletelyDisplayed() {
        Intent intent = new Intent();
        lActivityTestRule.launchActivity(intent);
        onView(withId(R.id.toolbar)).check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void testWhetherToolBarIsDisplayedAtLeast1() {
        Intent intent = new Intent();
        lActivityTestRule.launchActivity(intent);
        onView(withId(R.id.toolbar)).check(matches(isDisplayingAtLeast(1)));
    }

    @Test
    public void testWhetherToolBarIsDisplayedAtLeast2() {
        Intent intent = new Intent();
        lActivityTestRule.launchActivity(intent);
        onView(withId(R.id.toolbar)).check(matches(isDisplayingAtLeast(2)));
    }

    @Test
    public void testWhetherToolBarIsDisplayedAtLeast10() {
        Intent intent = new Intent();
        lActivityTestRule.launchActivity(intent);
        onView(withId(R.id.toolbar)).check(matches(isDisplayingAtLeast(10)));
    }

    @Test
    public void toolBarIsNotSelectable() {
        Intent i = new Intent();
        lActivityTestRule.launchActivity(i);
        onView(withId(R.id.toolbar)).check(matches(not(isSelected())));
    }

    @Test
    public void testToolBarIsNotEmpty() {
        Intent i = new Intent();
        lActivityTestRule.launchActivity(i);
        onView(withId(R.id.toolbar)).check(matches(not(withText(""))));
    }

    @Test
    public void testWhetherButtonUploadedIsDisplayed() {
        Intent i = new Intent();
        lActivityTestRule.launchActivity(i);
        onView(withId(R.id.button_upload)).check(matches(isDisplayed()));
    }

    @Test
    public void testWhetherButtonUploadedIsEnabled() {
        Intent intent = new Intent();
        lActivityTestRule.launchActivity(intent);
        onView(withId(R.id.button_upload)).check(matches(isEnabled()));
    }

    @Test
    public void testWhetherButtonUploadedIsCompletelyDisplayed() {
        Intent intent = new Intent();
        lActivityTestRule.launchActivity(intent);
        onView(withId(R.id.button_upload)).check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void testWhetherButtonUploadedIsDisplayedAtLeast1() {
        Intent intent = new Intent();
        lActivityTestRule.launchActivity(intent);
        onView(withId(R.id.button_upload)).check(matches(isDisplayingAtLeast(1)));
    }

    @Test
    public void testWhetherButtonUploadedIsDisplayedAtLeast2() {
        Intent intent = new Intent();
        lActivityTestRule.launchActivity(intent);
        onView(withId(R.id.button_upload)).check(matches(isDisplayingAtLeast(2)));
    }

    @Test
    public void testWhetherButtonUploadedIsDisplayedAtLeast10() {
        Intent intent = new Intent();
        lActivityTestRule.launchActivity(intent);
        onView(withId(R.id.button_upload)).check(matches(isDisplayingAtLeast(10)));
    }

    @Test
    public void buttonUploadedIsNotSelectable() {
        Intent i = new Intent();
        lActivityTestRule.launchActivity(i);
        onView(withId(R.id.button_upload)).check(matches(not(isSelected())));
    }

    @Test
    public void testButtonUploadedIsNotEmpty() {
        Intent i = new Intent();
        lActivityTestRule.launchActivity(i);
        onView(withId(R.id.button_upload)).check(matches(not(withText(""))));
    }


    @Test
    public void testConvertFileName1() {
        Intent i = new Intent();
        lActivityTestRule.launchActivity(i);
        assertEquals(lActivity.convertFileName("SPRINGSAFETY.json"), "springsafety.json");
    }

    @Test
    public void testConvertFileName2() {
        Intent i = new Intent();
        lActivityTestRule.launchActivity(i);
        assertEquals(lActivity.convertFileName("WINTER_SAFETY.json"), "winter_safety.json");
    }

    @Test
    public void testConvertFileName3() {
        Intent i = new Intent();
        lActivityTestRule.launchActivity(i);
        assertEquals(lActivity.convertFileName("Autumn_safety.json"), "autumn_safety.json");
    }

    @Test
    public void testConvertFileName4() {
        Intent i = new Intent();
        lActivityTestRule.launchActivity(i);
        assertEquals(lActivity.convertFileName("HELLOBRO"), "hellobro");
    }

    @Test
    public void testConvertFileName5() {
        Intent i = new Intent();
        lActivityTestRule.launchActivity(i);
        assertEquals(lActivity.convertFileName("summer__safety.json"), "summer__safety.json");
    }

    @Test
    public void testConvertFileName6() {
        Intent i = new Intent();
        lActivityTestRule.launchActivity(i);
        assertEquals(lActivity.convertFileName("________________"), "________________");
    }

    @Test
    public void testBarLayoutIsDisplayed() {
        Intent i = new Intent();
        lActivityTestRule.launchActivity(i);
        onView(withId(R.id.bar_layout)).check(matches(isDisplayed()));
    }

    @Test
    public void testBarLayoutIsEnabled() {
        Intent i = new Intent();
        lActivityTestRule.launchActivity(i);
        onView(withId(R.id.bar_layout)).check(matches(isEnabled()));
    }

    @Test
    public void testBarLayoutIsCompleteDisplayed() {
        Intent i = new Intent();
        lActivityTestRule.launchActivity(i);
        onView(withId(R.id.bar_layout)).check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void testBarLayoutIsNotClickable() {
        Intent i = new Intent();
        lActivityTestRule.launchActivity(i);
        onView(withId(R.id.bar_layout)).check(matches(not(isClickable())));
    }

    @Test
    public void testBarLayoutIsNotSelected() {
        Intent i = new Intent();
        lActivityTestRule.launchActivity(i);
        onView(withId(R.id.bar_layout)).check(matches(not(isSelected())));
    }

    @Test
    public void testBarLayoutIsFocusable() {
        Intent i = new Intent();
        lActivityTestRule.launchActivity(i);
        onView(withId(R.id.bar_layout)).check(matches(not(isFocusable())));
    }

    @Test
    public void testBarLayoutIsNotChecked2() {
        Intent i = new Intent();
        lActivityTestRule.launchActivity(i);
        onView(withId(R.id.bar_layout)).check(matches(not(isChecked())));
    }

    @Test
    public void testBarLayoutIsNotRoot() {
        Intent i = new Intent();
        lActivityTestRule.launchActivity(i);
        onView(withId(R.id.bar_layout)).check(matches(not(isRoot())));
    }

    @Test
    public void testBarLayoutIsNotJavaScriptEnabled() {
        Intent i = new Intent();
        lActivityTestRule.launchActivity(i);
        onView(withId(R.id.bar_layout)).check(matches(not(isJavascriptEnabled())));
    }

    @After
    public void tearDown() throws Exception {
        lActivity = null;
    }
}