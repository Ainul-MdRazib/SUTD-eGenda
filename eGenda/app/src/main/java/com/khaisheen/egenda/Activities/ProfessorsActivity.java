package com.khaisheen.egenda.Activities;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.khaisheen.egenda.R;

public class ProfessorsActivity extends Activity {

    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professors);
        linearLayout = findViewById(R.id.linearprofs);
    }

    public void onAdd(View v){
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View card = inflater.inflate(R.layout.professors_card, null);
        linearLayout.addView(card, linearLayout.getChildCount() -1);
    }

    public void onDelete(View v){
        linearLayout.removeView((View) v.getParent().getParent());
    }
}
