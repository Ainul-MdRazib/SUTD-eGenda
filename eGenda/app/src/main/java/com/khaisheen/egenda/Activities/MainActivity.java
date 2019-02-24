package com.khaisheen.egenda.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.khaisheen.egenda.R;

// Main page
public class MainActivity extends AppCompatActivity {
    Button buttonViewSchedule;
    Button buttonAddSubject;
    Button buttonLogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonAddSubject = findViewById(R.id.buttonAddSubject);
        buttonViewSchedule = findViewById(R.id.buttonViewSchedule);
        buttonLogOut = findViewById(R.id.buttonLogOut);
        /* Go to viewscheduleactivity */
        buttonViewSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ViewScheduleActivity.class));
            }
        });
        /* Go to addsubjectactivity */
        buttonAddSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddSubjectActivity.class));
            }
        });
        /* LOG OUT */
        /* TODO: AUTH STUFF */
        buttonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
    }
}
