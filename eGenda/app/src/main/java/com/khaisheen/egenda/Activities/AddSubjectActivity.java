package com.khaisheen.egenda.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

        Button buttonAdd = findViewById(R.id.buttonAdd);
        Button buttonCancel = findViewById(R.id.buttonCancel);

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddSubjectActivity.this, MainActivity.class));
            }
        });
    }

}
