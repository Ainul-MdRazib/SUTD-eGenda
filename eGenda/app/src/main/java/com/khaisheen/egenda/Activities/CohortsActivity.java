package com.khaisheen.egenda.Activities;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.khaisheen.egenda.R;

public class CohortsActivity extends Activity {

    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cohorts);
        linearLayout = findViewById(R.id.linearcohorts);
    }

    public void onAdd(View v){
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View card = inflater.inflate(R.layout.cohorts_card, null);
        linearLayout.addView(card, linearLayout.getChildCount() -1);
    }

    public void onDelete(View v){
        linearLayout.removeView((View) v.getParent().getParent());
    }
}
