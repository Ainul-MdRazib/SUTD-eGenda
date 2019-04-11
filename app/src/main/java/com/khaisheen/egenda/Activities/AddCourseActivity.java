package com.khaisheen.egenda.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.khaisheen.egenda.Data.AddedLessons;
import com.khaisheen.egenda.Data.Constraint;
import com.khaisheen.egenda.Data.Lesson;
import com.khaisheen.egenda.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class AddCourseActivity extends AppCompatActivity{
    final String alnumCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    private Button SubmitButton;
    private Button CancelButton;

    private Button CohortPick;
    private Button ProfPick;

    String[] profList;
    boolean[] checkedProfs;
    ArrayList<Integer>mProfs = new ArrayList<>();

    String[] cohortList;
    boolean[]checkedCohorts;
    ArrayList<Integer>mCohorts = new ArrayList<>();

    ProgressBar mProgress;
    int progress = 0;

    private String TAG ="101";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        mProgress = findViewById(R.id.simpleProgressBar);

        profList = getResources().getStringArray(R.array.istd_profs);
        checkedProfs = new boolean[profList.length];

        ProfPick = findViewById(R.id.ProfPick);
        ProfPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder ProfBuilder = new AlertDialog.Builder(AddCourseActivity.this);
                ProfBuilder.setTitle("ISTD Professors");
                ProfBuilder.setMultiChoiceItems(profList, checkedProfs, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                        if(isChecked){
                            if(!mProfs.contains(position)){
                                mProfs.add(position);
                            }else{
                                mProfs.remove(position);
                            }
                        }
                    }
                });

                ProfBuilder.setCancelable(false);
                ProfBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        String prof = "";
                        for (int i = 0;i < mProfs.size(); i++){
                            prof = prof + profList[mProfs.get(i)];
                            if(i != mProfs.size() - 1){
                                prof = prof + ", ";
                            }
                        }
                        ProfPick.setText(prof);

                    }
                });

                ProfBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                ProfBuilder.setNeutralButton("Clear all", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        for(int i = 0; i< checkedProfs.length; i++){
                            checkedProfs[i]=false;
                            mProfs.clear();
                            ProfPick.setText("ASSIGN PROFESSORS");
                        }
                    }
                });

                AlertDialog mDialog = ProfBuilder.create();
                mDialog.show();
            }
        });

        cohortList = getResources().getStringArray(R.array.term5cohorts);
        checkedCohorts = new boolean[profList.length];

        CohortPick = findViewById(R.id.CohortPick);
        CohortPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder CohortBuilder = new AlertDialog.Builder(AddCourseActivity.this);
                CohortBuilder.setTitle("2019 Term 5 Cohorts");
                CohortBuilder.setMultiChoiceItems(cohortList, checkedCohorts, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                        if(isChecked){
                            if(!mCohorts.contains(position)){
                                mCohorts.add(position);
                            }else{
                                mCohorts.remove(position);
                            }
                        }
                    }
                });

                CohortBuilder.setCancelable(false);
                CohortBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        String cohort = "";
                        for (int i = 0;i < mCohorts.size(); i++){
                            cohort = cohort + cohortList[mCohorts.get(i)];
                            if(i != mCohorts.size() - 1){
                                cohort = cohort + ", ";
                            }
                        }
                        CohortPick.setText(cohort);
                    }
                });

                CohortBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                CohortBuilder.setNeutralButton("Clear all", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        for(int i = 0; i< checkedCohorts.length; i++){
                            checkedCohorts[i]=false;
                            mCohorts.clear();
                            CohortPick.setText("ASSIGN COHORTS");
                        }
                    }
                });

                AlertDialog mDialog = CohortBuilder.create();
                mDialog.show();
            }
        });

        final Spinner SubjectSpinner = (Spinner) findViewById(R.id.SubjectSpinner);
        ArrayAdapter<CharSequence> SubjectAdapter = ArrayAdapter.createFromResource(this,
                R.array.subject_pick, android.R.layout.simple_spinner_item);
        SubjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SubjectSpinner.setAdapter(SubjectAdapter);

        final Spinner DurationSpinner = (Spinner) findViewById(R.id.DurationSpinner);
        ArrayAdapter<CharSequence> DurationAdapter = ArrayAdapter.createFromResource(this,
                R.array.duration_pick, android.R.layout.simple_spinner_item);
        DurationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        DurationSpinner.setAdapter(DurationAdapter);

        final Spinner VenueSpinner = (Spinner) findViewById(R.id.VenueSpinner);
        ArrayAdapter<CharSequence> VenueAdapter = ArrayAdapter.createFromResource(this,
                R.array.venue_pick, android.R.layout.simple_spinner_item);
        VenueAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        VenueSpinner.setAdapter(VenueAdapter);



        SubmitButton = findViewById(R.id.SubmitButton);
        SubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mSubject = SubjectSpinner.getSelectedItem().toString();
                String mDuration = DurationSpinner.getSelectedItem().toString();
                String mVenue = VenueSpinner.getSelectedItem().toString();
                String[] mProfList = ProfPick.getText().toString().split(", ");
                String[] mCohortList = CohortPick.getText().toString().split(", ");
//                String id = generateId();

                boolean cohortEmpty = mCohortList[0].equalsIgnoreCase("Assign Cohorts");
                boolean profsEmpty = mProfList[0].equalsIgnoreCase("Assign Professors");
                if(mSubject.equals("")||mDuration.equals("")||mVenue.equals("")||cohortEmpty||profsEmpty){
                    Toast.makeText(AddCourseActivity.this, "Please fill out all sections.", Toast.LENGTH_LONG).show();
                }else{

                    setProgressValue(progress);

                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    Lesson l = new Lesson(mSubject,mVenue,new ArrayList<>(Arrays.asList(mCohortList)),new ArrayList<>(Arrays.asList(mProfList)),mDuration);
                    Map<String, Object> newCourse = new HashMap<>();
                    newCourse.put("duration", l.getDuration());
                    newCourse.put("location", l.getLocation());

                    putProfs(newCourse,mProfList);

                    newCourse.put("cohorts", new ArrayList<>(l.getCohorts()));
                    newCourse.put("subject", l.getSubject());

                    Map<String, Object> docData = new HashMap<>();
                    docData.put(l.getId(),newCourse);

                    AddedLessons.getInstance().addLesson(l);

                    String username = mAuth.getCurrentUser().getDisplayName();
                    db.collection("lessons").document(username)
                            .set(docData, SetOptions.merge())
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "DocumentSnapshot successfully written!");
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error writing document", e);
                                    Toast.makeText(AddCourseActivity.this,"Error! Course not added", Toast.LENGTH_LONG).show();
                                }
                            });

                }


            }
        });

        CancelButton = findViewById(R.id.CancelButton);
        CancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    private void setProgressValue(final int progress) {
        // set the progress
        mProgress.setProgress(progress);
        // thread is used to change the progress value
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                setProgressValue(progress + 10);
            }
        });
        thread.start();
    }

    private String generateId(){
        Random r = new Random();
        String out = "";
        for(int i=0; i<8; i++){
            out += alnumCharacters.charAt(r.nextInt(alnumCharacters.length()));
        }
        return out;
    }

    private void putProfs(Map<String,Object> newCourse, String[] mProfList){
        ArrayList<String> tempProfList = new ArrayList<>(Arrays.asList(mProfList));
        if(tempProfList.size() > 1){
            newCourse.put("shared",tempProfList);
        }
    }

}
