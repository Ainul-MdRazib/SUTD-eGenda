package com.khaisheen.egenda.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.khaisheen.egenda.Activities.ConstraintsActivity;
import com.khaisheen.egenda.Data.Constraint;
import com.khaisheen.egenda.R;

import java.io.ObjectStreamException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ConstraintsAdapter extends RecyclerView.Adapter {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<Constraint> constraints;

    public ConstraintsAdapter(ArrayList<Constraint> constraints) {
        this.constraints = constraints;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        System.out.println("Constraints: " + constraints);
        return new ConstraintsViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.constraint_card, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ConstraintsViewHolder holder = (ConstraintsViewHolder) viewHolder;
        Constraint c = constraints.get(i);
        holder.tvConstraint.setText("Constraint " + String.valueOf(i+1));
        holder.tvDay.setText(c.getDay());
        holder.tvStartTime.setText(c.getStartTime());
        holder.tvDurationConstraint.setText(c.getDuration());
    }

    @Override
    public int getItemCount() {
        return constraints.size();
    }

    class ConstraintsViewHolder extends RecyclerView.ViewHolder{

        Button btnDelete;
        TextView tvConstraint, tvDay, tvStartTime, tvDurationConstraint;

        public ConstraintsViewHolder(@NonNull View itemView) {
            super(itemView);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            tvConstraint = itemView.findViewById(R.id.tvConstraint);
            tvDay = itemView.findViewById(R.id.tvDay);
            tvStartTime = itemView.findViewById(R.id.tvStartTime);
            tvDurationConstraint = itemView.findViewById(R.id.tvDurationConstraint);

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: get constraint id and remove entry from firebase
                    String constraintDay = tvDay.getText().toString();
                    removeConstraint(constraintDay);

                    constraints.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    notifyItemRangeChanged(0, constraints.size());
                }
            });
        }
    }

    public void removeConstraint(String day){
        String username = mAuth.getCurrentUser().getDisplayName();
        DocumentReference docRef = db.collection("prof_constraints").document(username);
        Map<String, Object> updates = new HashMap<>();
        updates.put(day, FieldValue.delete());

        docRef.update(updates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    System.out.println("Removed constraint form fb");
                }
                else{
                    System.out.println("Remove failed");
                }
            }
        });
    }
}
