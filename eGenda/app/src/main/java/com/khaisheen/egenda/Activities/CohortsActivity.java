package com.khaisheen.egenda.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.constraint.ConstraintLayout;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.khaisheen.egenda.R;

import java.util.ArrayList;

// VERY SIMILAR TO ProfessorsActivity
// just that there is no special first card

// Spinners' data are set in the .xml file

public class CohortsActivity extends Activity {

    LinearLayout linearLayout;
    SharedPreferences preferences;
    String prefFile = "preferences";
    ArrayList<String> cohortList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cohorts);
        // linear layout
        linearLayout = findViewById(R.id.linearcohorts);
    }

    // .xml = cohorts_card.xml
    /* OnClickListeners for 'add', 'delete' and 'apply' buttons in .xml */

    // On clicking "ADD" button, add a new card with layout from .xml
    public void onAdd(View v){
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View card = inflater.inflate(R.layout.cohorts_card, null);
        linearLayout.addView(card, linearLayout.getChildCount() -1);
    }

    // On clicking "Delete" button, remove last card with layout from .xml
    public void onDelete(View v){
        linearLayout.removeView((View) v.getParent().getParent());
    }

    // On clicking "apply" button, save prof names to preferences and go back to AddSubjectActivity
    public void onApply(View v){
        int cohortCount = linearLayout.getChildCount() - 1;
        for(int i=0;i<cohortCount;i++) {
            // get the spinner
            CardView card = (CardView) linearLayout.getChildAt(i);
            ConstraintLayout cl = (ConstraintLayout) card.getChildAt(0);
            Spinner sp = cl.findViewById(R.id.spinner);
            if (sp != null) {
                // get cohort name from the selected item in spinner and add it to cohortList
                String cohort = sp.getSelectedItem().toString();
                cohortList.add(cohort);
            }
        }
        // save cohortList to preferences
        preferences = getSharedPreferences(prefFile,MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("cohorts",cohortList.toString());
        editor.apply();

        // start AddSubjectActivity
        Intent intent = new Intent(CohortsActivity.this, AddSubjectActivity.class);
        startActivity(intent);
    }
}
