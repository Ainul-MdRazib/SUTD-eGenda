package com.khaisheen.egenda.Activities;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class BackEndLoginTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.khaisheen.egenda", appContext.getPackageName());
    }
    @Test
    public void FBConnectionTest(){
        FirebaseApp.initializeApp(InstrumentationRegistry.getTargetContext());
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        assertNotNull(mAuth);
    }

    @Test
    public void FBGoodLogin() throws InterruptedException {
        FirebaseApp.initializeApp(InstrumentationRegistry.getTargetContext());
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser() != null){
            mAuth.signOut();
        }

        mAuth.signInWithEmailAndPassword("booga@sutd.edu.sg","boogaboo");
        Thread.sleep(2000);
        FirebaseUser user = mAuth.getCurrentUser();

        assertNotNull(user);
    }

    @Test
    public void FBBadLogin() throws InterruptedException {
        FirebaseApp.initializeApp(InstrumentationRegistry.getTargetContext());
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser() != null){
            mAuth.signOut();
        }

        mAuth.signInWithEmailAndPassword("booga@email.sg","boogaboo");
        Thread.sleep(2000);
        FirebaseUser user = mAuth.getCurrentUser();

        assertNotNull(user);
    }



}
