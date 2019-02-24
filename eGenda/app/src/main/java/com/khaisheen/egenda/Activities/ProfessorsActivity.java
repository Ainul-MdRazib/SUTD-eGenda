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
import android.widget.TextView;

import com.khaisheen.egenda.R;

import java.util.ArrayList;

// Page to input professors in charge of the class
// VERY SIMILAR TO CohortsActivity
// just that the FIRST card is special

// Spinners' data are set in the .xml file

public class ProfessorsActivity extends Activity {

    LinearLayout linearLayout;
    SharedPreferences preferences;
    String prefFile = "preferences";
    ArrayList<String> profList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professors);
        // linear layout
        linearLayout = findViewById(R.id.linearprofs);
    }

    // .xml = professors_card.xml
    /* OnClickListeners for 'add', 'delete' and 'apply' buttons in .xml */

    // On clicking "ADD" button, add a new card with layout from .xml
    public void onAdd(View v){
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View card = inflater.inflate(R.layout.professors_card, null);
        linearLayout.addView(card, linearLayout.getChildCount() - 1);
    }

    // On clicking "Delete" button, remove last card with layout from .xml
    public void onDelete(View v){
        linearLayout.removeView((View) v.getParent().getParent());
    }

    // On clicking "apply" button, save prof names to preferences and go back to AddSubjectActivity
    public void onApply(View v){
        int profCount = linearLayout.getChildCount() - 1;
        for(int i=0;i<profCount;i++) {
            CardView card = (CardView) linearLayout.getChildAt(i);
            // if card is the very first card, with default prof name (depending on user)
            if(i == 0){
                // get default prof name from textview and add it to profList
                TextView tv = card.findViewById(R.id.defaultProf);
                String profName = tv.getText().toString();
                profList.add(profName);
            }
            // for the rest of the cards
            else {
                // get the spinner
                ConstraintLayout cl = (ConstraintLayout) card.getChildAt(0);
                Spinner sp = cl.findViewById(R.id.spinner);
                if (sp != null) {
                    // get the prof name from selected item in spinner and add it to profList
                    String profName = sp.getSelectedItem().toString();
                    profList.add(profName);
                }
            }
        }
        // save profList to preferences
        preferences = getSharedPreferences(prefFile,MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("profs",profList.toString());
        editor.apply();

        // start AddSubjectActivity
        Intent intent = new Intent(ProfessorsActivity.this, AddSubjectActivity.class);
        startActivity(intent);
    }
}
