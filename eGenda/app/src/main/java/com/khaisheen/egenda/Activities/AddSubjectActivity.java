package com.khaisheen.egenda.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.khaisheen.egenda.Adapters.NewSubjectAdapter;
import com.khaisheen.egenda.R;

public class AddSubjectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject);
        RecyclerView rvAddSubject = findViewById(R.id.rv_addsubject);
        rvAddSubject.setLayoutManager(new LinearLayoutManager(this));
        rvAddSubject.setAdapter(new NewSubjectAdapter());

        Button buttonAdd = findViewById(R.id.buttonAddCohorts);
        Button buttonCancel = findViewById(R.id.buttonCancel);

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddSubjectActivity.this, MainActivity.class));
            }
        });
    }

    public void editProfs(){
        startActivity(new Intent(AddSubjectActivity.this,ProfessorsActivity.class));
    }

}
