package com.khaisheen.egenda.Activities;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class RegisterActivityTest {

    private static void deleteAcc(FirebaseAuth fA, String email, String pass){
        fA.signInWithEmailAndPassword(email, pass);
        FirebaseUser user = fA.getCurrentUser();
        user.delete();
    }

    private void createAcc(Activity someActivity, final FirebaseAuth fA, final String mDisplayName, String mEmail, String mPass) {
        fA.createUserWithEmailAndPassword(mEmail, mPass).addOnCompleteListener(someActivity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success
                    FirebaseUser user = fA.getCurrentUser();

                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(mDisplayName).build();

                    user.updateProfile(profileUpdates);

                }
            }

        });
    }


    @Rule
    public ActivityTestRule<RegisterActivity> mActivityTestRule = new ActivityTestRule<RegisterActivity>(RegisterActivity.class);
    private RegisterActivity mActivity = null;
    Activity activity;

    Instrumentation.ActivityMonitor loginMonitor = getInstrumentation().addMonitor(LoginActivity.class.getName(),null,false);


    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();
    }

    @Test
    public void validateProfTest() throws InterruptedException {

        FirebaseApp.initializeApp(InstrumentationRegistry.getTargetContext());
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser() != null){
            mAuth.signOut();
        }

        String mProfDisplayName = "testing";
        String mProfEmail = "testing@sutd.edu.sg";
        String mProfPass = "testing123";

        final boolean[] status = {false};

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docIdRef = db.collection("employees").document(mProfEmail);
        docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        status[0]=true;
                    } else {

                    }
                } else {
                    Log.d("SOMEERROR", "get failed with ", task.getException());
                }
            }
        });

        Thread.sleep(2000);

        Assert.assertEquals(true, status[0]);
        //assertNull();

        /*

        createAcc(mActivity,mAuth,mProfDisplayName,mProfEmail,mProfPass);
        Thread.sleep(2000);

        mAuth.signInWithEmailAndPassword(mProfEmail,mProfPass);
        Thread.sleep(2000);

        FirebaseUser user = mAuth.getCurrentUser();
        assertNotNull(user);

        user.delete();
        Thread.sleep(2000);

        assertNull(mAuth.getCurrentUser()); */
    }

    @Test
    public void registerProf() throws InterruptedException {

        FirebaseApp.initializeApp(InstrumentationRegistry.getTargetContext());
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser() != null){
            mAuth.signOut();
        }

        String mProfDisplayName = "testing";
        String mProfEmail = "testing@sutd.edu.sg";
        String mProfPass = "testing123";

        createAcc(mActivity,mAuth,mProfDisplayName,mProfEmail,mProfPass);
        Thread.sleep(2000);

        mAuth.signInWithEmailAndPassword(mProfEmail,mProfPass);
        Thread.sleep(2000);

        FirebaseUser user = mAuth.getCurrentUser();
        assertNotNull(user);

        user.delete();
        Thread.sleep(2000);

        assertNull(mAuth.getCurrentUser());
    }

    @After
    public void tearDown() throws Exception {
        mActivity = null;
    }
}