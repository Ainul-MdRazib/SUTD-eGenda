package com.khaisheen.egenda.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.khaisheen.egenda.R;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class ConstraintsAdapter extends RecyclerView.Adapter {

    /* DUMMY DATA */
    ArrayList<Constraint> constraints = new ArrayList<>(Arrays.asList(
            new Constraint("Monday", 0, 19),
            new Constraint("Wednesday", 8, 4),
            new Constraint("Friday", 4, 4)
    ));

    // TODO: pull constraints data from firebase and make new 'constraints' arraylist

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ConstraintsViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.constraint_card, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ConstraintsViewHolder holder = (ConstraintsViewHolder) viewHolder;
        Constraint c = constraints.get(i);
        holder.tvConstraint.setText("Constraint " + String.valueOf(i+1));
        holder.tvDay.setText(c.day);
        holder.tvStartTime.setText(String.valueOf(c.startTime));
        holder.tvDurationConstraint.setText(String.valueOf(c.duration));
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
                    constraints.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    notifyItemRangeChanged(0, constraints.size());
                }
            });
        }
    }

    class Constraint {
        String day;
        int startTime; // 0 = 8.30, 1 = 9.00, ... 18 = 17.30
        int duration; // 1 = 30min, range from 1 to 19
        int id;

        public Constraint(String day, int startTime, int duration) {
            Random r = new Random();
            id = r.nextInt(10000000);
            this.day = day;
            this.startTime = startTime;
            this.duration = duration;
        }
    }
}
