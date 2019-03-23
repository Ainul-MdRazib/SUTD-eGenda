package com.khaisheen.egenda;

import android.support.annotation.NonNull;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.khaisheen.egenda.Activities.LoginActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTests extends LoginActivity{
    private LoginActivity mLoginActivity;
    private TextView mEmailField;
    private TextView mPasswordField;
    private Button mLoginButton;

    String TAG = "100";

    private FirebaseAuth mAuth;

    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule =
            new ActivityTestRule(LoginActivity.class);

    @Before
    public void setUpFB(){
        mAuth = FirebaseAuth.getInstance();
    }

    @Test
    public void goodLoginTest(){

        String goodEmail = "booga@sutd.edu.sg";
        String goodPW = "boogaboo";

        if(mAuth.getCurrentUser()!=null){
            mAuth.signOut();
        }

        mAuth.signInWithEmailAndPassword(goodEmail, goodPW)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                        }
                    }
                });

        final FirebaseUser currentUser = mAuth.getCurrentUser();

        assert(currentUser!=null);
    }

/*
    @Before
    public void setUp() throws Exception {
        // TODO: Initialise test activity LoginActivityTest
        mLoginActivity = new LoginActivity();
        mEmailField = (TextView) mLoginActivity.findViewById(R.id.EmailField);
        mLoginButton = (Button) mLoginActivity.findViewById(R.id.LoginButton);
        mPasswordField = (TextView) mLoginActivity.findViewById(R.id.PassField);

    }

    @Test
    public void TestActInitTest(){
        Assert.assertNotNull("Email Field not initialised", mEmailField);
        Assert.assertNotNull("Login Button not initialised", mLoginButton);
        Assert.assertNotNull("Password field not initialised", mPasswordField);
    }

    @Test
    public void BadSignInTest() {
        String badEmail = "werong@email.com";
        String badPW = "notbooaga";

        //
    }
    */
}