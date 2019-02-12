package com.khaisheen.egenda.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.khaisheen.egenda.Adapters.NewSubjectAdapter;
import com.khaisheen.egenda.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AddSubjectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject);
        RecyclerView rvAddSubject = findViewById(R.id.rv_addsubject);
        rvAddSubject.setLayoutManager(new LinearLayoutManager(this));
        rvAddSubject.setAdapter(new NewSubjectAdapter());

//        Spinner spinnerSubjectCode = findViewById(R.id.spinnerSubjectCode);
//        Spinner spinnerLessonOne = findViewById(R.id.spinnerLessonOne);
//        Spinner spinnerLessonTwo = findViewById(R.id.spinnerLessonTwo);
//        Spinner spinnerLessonThree = findViewById(R.id.spinnerLessonThree);
//
//        List<String> subjectCodes = new ArrayList<>();
//        subjectCodes.add("-");
//        subjectCodes.add("50.005");
//        subjectCodes.add("50.003");
//        Collections.sort(subjectCodes);
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, subjectCodes);
//        spinnerSubjectCode.setAdapter(adapter);
//
//        List<String> durations = new ArrayList<>();
//        durations.add("-");
//        durations.add("1.5");
//        durations.add("2.0");
//        durations.add("2.5");
//        durations.add("3.0");
//        ArrayAdapter<String> durationsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, durations);
//        spinnerLessonOne.setAdapter(durationsAdapter);
//        spinnerLessonTwo.setAdapter(durationsAdapter);
//        spinnerLessonThree.setAdapter(durationsAdapter);
    }

}
