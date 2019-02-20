package com.khaisheen.egenda.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.khaisheen.egenda.Activities.AddSubjectActivity;
import com.khaisheen.egenda.Activities.CohortsActivity;
import com.khaisheen.egenda.Activities.ProfessorsActivity;
import com.khaisheen.egenda.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NewSubjectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    /* FOR REGULAR HOLDER */

    private String[] constraints = new String[]{"Subject", "Duration", "Location"};

//    private String[] terms = new String[]{"-", "5", "7"};
//    private String[] pillars = new String[]{"-", "ASD", "EPD", "ESD", "ISTD", "Freshmore"};
//    private String[] cohorts = new String[]{"-", "istdt5c1", "istdt5c2", "istdt5c3", "esdt5c1", "esdt5c2"};
    private String[] subjects = new String[]{"-", "50.003 Elements of Software Construction",
            "50.005 Computer Systems Engineering", "50.034 PnS"};
//    private String[] lessons = new String[]{"-", "1", "2", "3"};
    private String[] durations = new String[]{"-", "1", "1.5", "2", "2.5", "3"};
    private String[] locations = new String[]{"-", "Lecture", "Lab", "Classroom"};

    private String[][] values = new String[][]{subjects, durations, locations};

    /* FOR SPECIAL HOLDER (FIRST TWO) */
    private String[] title = new String[]{"Professors:", "Cohorts:"};
    private String[] listForTextView = new String[]{"Natalie", "none"};
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

    @Override
    public int getItemViewType(int position) {
        switch(position){
            case 0: case 1: return 1;
            default: return 0;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case 1: return new SpecialViewHolder(
                    LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.special_card, parent, false));
            default: return new NewSubjectViewHolder(
                    LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.new_subject_card, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()){
            case 0: NewSubjectViewHolder holder0 = (NewSubjectViewHolder) holder;
            holder0.cardTextView.setText(constraints[position - title.length]);
            List<String> spinnerValue = Arrays.asList(values[position - title.length]);
            ArrayAdapter <String> adapter = new ArrayAdapter<>(holder0.cardSpinner.getContext(), android.R.layout.simple_spinner_item, spinnerValue);
            holder0.cardSpinner.setAdapter(adapter);
            break;

            case 1: SpecialViewHolder holder1 = (SpecialViewHolder) holder;
            holder1.specialTitle.setText(title[position]);
            holder1.textViewList.setText(listForTextView[position]);
            holder1.textViewEdit.setOnClickListener(onClickListeners[position]);

        }



    }

    @Override
    public int getItemCount() {
        return constraints.length + title.length;
    }

    class NewSubjectViewHolder extends RecyclerView.ViewHolder{
        TextView cardTextView;
        Spinner cardSpinner;

        public NewSubjectViewHolder(View view){
            super(view);
            cardTextView = view.findViewById(R.id.cardTextView);
            cardSpinner = view.findViewById(R.id.cardSpinner);

        }
    }

    class SpecialViewHolder extends RecyclerView.ViewHolder{
        TextView specialTitle;
        TextView textViewList;
        TextView textViewEdit;


        public SpecialViewHolder(View view){
            super(view);
            specialTitle = view.findViewById(R.id.specialTitle);
            textViewList = view.findViewById(R.id.textViewList);
            textViewEdit = view.findViewById(R.id.textViewEdit);
        }
    }
}
