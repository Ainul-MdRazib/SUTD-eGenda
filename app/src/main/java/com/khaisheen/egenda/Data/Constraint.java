package com.khaisheen.egenda.Data;

import java.util.HashMap;
import java.util.Random;

public class Constraint {
    String day;
    String startTime; // 0 = 8.30, 1 = 9.00, ... 18 = 17.30
    String duration; // 1 = 30min, range from 1 to 19
    public static final HashMap<String,String> START_TIME_MAP = new HashMap<String,String>(){{
        put("8:30","0");put("9:00","2");put("9:30","3");put("10:00","4");
        put("10:30","5");put("11:00","6");put("11:30","7");put("12:00","8");
        put("12:30","9");put("13:00","10");put("13:30","11");put("14:00","12");
        put("14:30","13");put("15:00","14");put("15:30","15");put("16:00","16");
        put("16:30","17");put("17:00","18");put("17:30","19");
    }};

    public String getDay() {
        return day;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setDuration(String duration) {
        this.duration = duration;
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
