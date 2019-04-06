package com.khaisheen.egenda.Activities;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.rule.ActivityTestRule;

import com.khaisheen.egenda.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    private MainActivity mActivity = null;
    Activity activity;

    Instrumentation.ActivityMonitor viewSchedMonitor = getInstrumentation().addMonitor(ViewScheduleActivity.class.getName(),null,false);
    Instrumentation.ActivityMonitor addCourseMonitor = getInstrumentation().addMonitor(AddCourseActivity.class.getName(),null, false);
    Instrumentation.ActivityMonitor logOutMonitor = getInstrumentation().addMonitor(LoginActivity.class.getName(),null,false);

    @Before
    public void setUp() throws Exception {

        mActivity = mActivityTestRule.getActivity();

    }

    @Test
    public void testAllViewsExist(){
        assertNotNull(mActivity.findViewById(R.id.MainGreeting));
        assertNotNull(mActivity.findViewById(R.id.ViewSchButton));
        assertNotNull(mActivity.findViewById(R.id.LogOutButton));
        assertNotNull(mActivity.findViewById(R.id.AddSubjButton));

    }

    @Test
    public void testViewScheduleActivityAccess(){

        onView(withId(R.id.ViewSchButton)).perform(click());

        activity = getInstrumentation().waitForMonitorWithTimeout(viewSchedMonitor,5000);

        assertNotNull(activity);

    }

    @Test
    public void testAddCourseActivityAccess(){

        onView(withId(R.id.AddSubjButton)).perform(click());
        activity = getInstrumentation().waitForMonitorWithTimeout(addCourseMonitor, 5000);
        assertNotNull(activity);

    }

    @Test
    public void testLogOut(){
        onView(withId(R.id.LogOutButton)).perform(click());
        activity = getInstrumentation().waitForMonitorWithTimeout(logOutMonitor, 5000);
        assertNotNull(activity);

    }


    @After
    public void tearDown() throws Exception {

        mActivity = null;

    }
}