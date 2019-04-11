package com.khaisheen.egenda.Data;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.khaisheen.egenda.Data.Constraint.START_TIME_MAP;


public class AddedConstraints {
    private static final AddedConstraints ourInstance = new AddedConstraints();
    private ArrayList<Constraint> constraints = new ArrayList<>();

    public static AddedConstraints getInstance() {
        return ourInstance;
    }

    private AddedConstraints() {
    }

    public void getFromFireStore(FirebaseAuth auth, FirebaseFirestore db){
//        if(auth.getCurrentUser() == null){
//            return;
//        }
//        String username = auth.getCurrentUser().getDisplayName();
        String username = "Booga";
        constraints = new ArrayList<>();
        if(!username.equals("")) {
            db.collection("prof_constraints").document(username).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot document) {
                    for (Map.Entry<String, Object> e : document.getData().entrySet()) {
                        HashMap<String, String> m = (HashMap<String, String>) e.getValue();
                        String day = e.getKey();
                        String duration = getDurationFrom(m);
                        String startTime = getStartTimeFrom(m);
                        Constraint c = new Constraint(day, startTime, duration);
                        constraints.add(c);
                    }
                }
            });
        }
    }

    private String getStartTimeFrom(HashMap m){
        String tempStartTime = (String) m.get("startTime");
        String out = "";
        for(Map.Entry e : START_TIME_MAP.entrySet()){
            if(e.getValue().equals(tempStartTime)){
                out += (String) e.getKey();
            }
        }
        return out;
    }

    private String getDurationFrom(HashMap m){
        String tempDuration = (String) m.get("duration");
        double durationInHours = Double.valueOf(tempDuration) / 2;
        return String.valueOf(durationInHours);
    }

    public Constraint get(int i) {
        return constraints.get(i);
    }

    public int size() {
        return constraints.size();
    }

    public void remove(int i) {
        constraints.remove(i);
    }

    public void add(Constraint c){
        boolean newConstraintOfSameDayExists = false;
        for(Constraint existingConstraint : constraints){
            if(existingConstraint.getDay().equals(c.getDay())){
                existingConstraint.setDuration(c.getDuration());
                existingConstraint.setStartTime(c.getStartTime());
                newConstraintOfSameDayExists = true;
            }
        }
        System.out.println("Same constraint day exits : " + newConstraintOfSameDayExists);
        if(!newConstraintOfSameDayExists){
            constraints.add(c);
        }
    }
}
