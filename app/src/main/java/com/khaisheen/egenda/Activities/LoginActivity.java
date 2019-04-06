package com.khaisheen.egenda.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.khaisheen.egenda.R;

// Login page

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private ProgressDialog mProgress;

    private String TAG = "001";

    private TextView EmailField;
    private TextView PassField;

    private Button LoginButton;
    private Button RegisterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        FirebaseApp.initializeApp(LoginActivity.this);
        mAuth = FirebaseAuth.getInstance();
        LoginButton = findViewById(R.id.LoginButton);

        EmailField = findViewById(R.id.EmailField);
        PassField = findViewById(R.id.RegPassField);

        mProgress = new ProgressDialog(LoginActivity.this);


        /* LOG IN */
        /* TODO: AUTH STUFF */
        final FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser == null){
            Toast.makeText(LoginActivity.this, "Please sign in with your sutd.edu.sg email.",Toast.LENGTH_LONG).show();
            System.out.println("No login");
        }
        else{
            System.out.println(currentUser.getEmail() + " is logged in.");
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }


        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mEmail = EmailField.getEditableText().toString();
                String mPass = PassField.getEditableText().toString();
                if(mEmail.equals("") && mPass.equals("")){
                    Toast.makeText(LoginActivity.this, "Retype your SUTD email and password.", Toast.LENGTH_LONG).show();
                }else{
                    signIn(mEmail,mPass);
                }
            }
        });

        RegisterButton = findViewById(R.id.RegisterButton);
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    protected void signIn(String email, String password){
        mProgress.setMessage("Logging you in...");
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        mProgress.dismiss();
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            finish();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        }

                    }
                });
    }
}
