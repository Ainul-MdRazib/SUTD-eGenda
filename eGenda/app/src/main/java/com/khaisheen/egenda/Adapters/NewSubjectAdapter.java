package com.khaisheen.egenda.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.khaisheen.egenda.Activities.AddSubjectActivity;
import com.khaisheen.egenda.Activities.CohortsActivity;
import com.khaisheen.egenda.Activities.ProfessorsActivity;
import com.khaisheen.egenda.R;

import java.util.Arrays;
import java.util.List;

// RECYCLERVIEW ADAPTER FOR AddSubjectActivity
// MOST OF THE SHIT HAPPENS HERE
// The RecyclerView has two different kinds of viewHolders, REGULAR and SPECIAL.

// First TWO are special holders. First one is the profs info and second one is the cohorts info

public class NewSubjectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    SharedPreferences preferences;
    String prefFile = "preferences";
    public static final String[] KEY_LIST = new String[]{"profs", "cohorts"};

    /* FOR SPECIAL HOLDER (FIRST TWO) */

    // title for the first two holders
    private String[] title = new String[]{"Professors:", "Cohorts:"};
    // default text for the first two holders
    // TODO but not really: the default prof name can be gotten from firestore after implementing auth stuff
    private String[] defaultText = new String[]{"PROFNAME", "None"};
    // Two OnClickListeners for the "EDIT" button for prof and cohort info respectively
    private View.OnClickListener[] onClickListeners = new View.OnClickListener[]{
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    context.startActivity(new Intent(context,ProfessorsActivity.class));
                }},
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    context.startActivity(new Intent(context,CohortsActivity.class));
                }
            }};

    /* FOR REGULAR HOLDER */

    // the names of the constraints
    private String[] constraints = new String[]{"Subject", "Duration", "Location"};

    // TODO but not really: maybe these constraints could be gotten from firestore instead of being hardcoded here
    // not too impt now.

    // constraints
    private String[] subjects = new String[]{"-", "50.003 Elements of Software Construction", "50.005 Computer Systems Engineering", "50.034 PnS"};
    private String[] durations = new String[]{"-", "1", "1.5", "2", "2.5", "3"};
    private String[] locations = new String[]{"-", "Lecture", "Lab", "Classroom"};

    // Spinner values for three spinners ( each set of spinner values is a list of strings )
    private String[][] values = new String[][]{subjects, durations, locations};


    @Override
    // This method allows us to differentiate the SPECIAL and REGULAR viewHolders
    public int getItemViewType(int position) {
        switch(position){
            // For the viewHolders at position 0 and 1 (first two), we set their viewType to be '1'. (SPECIAL)
            case 0: case 1: return 1;
            // For the rest, we set their viewType as '0'. (REGULAR)
            default: return 0;
            // This returned value is passed to OnCreateViewHolder
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            // if viewType is '1' (SPECIAL):
            case 1: return new SpecialViewHolder(
                    LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.special_card, parent, false));
            /// else if viewType is not '1' (REGULAR):
            default: return new NewSubjectViewHolder(
                    LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.new_subject_card, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()){
            // if viewType is 1 (SPECIAL):
            case 1: SpecialViewHolder holder1 = (SpecialViewHolder) holder;
                // set the titles and onclicklisteners according to position
                holder1.specialTitle.setText(title[position]);
                holder1.textViewEdit.setOnClickListener(onClickListeners[position]);
                // the textview with the list of profs/cohorts
                TextView tvList = holder1.textViewList;
                // get the saved data from preferences and set them accordingly
                Activity a = (Activity) tvList.getContext();
                preferences = a.getSharedPreferences(prefFile, Context.MODE_PRIVATE);
                holder1.textViewList.setText(preferences.getString(KEY_LIST[position], defaultText[position]));
            break;
            // if viewType is 0 (REGULAR):
            case 0: NewSubjectViewHolder holder0 = (NewSubjectViewHolder) holder;

            // setting the different views' accordingly
            // title.length = number of titles for the special views = number of special views = 2.

            // setting the names of the constraints
            holder0.cardTextView.setText(constraints[position - title.length]);

            // setting the spinners' data from list of strings
            List<String> spinnerValue = Arrays.asList(values[position - title.length]);
            ArrayAdapter <String> adapter = new ArrayAdapter<>(holder0.cardSpinner.getContext(), android.R.layout.simple_spinner_item, spinnerValue);
            holder0.cardSpinner.setAdapter(adapter);
            break;

//            TODO: PUT THE spinner values (constraints) to preferences so that we can get it in AddSubjectActivity
//            IF u have another way to do so plz feel free! dont be restricted by this.
//            else THE FOLLOWING setOnItemSelectedListener method may help I AM NOT TOO SURE
//
//
//                holder0.cardSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                @Override
//                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//
//                }
//
//                @Override
//                public void onNothingSelected(AdapterView<?> adapterView) {
//
//                }
//            });
        }
    }

    @Override
    public int getItemCount() {
        // constraints.length = number of regular holders, title.length = number of special holders
        return constraints.length + title.length;
    }

    // REGULAR holder
    class NewSubjectViewHolder extends RecyclerView.ViewHolder{
        TextView cardTextView;
        Spinner cardSpinner;

        public NewSubjectViewHolder(View view){
            super(view);
            cardTextView = view.findViewById(R.id.cardTextView);
            cardSpinner = view.findViewById(R.id.cardSpinner);

        }
    }

    // SPECIAL holder
    class SpecialViewHolder extends RecyclerView.ViewHolder{
        TextView specialTitle;
        TextView textViewList;
        TextView textViewEdit;
        SharedPreferences preferences;


        public SpecialViewHolder(View view){
            super(view);
            specialTitle = view.findViewById(R.id.specialTitle);
            textViewList = view.findViewById(R.id.textViewList);
            textViewEdit = view.findViewById(R.id.textViewEdit);
            preferences = view.getContext().getSharedPreferences(prefFile,Context.MODE_PRIVATE);
        }
    }
}
