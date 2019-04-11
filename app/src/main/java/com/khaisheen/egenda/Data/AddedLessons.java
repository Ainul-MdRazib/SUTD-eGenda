package com.khaisheen.egenda.Data;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddedLessons {
    private static final AddedLessons ourInstance = new AddedLessons();
    private ArrayList<Lesson> lessons;

    public static AddedLessons getInstance() {
        return ourInstance;
    }

    public Lesson get(int i){
        return lessons.get(i);
    }

    public int size(){
        return lessons.size();
    }

    public void remove(int i){
        lessons.remove(i);
    }

    private AddedLessons() {
        lessons = new ArrayList<>();
    }

    public void addLesson(Lesson l){
        lessons.add(l);
    }

    public void getFromFirestore(FirebaseAuth auth, FirebaseFirestore db){
        if(auth.getCurrentUser() == null){
            return;
        }
        String username = auth.getCurrentUser().getDisplayName();
        lessons = new ArrayList<>();
        if(!username.equals("")) {
            db.collection("lessons").document(username).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot document) {
                    for (Map.Entry<String, Object> e : document.getData().entrySet()) {
                        HashMap<String, Object> m = (HashMap<String, Object>) e.getValue();
                        String id = e.getKey();
                        String subject = (String) m.get("subject");
                        String location = (String) m.get("location");
                        ArrayList cohorts = (ArrayList) m.get("cohorts");
                        ArrayList profs = getProfsFrom(m);
                        String duration = (String) m.get("duration");
                        Lesson l = new Lesson(subject, location, cohorts, profs, duration, id);
                        lessons.add(l);
                    }
                }
            });
        }

    }

    private ArrayList<String> getProfsFrom (HashMap m){
        ArrayList<String> out = new ArrayList<>();
        if(m.containsKey("shared")){
            out = (ArrayList<String>) m.get("shared");
        }
        else{
            out.add("myself");
        }
        return out;
    }

}
