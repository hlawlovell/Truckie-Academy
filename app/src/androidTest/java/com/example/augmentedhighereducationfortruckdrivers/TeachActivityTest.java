package com.example.augmentedhighereducationfortruckdrivers;

import android.content.Intent;
import android.view.View;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

public class TeachActivityTest {

    @Rule
    public ActivityTestRule<TeachActivity> tActivityTestRule = new ActivityTestRule<TeachActivity>(TeachActivity.class, false, false);

    private TeachActivity tActivity = null;

    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    private MainActivity mActivity = null;

    @Before
    public void setUp() throws Exception {
        tActivity = tActivityTestRule.getActivity();
        mActivity = mActivityTestRule.getActivity();
    }

    @Test
    public void testLaunchTeachActivity() {
        Intent i = new Intent();
        i.putExtra("FILE_NAME", "summer safety.json");
        tActivityTestRule.launchActivity(i);
    }

    @Test
    public void testSetFileNameToTeachActivity() {
        Intent i = new Intent();
        i.putExtra("FILE_NAME", "summer safety.json");
        tActivityTestRule.launchActivity(i);
        assertEquals(TeachActivity.fileName, "summer safety.json");
    }

    @Test
    public void judgeWhetherTheSetFileNameToTeachActivityIsExist() {
        Intent i = new Intent();
        i.putExtra("FILE_NAME", "summer safety.json");
        tActivityTestRule.launchActivity(i);
        assertNotNull(TeachActivity.fileName);
    }

    @Test
    public void judgeWhetherHeardWordsIsNullAtTheBeginning() {
        Intent i = new Intent();
        i.putExtra("FILE_NAME", "summer safety.json");
        tActivityTestRule.launchActivity(i);
        assertNull(TeachActivity.heardWords);
    }

    @Test
    public void judgeWhetherLessonIsNullAtTheBeginning() {
        Intent i = new Intent();
        i.putExtra("FILE_NAME", "summer safety.json");
        tActivityTestRule.launchActivity(i);
        assertNull(TeachActivity.lesson);
    }

    @Test
    public void testCurrentState() {
        Intent i = new Intent();
        i.putExtra("FILE_NAME", "summer safety.json");
        tActivityTestRule.launchActivity(i);
        assertEquals(TeachActivity.currentState, 0);
    }

    @Test
    public void testCurrentQuestion() {
        Intent i = new Intent();
        i.putExtra("FILE_NAME", "summer safety.json");
        tActivityTestRule.launchActivity(i);
        assertEquals(TeachActivity.currentQuestion, 0);
    }

    @Test
    public void testCurrentSegmentation() {
        Intent i = new Intent();
        i.putExtra("FILE_NAME", "summer safety.json");
        tActivityTestRule.launchActivity(i);
        assertEquals(TeachActivity.currentSegment, 0);
    }

    @Test
    public void testCurrentAnswers() {
        Intent i = new Intent();
        i.putExtra("FILE_NAME", "summer safety.json");
        tActivityTestRule.launchActivity(i);
        assertEquals(TeachActivity.correctAnswers, 0);
    }

    @Test
    public void testTheSpeechAtTheBeginningOfTheLesson() {
        Intent i = new Intent();
        i.putExtra("FILE_NAME", "summer safety.json");
        tActivityTestRule.launchActivity(i);
        assertEquals(TeachActivity.greetings,"Are you ready to begin the lesson?");
    }

    @Test
    public void judgeWhetherTheSpeechAtTheBeginningOfTheLessonIsExist() {
        Intent i = new Intent();
        i.putExtra("FILE_NAME", "summer safety.json");
        tActivityTestRule.launchActivity(i);
        assertNotNull(TeachActivity.greetings);
    }

    @Test
    public void testTheFileNameTransfer() {
        Intent j = new Intent();
        mActivityTestRule.launchActivity(j);
        String fileName = mActivity.fileList.get(0);
        String changeFileName = fileName.replaceAll("_"," ");
        Intent i = new Intent();
        i.putExtra("FILE_NAME", "summer safety.json");
        tActivityTestRule.launchActivity(i);
        assertEquals(changeFileName, TeachActivity.fileName);
    }

    @Test
    public void testLesson() {
        Intent i = new Intent();
        i.putExtra("FILE_NAME", "summer safety.json");
        tActivityTestRule.launchActivity(i);
        assertEquals(TeachActivity.lesson, null);
    }

    @Test
    public void testTheAlgorithmOfCalculateStore1() {
        int total = 0;
        ArrayList<ArrayList<String>> doubleString = new ArrayList<ArrayList<String>>();
        int correct = 3;
        ArrayList<String> str1 = new ArrayList<String>();
        str1.add("1");
        str1.add("2");
        str1.add("3");
        ArrayList<String> str2 = new ArrayList<String>();
        str2.add("4");
        str2.add("5");
        str2.add("6");
        doubleString.add(str1);
        doubleString.add(str2);
        for (int i = 0; i < doubleString.size(); i++) {
            total += doubleString.get(i).size();
        }
        int score = correct*100/total;
        assertEquals(score, 50);
    }

    @Test
    public void testTheAlgorithmOfCalculateStore2() {
        int total = 0;
        ArrayList<ArrayList<String>> doubleString = new ArrayList<ArrayList<String>>();
        int correct = 4;
        ArrayList<String> str1 = new ArrayList<String>();
        str1.add("1");
        str1.add("2");
        str1.add("3");
        ArrayList<String> str2 = new ArrayList<String>();
        str2.add("4");
        str2.add("5");
        str2.add("6");
        doubleString.add(str1);
        doubleString.add(str2);
        for (int i = 0; i < doubleString.size(); i++) {
            total += doubleString.get(i).size();
        }
        int score = correct*100/total;
        assertEquals(score, 66);
    }


    @After
    public void tearDown() throws Exception {
        tActivity = null;
        mActivity = null;
    }
}