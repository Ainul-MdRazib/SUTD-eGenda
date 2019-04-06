package com.khaisheen.egenda.Activities;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.khaisheen.egenda.R;

public class AddConstraintActivity extends Activity {

    Spinner daySpinner, startTimeSpinner, cDurationSpinner;
    Button SubmitButton, CancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_constraint);

        daySpinner = findViewById(R.id.daySpinner);
        ArrayAdapter<CharSequence> dayAdapter = ArrayAdapter.createFromResource(this,
                R.array.constraint_day, android.R.layout.simple_spinner_item);
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(dayAdapter);
        startTimeSpinner = findViewById(R.id.startTimeSpinner);
        ArrayAdapter<CharSequence> startTimeAdapter = ArrayAdapter.createFromResource(this,
                R.array.constraint_start_time, android.R.layout.simple_spinner_item);
        startTimeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        startTimeSpinner.setAdapter(startTimeAdapter);
        cDurationSpinner = findViewById(R.id.cDurationSpinner);
        ArrayAdapter<CharSequence> cDurationAdapter = ArrayAdapter.createFromResource(this,
                R.array.constraint_duration, android.R.layout.simple_spinner_item);
        cDurationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cDurationSpinner.setAdapter(cDurationAdapter);

        SubmitButton = findViewById(R.id.SubmitButton);
        CancelButton = findViewById(R.id.CancelButton);

        SubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddConstraintActivity.this,"pls fill properly thx", Toast.LENGTH_SHORT).show();
                //TODO: push to firestore stuff
                return;
            }
        });
        CancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
