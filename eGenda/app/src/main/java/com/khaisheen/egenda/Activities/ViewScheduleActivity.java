package com.khaisheen.egenda.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.khaisheen.egenda.R;

public class ViewScheduleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_schedule);

        Button buttonBack = findViewById(R.id.buttonBack);
        Button buttonDownload = findViewById(R.id.buttonDownload);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ViewScheduleActivity.this, MainActivity.class));
            }
        });
    }
}
