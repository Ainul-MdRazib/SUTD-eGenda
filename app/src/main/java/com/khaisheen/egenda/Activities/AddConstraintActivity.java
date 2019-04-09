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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.khaisheen.egenda.Data.Constraint;
import com.khaisheen.egenda.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static com.khaisheen.egenda.Activities.MainActivity.CONSTRAINTS;

public class AddConstraintActivity extends Activity {

    Spinner daySpinner, startTimeSpinner, cDurationSpinner;
    Button SubmitButton, CancelButton;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    final HashMap<String,String> startTimeMap = new HashMap<String,String>(){{
        put("8:30","0");put("9:00","2");put("9:30","3");put("10:00","4");
        put("10:30","5");put("11:00","6");put("11:30","7");put("12:00","8");
        put("12:30","9");put("13:00","10");put("13:30","11");put("14:00","12");
        put("14:30","13");put("15:00","14");put("15:30","15");put("16:00","16");
        put("16:30","17");put("17:00","18");put("17:30","19");
    }};


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
        String day = daySpinner.getSelectedItem().toString(),
            startTime = formatStartTime(startTimeSpinner.getSelectedItem().toString()),
            duration = formatDuration(cDurationSpinner.getSelectedItem().toString());
        String username = mAuth.getCurrentUser().getDisplayName();

        Map<String,String> constraint = new HashMap<>();
        constraint.put("startTime", startTime);
        constraint.put("duration", duration);

        Constraint c = new Constraint(day, startTime, duration);
        CONSTRAINTS.add(c);

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
        return startTimeMap.get(inp);
    }


}
