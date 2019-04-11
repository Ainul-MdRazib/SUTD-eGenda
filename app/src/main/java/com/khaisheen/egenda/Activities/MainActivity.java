package com.khaisheen.egenda.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.khaisheen.egenda.Data.AddedConstraints;
import com.khaisheen.egenda.Data.AddedLessons;
import com.khaisheen.egenda.Data.Constraint;
import com.khaisheen.egenda.Data.Lesson;
import com.khaisheen.egenda.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// Main page
public class MainActivity extends AppCompatActivity {

    public static ArrayList<Constraint> CONSTRAINTS = new ArrayList<>();
//    public static ArrayList<Lesson> LESSONS = new ArrayList<>();

    AddedLessons addedLessons = AddedLessons.getInstance();
    AddedConstraints addedConstraints = AddedConstraints.getInstance();

//    public static final HashMap<String,String> START_TIME_MAP = new HashMap<String,String>(){{
//        put("8:30","0");put("9:00","2");put("9:30","3");put("10:00","4");
//        put("10:30","5");put("11:00","6");put("11:30","7");put("12:00","8");
//        put("12:30","9");put("13:00","10");put("13:30","11");put("14:00","12");
//        put("14:30","13");put("15:00","14");put("15:30","15");put("16:00","16");
//        put("16:30","17");put("17:00","18");put("17:30","19");
//    }};



    TextView MainGreeting;
    Button ViewSchButton;
    Button AddSubjButton;
    Button LogOutButton;
    Button MyLessonsButton;
    Button ConstraintsButton;
    Button ChatButton;

    SharedPreferences mPrefs;
    SharedPreferences.Editor mPrefEditor;

    String profName = "PROFNAME";
    String profPillar = "PROFPILLAR";

    FirebaseAuth mAuth;
    FirebaseFirestore db;

    String TAG = "100";

    @Override
    protected void onResume() {
        super.onResume();
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        addedConstraints.getFromFireStore(mAuth, db);
        addedLessons.getFromFirestore(mAuth, db);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        FirebaseApp.initializeApp(MainActivity.this);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();



        MainGreeting = findViewById(R.id.MainGreeting);
        MainGreeting.setText("");




        db.collection("professors")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                profName = document.get("name").toString();
                                profPillar = document.get("pillar").toString();
                                SharedPreferences sharedPref = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putString("professor", profName);
                                editor.putString("pillar", profPillar);
                                editor.commit();
//                                MainGreeting.setText("Welcome back "+ mAuth.getCurrentUser().getDisplayName() + "!");
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });


        ViewSchButton = findViewById(R.id.ViewSchButton);
        LogOutButton = findViewById(R.id.LogOutButton);
        MyLessonsButton = findViewById(R.id.MyLessonsButton);
        ConstraintsButton = findViewById(R.id.ConstraintsButton);
        ChatButton = findViewById(R.id.ChatButton);

        ViewSchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ViewScheduleActivity.class));
            }
        });

        MyLessonsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LessonsActivity.class));
            }
        });

        ChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ChatActivity.class));
            }
        });

        ConstraintsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ConstraintsActivity.class));
            }
        });

        /* LOG OUT */
        LogOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));

            }
        });
    }

}
