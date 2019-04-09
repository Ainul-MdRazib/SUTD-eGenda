package com.khaisheen.egenda.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.khaisheen.egenda.Adapters.LessonsAdapter;
import com.khaisheen.egenda.R;

import static com.khaisheen.egenda.Activities.MainActivity.LESSONS;

public class LessonsActivity extends Activity {

    RecyclerView rvClasses;
    Button AddLessonButton;
    Button BackButton;

    @Override
    protected void onResume() {
        super.onResume();
        rvClasses.setAdapter(new LessonsAdapter(LESSONS));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessons);
        rvClasses = findViewById(R.id.rvClasses);
        rvClasses.setLayoutManager(new LinearLayoutManager(this));

        AddLessonButton = findViewById(R.id.AddLessonButton);
        BackButton = findViewById(R.id.LessonsButtonBack);

        AddLessonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LessonsActivity.this, AddCourseActivity.class));
            }
        });

        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
