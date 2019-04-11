package com.khaisheen.egenda.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.khaisheen.egenda.Data.AddedLessons;
import com.khaisheen.egenda.Data.Lesson;
import com.khaisheen.egenda.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class LessonsAdapter extends RecyclerView.Adapter {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    AddedLessons lessons;
    /* DUMMY DATA */
    ArrayList<String> dummyCohorts = new ArrayList<>(Arrays.asList("istdt5c1", "istdt5c2", "istdt5c3"));
    ArrayList<String> dummyProfs = new ArrayList<>(Arrays.asList("David", "Natalie"));
//    ArrayList<Lesson> lessons = new ArrayList<Lesson>(Arrays.asList(
//            new Lesson("CSE", "lt", dummyCohorts, dummyProfs, 4),
//            new Lesson("CSE", "lt", dummyCohorts, dummyProfs, 4),
//            new Lesson("CSE", "lt", dummyCohorts, dummyProfs, 4),
//            new Lesson("CSE", "lt", dummyCohorts, dummyProfs, 4),
//            new Lesson("CSE", "lt", dummyCohorts, dummyProfs, 4)
//    ));

    public LessonsAdapter(AddedLessons lessons) {
        this.lessons = lessons;
    }
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
        holder.tvSubjectName.setText(l.getSubject());
        holder.tvCohortIds.setText(l.getCohorts().toString());
        holder.tvDurationValue.setText(String.valueOf(l.getDuration()));
        holder.tvLocation.setText(l.getLocation());
        holder.tvProfs.setText(l.getProfs().toString());
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
                    String id = lessons.get(getAdapterPosition()).getId();
                    removeLesson(id);

                    lessons.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    notifyItemRangeChanged(0, lessons.size());
                }
            });
        }
    }

    private void removeLesson(String id){
//        String username = mAuth.getCurrentUser().getDisplayName();
        String username = "Booga";
        DocumentReference docRef = db.collection("dummy").document(username);
        Map<String, Object> updates = new HashMap<>();
        updates.put(id, FieldValue.delete());

        docRef.update(updates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    System.out.println("Removed lesson from fb");
                }
                else{
                    System.out.println("Remove failed");
                }
            }
        });
    }

}
