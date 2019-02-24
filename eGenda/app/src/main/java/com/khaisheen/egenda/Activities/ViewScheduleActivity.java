package com.khaisheen.egenda.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.khaisheen.egenda.R;

public class ViewScheduleActivity extends AppCompatActivity {

    Button buttonBack;
    Button buttonDownload;
    ImageView imageViewOfSchedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_schedule);
        // Views
        buttonBack = findViewById(R.id.buttonBack);
        // TODO something with imageview and download button (NOT SO SOON)
        imageViewOfSchedule = findViewById(R.id.imageViewOfSchedule);
        buttonDownload = findViewById(R.id.buttonDownload);
        // Go to MainActivity with back button.
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ViewScheduleActivity.this, MainActivity.class));
            }
        });
    }
}
