package com.khaisheen.egenda.Data;

import java.util.ArrayList;
import java.util.Random;

public class Lesson {
    private String subject, location;
    private ArrayList<String> cohorts, profs;
    private String duration, id;

    public String getSubject() {
        return subject;
    }

    public String getLocation() {
        return location;
    }

    public ArrayList<String> getCohorts() {
        return cohorts;
    }

    public ArrayList<String> getProfs() {
        return profs;
    }

    public String getDuration() {
        return duration;
    }

    public String getId() {
        return id;
    }

    public Lesson(String subject, String location, ArrayList<String> cohorts, ArrayList<String> profs, String duration, String id) {
        this.subject = subject;
        this.location = location;
        this.cohorts = cohorts;
        this.profs = profs;
        this.duration = duration;
        this.id = id;
    }
}