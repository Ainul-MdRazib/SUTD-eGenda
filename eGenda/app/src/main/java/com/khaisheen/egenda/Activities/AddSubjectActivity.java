package com.khaisheen.egenda.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.khaisheen.egenda.Adapters.NewSubjectAdapter;
import com.khaisheen.egenda.R;

// Should have named it AddClassActivity.................. Z/lkxcjasdasdlk
// Form for profs to input constraints

public class AddSubjectActivity extends AppCompatActivity {

    SharedPreferences preferences;
    String prefFile = "preferences";
    RecyclerView rvAddSubject;
    Button buttonCancel;
    Button buttonAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject);
        /* RecyclerView stuff */
        rvAddSubject = findViewById(R.id.rv_addsubject);
        rvAddSubject.setLayoutManager(new LinearLayoutManager(this));
        rvAddSubject.setAdapter(new NewSubjectAdapter());
        // buttons
        buttonAdd = findViewById(R.id.buttonAddCohorts);
        buttonCancel = findViewById(R.id.buttonCancel);

        // Go to MainActivity with Add button and save some stuff
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* TODO: SAVE INFO TO FIRESTORE ( probably use sharedpreferences in NewSubjectAdapter to get the values )*/
                // BECAUSE i think it is probably impossible to get them from out here. Cannot simply findviewbyid
                // cos 1. all the recyclerview's ViewHolder's items have same ids
                // 2. adapter has no findviewbyid method anyway
                // IF u have another way to do so plz feel free! dont be restricted by this

                // "Forget" sharedpreferences
                preferences = getSharedPreferences(prefFile, MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                // go to MainActivity
                startActivity(new Intent(AddSubjectActivity.this, MainActivity.class));
            }
        });


        // Go to MainActivity with cancel button
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // "Forget" sharedpreferences
                preferences = getSharedPreferences(prefFile, MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                // go to MainActivity
                startActivity(new Intent(AddSubjectActivity.this, MainActivity.class));
            }
        });
    }
}
