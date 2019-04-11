package com.khaisheen.egenda.Activities;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.khaisheen.egenda.Data.AddedConstraints;
import com.khaisheen.egenda.Data.Constraint;
import com.khaisheen.egenda.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static com.khaisheen.egenda.Activities.MainActivity.CONSTRAINTS;
import static com.khaisheen.egenda.Data.Constraint.START_TIME_MAP;

public class AddConstraintActivity extends Activity {

    Spinner daySpinner, startTimeSpinner, cDurationSpinner;
    Button SubmitButton, CancelButton;
    FirebaseFirestore db;
    FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_constraint);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

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
                submitConstraints();
            }
        });

        CancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void submitConstraints(){
        String tempDay = daySpinner.getSelectedItem().toString(),
                tempStartTime = startTimeSpinner.getSelectedItem().toString(),
                tempDuration = cDurationSpinner.getSelectedItem().toString();
        String day = tempDay,
                startTime = formatStartTime(tempStartTime),
                duration = formatDuration(tempDuration);
        String username = mAuth.getCurrentUser().getDisplayName();

        Map<String,String> constraint = new HashMap<>();
        constraint.put("startTime", startTime);
        constraint.put("duration", duration);

        Constraint c = new Constraint(tempDay, tempStartTime, tempDuration);
        AddedConstraints.getInstance().add(c);


        Map<String, Object> docData = new HashMap<>();
        docData.put(day,constraint);
        db.collection("prof_constraints").document(username)
                .set(docData,SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AddConstraintActivity.this,"Constraint successfully added", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddConstraintActivity.this,"Constraint not added", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public String formatDuration(String inp) {
        double value = Double.valueOf(inp);
        return String.valueOf(value*2);
    };

    public String formatStartTime(String inp){
        return START_TIME_MAP.get(inp);
    }

}
