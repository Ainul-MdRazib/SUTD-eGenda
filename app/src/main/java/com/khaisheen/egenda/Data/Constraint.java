package com.khaisheen.egenda.Data;

import java.util.Random;

public class Constraint {
    String day;
    String startTime; // 0 = 8.30, 1 = 9.00, ... 18 = 17.30
    String duration; // 1 = 30min, range from 1 to 19

    public String getDay() {
        return day;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getDuration() {
        return duration;
    }

    public Constraint(String day, String startTime, String duration) {
        this.day = day;
        this.startTime = startTime;
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Constraint with day=" + day + " startTime=" + startTime + " duration=" + duration;
    }
}
