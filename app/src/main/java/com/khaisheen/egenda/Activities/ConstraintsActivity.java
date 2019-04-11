package com.khaisheen.egenda.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.khaisheen.egenda.Adapters.ConstraintsAdapter;
import com.khaisheen.egenda.Data.AddedConstraints;
import com.khaisheen.egenda.R;

public class ConstraintsActivity extends Activity {

    RecyclerView rvConstraints;
    Button btnAddConstraints, btnBackConstraints;

    @Override
    protected void onResume() {
        super.onResume();
        rvConstraints.setAdapter(new ConstraintsAdapter(AddedConstraints.getInstance()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constraints);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        rvConstraints = findViewById(R.id.rvConstraints);
        rvConstraints.setLayoutManager(new LinearLayoutManager(this));

        btnAddConstraints = findViewById(R.id.btnAddConstraints);
        btnBackConstraints = findViewById(R.id.btnBackConstraints);

        btnAddConstraints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ConstraintsActivity.this, AddConstraintActivity.class));
            }
        });

        btnBackConstraints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


}
