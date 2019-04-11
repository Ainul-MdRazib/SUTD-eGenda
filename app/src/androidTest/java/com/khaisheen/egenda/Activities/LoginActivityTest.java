package com.khaisheen.egenda.Activities;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.rule.ActivityTestRule;

import com.google.firebase.auth.FirebaseAuth;
import com.khaisheen.egenda.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertNotNull;

public class LoginActivityTest {

    FirebaseAuth mAuth;

    @Rule // initialise LoginActivty
    public ActivityTestRule<LoginActivity> LoginRule = new ActivityTestRule<LoginActivity>(LoginActivity.class);

    private LoginActivity mActivity;

    Instrumentation.ActivityMonitor registerMonitor = getInstrumentation().addMonitor(RegisterActivity.class.getName(),null,false);
    Instrumentation.ActivityMonitor loginMonitor = getInstrumentation().addMonitor(LoginActivity.class.getName(),null,false);

    @Before
    public void setUp() throws Exception {
        mActivity = LoginRule.getActivity();
    }

    @Test
    public void testAllViewsExist(){
        assertNotNull(mActivity.findViewById(R.id.RegisterButton));
        assertNotNull(mActivity.findViewById(R.id.LoginButton));

        assertNotNull(mActivity.findViewById(R.id.EmailField));
        assertNotNull(mActivity.findViewById(R.id.PassField));
    }

    @Test
    public void testLoginActivityAccessNoDeets(){

        onView(withId(R.id.LoginButton)).perform(click());

        Activity testActivity = getInstrumentation().waitForMonitorWithTimeout(loginMonitor,5000);
        assertNotNull(testActivity);

    }

    @Test
    public void testLoginActivityAccessWithDeets(){


        onView(withId(R.id.LoginButton)).perform(click());

        Activity testActivity = getInstrumentation().waitForMonitorWithTimeout(loginMonitor,5000);
        assertNotNull(testActivity);

    }

    @Test
    public void testRegisterAccess(){
        onView(withId(R.id.RegisterButton)).perform(click());
        Activity testActivity = getInstrumentation().waitForMonitorWithTimeout(registerMonitor, 5000);
        assertNotNull(testActivity);

    }


    @After
    public void tearDown() throws Exception {

        mActivity = null;

    }
}