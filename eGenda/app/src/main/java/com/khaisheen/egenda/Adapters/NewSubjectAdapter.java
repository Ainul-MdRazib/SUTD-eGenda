package com.khaisheen.egenda.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.khaisheen.egenda.Activities.AddSubjectActivity;
import com.khaisheen.egenda.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NewSubjectAdapter extends RecyclerView.Adapter<NewSubjectAdapter.NewSubjectViewHolder> {

    private String[] constraints = new String[]{"Term #", "Pillar", "Cohort #", "Subject", "Lesson #", "Duration", "Location"};

    private String[] terms = new String[]{"-", "5", "7"};
    private String[] pillars = new String[]{"-", "ASD", "EPD", "ESD", "ISTD", "Freshmore"};
    private String[] cohorts = new String[]{"-", "ALL", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    private String[] subjects = new String[]{"-", "50.003 Elements of Software Construction",
            "50.005 Computer Systems Engineering", "50.034 PnS"};
    private String[] lessons = new String[]{"-", "1", "2", "3"};
    private String[] durations = new String[]{"-", "1", "1.5", "2", "2.5", "3"};
    private String[] locations = new String[]{"-", "Lecture", "Lab", "Classroom"};

    private String[][] values = new String[][]{terms, pillars, cohorts, subjects, lessons, durations, locations};

    @NonNull
    @Override
    public NewSubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewSubjectViewHolder(
                LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.new_subject_card, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull NewSubjectViewHolder holder, int position) {
        holder.cardTextView.setText(constraints[position]);
        List<String> spinnerValue = Arrays.asList(values[position]);
        ArrayAdapter <String> adapter = new ArrayAdapter<>(holder.cardSpinner.getContext(), android.R.layout.simple_spinner_item, spinnerValue);
        holder.cardSpinner.setAdapter(adapter);

    }

    @Override
    public int getItemCount() {
        return constraints.length;
    }

    public class NewSubjectViewHolder extends RecyclerView.ViewHolder{
        TextView cardTextView;
        Spinner cardSpinner;

        public NewSubjectViewHolder(View view){
            super(view);
            cardTextView = view.findViewById(R.id.cardTextView);
            cardSpinner = view.findViewById(R.id.cardSpinner);

        }
    }
}
