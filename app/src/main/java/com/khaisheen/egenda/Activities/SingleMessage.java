package com.khaisheen.egenda.Activities;

import com.google.firebase.Timestamp;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SingleMessage {

    private String sentBy;
    private Timestamp timestamp;
    private String message;

    public SingleMessage(){

    }

    public SingleMessage(String sentBy, Timestamp timestamp, String message){
        this.message = message;
        this.sentBy = sentBy;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getSentBy() {
        return sentBy;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public String getTime(){
         Date mDate = timestamp.toDate();
         SimpleDateFormat mTimeFormatter = new SimpleDateFormat("dd/MM, HH:mm");
         return mTimeFormatter.format(mDate);
     }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSentBy(String sentBy) {
        this.sentBy = sentBy;
    }
}
