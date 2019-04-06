package com.khaisheen.egenda;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.rule.ActivityTestRule;

import com.khaisheen.egenda.Activities.AddCourseActivity;
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

public class AddCourseActivityTest {

    @Rule
    public ActivityTestRule<AddCourseActivity> rule = new ActivityTestRule<>(AddCourseActivity.class);

    Instrumentation.ActivityMonitor thisActivityMonitor = getInstrumentation().addMonitor(AddCourseActivity.class.getName(), null,false);
    Activity activity;

    private AddCourseActivity addCourseActivity;

    @Before
    public void setUp() throws Exception {
        addCourseActivity = rule.getActivity();
    }


    @Test
    public void testAllViewsExist(){

        int[] ids = new int[]{R.id.SubjectField, R.id.DurationField, R.id.VenueField, R.id.SubjectSpinner,
                R.id.DurationSpinner, R.id.VenueSpinner, R.id.ProfField, R.id.CohortField, R.id.ProfPick,
                R.id.CohortPick, R.id.SubmitButton, R.id.CancelButton};

        for(int id : ids){
            assertNotNull(addCourseActivity.findViewById(id));
        }
    }
    @Test
    public void testSubmitButtonNoInputs(){

        onView(withId(R.id.SubmitButton)).perform(click());
        activity = getInstrumentation().waitForMonitorWithTimeout(thisActivityMonitor,5000);
        assertFalse(activity.isFinishing());
    }
    @Test
    public void testCancelButton(){

        onView(withId(R.id.CancelButton)).perform(click());
        activity = getInstrumentation().waitForMonitorWithTimeout(thisActivityMonitor,10000);
        assertTrue(activity.isFinishing());
    }

    @After
    public void tearDown() throws Exception {
        addCourseActivity = null;
    }
}