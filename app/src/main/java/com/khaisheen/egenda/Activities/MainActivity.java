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
import com.khaisheen.egenda.Data.Constraint;
import com.khaisheen.egenda.Data.Lesson;
import com.khaisheen.egenda.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// Main page
public class MainActivity extends AppCompatActivity {

    public static ArrayList<Constraint> CONSTRAINTS = new ArrayList<>();
    public static ArrayList<Lesson> LESSONS = new ArrayList<>();

    FirebaseAuth mAuth;

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

    String TAG = "100";

    @Override
    protected void onResume() {
        super.onResume();
        refreshConstraints();
        refreshLessons();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(MainActivity.this);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();



        MainGreeting = findViewById(R.id.MainGreeting);
        MainGreeting.setText("");

        FirebaseFirestore db = FirebaseFirestore.getInstance();


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
                                MainGreeting.setText("Welcome back "+ mAuth.getCurrentUser().getDisplayName() + "!");
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });




        AddSubjButton = findViewById(R.id.AddSubjButton);
        ViewSchButton = findViewById(R.id.ViewSchButton);
        LogOutButton = findViewById(R.id.LogOutButton);
        MyLessonsButton = findViewById(R.id.MyLessonsButton);
        ConstraintsButton = findViewById(R.id.ConstraintsButton);
        ChatButton = findViewById(R.id.ChatButton);

        /* Go to viewscheduleactivity */
        ViewSchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ViewScheduleActivity.class));
            }
        });
        /* Go to addsubjectactivity */
        AddSubjButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddCourseActivity.class));
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

    public void refreshConstraints(){
        CONSTRAINTS = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String username = user.getDisplayName();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        /* CONSTRAINTS */

        db.collection("prof_constraints").document(username).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot document) {
                for(Map.Entry<String, Object> e: document.getData().entrySet()){
                    HashMap<String, String> m = (HashMap<String, String>) e.getValue();
                    String day = e.getKey();
                    String duration = m.get("duration");
                    String startTime = m.get("startTime");
                    Constraint c = new Constraint(day, startTime, duration);
                    CONSTRAINTS.add(c);
                }
            }
        });
    }


    private void refreshLessons() {
        LESSONS = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String username = user.getDisplayName();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        /* CONSTRAINTS */

        db.collection("lessons").document(username).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot document) {
                for(Map.Entry<String, Object> e: document.getData().entrySet()){
                    HashMap<String, Object> m = (HashMap<String, Object>) e.getValue();
                    String id = e.getKey();
                    String subject = (String) m.get("subject");
                    String location = (String) m.get("location");
                    ArrayList cohorts = (ArrayList) m.get("cohorts");
                    ArrayList profs = getProfsFrom(m);
                    String duration = (String) m.get("duration");
                    Lesson l = new Lesson(subject,location,cohorts,profs,duration,id);
                    LESSONS.add(l);
                }
            }
        });
    }

    private ArrayList<String> getProfsFrom (HashMap m){
        ArrayList<String> out = new ArrayList<>();
        if(m.containsKey("shared")){
            out = (ArrayList<String>) m.get("shared");
        }
        else{
            out.add("myself");
        }
        return out;
    }
}
