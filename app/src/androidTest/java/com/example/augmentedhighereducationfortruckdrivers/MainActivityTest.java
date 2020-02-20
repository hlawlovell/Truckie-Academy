package com.example.augmentedhighereducationfortruckdrivers;

import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.view.View;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.InstrumentationRegistry.getInstrumentation;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
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
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;

public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    public ActivityTestRule<MainActivity> mActivityTestRule1 = new ActivityTestRule<MainActivity>(MainActivity.class, true, false);

    private MainActivity mActivity = null;

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();
    }

    @Test
    public void testView() {
        View view = mActivity.findViewById(R.id.list_view);
        assertNotNull(view);
    }

    @Test
    public void testSaySomethingWhenLaunchAtTheFirstTime() {
        assertEquals(mActivity.textToSay, "Hello, nice to meet you!");
    }

    @Test
    public void testWhetherTheFirstHomePageWillChangeItsStatusAutomatically() {
        assertTrue(MainActivity.firstHomePage);
    }

    @Test
    public void ShouldBeAbleToLaunchMainScreen() {
        onView(withId(R.id.list_view)).check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void TextWhetherTheMainScreenIsClickable() {
        onView(withId(R.id.list_view)).check(ViewAssertions.matches(isClickable()));
    }

    @Test
    public void TextWhetherTheMainScreenIsCompletedDisplayed() {
        onView(withId(R.id.list_view)).check(ViewAssertions.matches(isCompletelyDisplayed()));
    }

    @Test
    public void TextWhetherTheMainScreenIsDisplayingAtLeast1() {
        onView(withId(R.id.list_view)).check(ViewAssertions.matches(isDisplayingAtLeast(1)));
    }

    @Test
    public void TextWhetherTheMainScreenIsDisplayingAtLeast2() {
        onView(withId(R.id.list_view)).check(ViewAssertions.matches(isDisplayingAtLeast(2)));
    }

    @Test
    public void TextWhetherTheMainScreenIsDisplayingAtLeast3() {
        onView(withId(R.id.list_view)).check(ViewAssertions.matches(isDisplayingAtLeast(3)));
    }

    @Test
    public void TextWhetherTheMainScreenIsDisplayingAtLeast10() {
        onView(withId(R.id.list_view)).check(ViewAssertions.matches(isDisplayingAtLeast(10)));
    }

    @Test
    public void TextWhetherTheMainScreenIsDisplayingAtLeast100() {
        onView(withId(R.id.list_view)).check(ViewAssertions.matches(isDisplayingAtLeast(100)));
    }

    @Test
    public void TextWhetherTheMainScreenIsEnabled() {
        onView(withId(R.id.list_view)).check(ViewAssertions.matches(isEnabled()));
    }

    @Test
    public void TextWhetherTheMainScreenFocusable() {
        onView(withId(R.id.list_view)).check(ViewAssertions.matches(isFocusable()));
    }

    @Test
    public void testLaunch() {
        Intent intent = new Intent();
        mActivityTestRule.launchActivity(intent);

    }

    @Test
    public void testWhetherToolBarIsEnabled() {
        Intent intent = new Intent();
        mActivityTestRule.launchActivity(intent);
        onView(withId(R.id.toolbar)).check(matches(isEnabled()));
    }

    @Test
    public void testWhetherToolBarIsDisplayed() {
        Intent intent = new Intent();
        mActivityTestRule.launchActivity(intent);
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()));
    }

    @Test
    public void testWhetherToolBarIsCompletelyDisplayed() {
        Intent intent = new Intent();
        mActivityTestRule.launchActivity(intent);
        onView(withId(R.id.toolbar)).check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void testWhetherToolBarIsDisplayedAtLeast1() {
        Intent intent = new Intent();
        mActivityTestRule.launchActivity(intent);
        onView(withId(R.id.toolbar)).check(matches(isDisplayingAtLeast(1)));
    }

    @Test
    public void testWhetherToolBarIsDisplayedAtLeast2() {
        Intent intent = new Intent();
        mActivityTestRule.launchActivity(intent);
        onView(withId(R.id.toolbar)).check(matches(isDisplayingAtLeast(2)));
    }

    @Test
    public void testWhetherToolBarIsDisplayedAtLeast10() {
        Intent intent = new Intent();
        mActivityTestRule.launchActivity(intent);
        onView(withId(R.id.toolbar)).check(matches(isDisplayingAtLeast(10)));
    }

    @Test
    public void testWhetherToolBarIsNotClickable() {
        Intent intent = new Intent();
        mActivityTestRule.launchActivity(intent);
        onView(withId(R.id.toolbar)).check(matches(not(isClickable())));
    }

    @Test
    public void testTheSpeechInHelpActivity() {
        assertEquals(mActivity.text, "Dear users, thank you for choosing us. This app is designed for truck drivers to provide higher education. We are still perfecting it and will continue to update it. We hope everyone can enjoy it. Sincerely, all team members.");
    }

    @Test
    public void testTheSpeechInHelpActivityNotNull() {
        assertNotNull(mActivity.text);
    }

    @Test
    public void testTheSpeechForLessonSegment1() {
        assertEquals(mActivity.text1, "Summer is high time drivers take care of tyres as the high temperature increases the chance of tyre explosions. After driving for long distance, drivers sometimes use cold water to immediately cool down the tyres as they got really hot. This, however, is one cause of tyre explosion. The correct way is to park the truck in shade and let it cool down naturally. Another way is to adjust the pressure according to temperature. carrying load and tyre abrasion. Pressure too high or low can both cause tyre explosion. The article has been finished, repeat or start to answer the question?");

    }

    @Test
    public void testTheSpeechForLessonSegment1NotNull() {
        assertNotNull(mActivity.text1);

    }

    @Test
    public void testTheSpeechForLessonSegment2() {
        assertEquals(mActivity.text2, "In summer, driver uses AC quite often without realising many hazard of long using hours of it. First, AC equipped truck has better tightness. Fuels's burning process in engine can produce carbon monoxide, which will make driver sick and dizzy. So drivers are advised to turn of AC and open windows after driving for a while. The article has been finished, repeat or start to answer the question?");
    }

    @Test
    public void testTheSpeechForLessonSegment2NotNull() {
        assertNotNull(mActivity.text2);
    }

    @Test
    public void testClickMenuItemAddLesson() {
        Espresso.openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("Add Lesson")).perform(click());
    }

    @Test
    public void testWhetherClickMenuItemAddLessonIsEnabled() {
        Espresso.openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("Add Lesson")).perform(click()).check(matches(isEnabled()));
    }

    @Test
    public void testWhetherClickMenuItemAddLessonIsNotChecked() {
        Espresso.openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("Add Lesson")).perform(click()).check(matches(not(isChecked())));
    }

    @Test
    public void testWhetherClickMenuItemAddLessonIsFocusable() {
        Espresso.openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("Add Lesson")).perform(click()).check(matches(not(isFocusable())));
    }

    @Test
    public void testWhetherClickMenuItemAddLessonIsCompletelyDisplayed() {
        Espresso.openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("Add Lesson")).perform(click()).check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void testWhetherClickMenuItemAddLessonIsNotClickable() {
        Espresso.openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("Add Lesson")).perform(click()).check(matches(not(isClickable())));
    }

    @Test
    public void testWhetherClickMenuItemAddLessonIsNotNotClickable() {
        Espresso.openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("Add Lesson")).perform(click()).check(matches(not(isNotChecked())));
    }

    @Test
    public void testClickMenuItemHelp() {
        Espresso.openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("Help")).perform(click());
    }

    @Test
    public void testClickMenuItemHelpIsEnabled() {
        Espresso.openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("Help")).perform(click()).check(matches(isEnabled()));
    }

    @Test
    public void testClickMenuItemHelpIsNotFocusable() {
        Espresso.openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("Help")).perform(click()).check(matches(not(isFocusable())));
    }

    @Test
    public void testClickMenuItemHelpIsNotChecked() {
        Espresso.openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("Help")).perform(click()).check(matches(not(isChecked())));
    }

    @Test
    public void testClickMenuItemHelpIsCompletelyDisplayed() {
        Espresso.openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("Help")).perform(click()).check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void testClickMenuItemHelpIsNotClickable() {
        Espresso.openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("Add Lesson")).perform(click()).check(matches(not(isClickable())));
    }

    @Test
    public void testClickMenuItemHelpIsNotNotChecked() {
        Espresso.openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("Add Lesson")).perform(click()).check(matches(not(isNotChecked())));
    }

    @Test
    public void testTheNumberOfFileList() {
        assertEquals(mActivity.fileList.size(), 1);
    }

    @Test
    public void testNumberOfFileNameList() {
        assertEquals(mActivity.filenamelist.size(), 1);
    }

    @Test
    public void testTheNameOfFileNameList() {
        assertEquals(mActivity.filenamelist.get(0), "Summer Safety");
    }

    @Test
    public void testTheNameOfFileList() {
        assertEquals(mActivity.fileList.get(0), "summer_safety.json");
    }

    @Test
    public void testLanguage1() {
        assertEquals(mActivity.result1,0);
    }

    @Test
    public void testLanguage2() {
        assertEquals(mActivity.result2,0);
    }

    @After
    public void tearDown() throws Exception {
        mActivity = null;
    }
}