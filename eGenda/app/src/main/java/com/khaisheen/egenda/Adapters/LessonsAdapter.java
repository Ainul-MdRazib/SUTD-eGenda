package com.khaisheen.egenda.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.khaisheen.egenda.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class LessonsAdapter extends RecyclerView.Adapter {

    /* DUMMY DATA */
    ArrayList<String> dummyCohorts = new ArrayList<>(Arrays.asList("istdt5c1", "istdt5c2", "istdt5c3"));
    ArrayList<String> dummyProfs = new ArrayList<>(Arrays.asList("David", "Natalie"));
    ArrayList<Lesson> lessons = new ArrayList<Lesson>(Arrays.asList(
            new Lesson("CSE", "lt", dummyCohorts, dummyProfs, 4),
            new Lesson("CSE", "lt", dummyCohorts, dummyProfs, 4),
            new Lesson("CSE", "lt", dummyCohorts, dummyProfs, 4),
            new Lesson("CSE", "lt", dummyCohorts, dummyProfs, 4),
            new Lesson("CSE", "lt", dummyCohorts, dummyProfs, 4)
    ));
    // TODO: make a new 'lessons' arraylist by pulling lessons data from firebase.

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new LessonsViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.lesson_card, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        LessonsViewHolder holder = (LessonsViewHolder) viewHolder;
        holder.tvLesson.setText("Lesson " + String.valueOf(i+1));
        Lesson l = lessons.get(i);
        holder.tvSubjectName.setText(l.subject);
        holder.tvCohortIds.setText(l.cohorts.toString());
        holder.tvDurationValue.setText(String.valueOf(l.duration));
        holder.tvLocation.setText(l.location);
        holder.tvProfs.setText(l.profs.toString());
    }

    @Override
    public int getItemCount() {
        return lessons.size();
    }

    class LessonsViewHolder extends RecyclerView.ViewHolder{
        CardView card;
        TextView tvSubjectName, tvCohortIds, tvDurationValue, tvLocation, tvLesson, tvProfs;
        Button btnDelete;

        public LessonsViewHolder(@NonNull View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.lesson_card);
            tvSubjectName = itemView.findViewById(R.id.tvSubjectName);
            tvCohortIds = itemView.findViewById(R.id.tvCohortIds);
            tvDurationValue = itemView.findViewById(R.id.tvDurationValue);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvLesson = itemView.findViewById(R.id.tvLesson);
            tvProfs = itemView.findViewById(R.id.tvProfs);
            btnDelete = itemView.findViewById(R.id.btnDelete);

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: get lesson id and remove the entry from firebase
                    lessons.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    notifyItemRangeChanged(0, lessons.size());
                }
            });
        }
    }

    class Lesson{
        String subject, location;
        ArrayList<String> cohorts, profs;
        int duration, id;

        public Lesson(String subject, String location, ArrayList<String> cohorts, ArrayList<String> profs, int duration) {
            Random r = new Random();
            id = r.nextInt(10000000);
            this.subject = subject;
            this.location = location;
            this.cohorts = cohorts;
            this.profs = profs;
            this.duration = duration;
        }
    }
}
