package com.khaisheen.egenda.Data;

import android.content.Context;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import com.opencsv.CSVWriter;

public class CsvGenerator {
    ArrayList<String> timeFromIndex = new ArrayList<>(
            Arrays.asList("8:30:00", "9:00:00", "9:30:00", "10:00:00", "10:30:00", "11:00:00",
                    "11:30:00", "12:00:00", "12:30:00", "13:00:00", "13:30:00", "14:00:00",
                    "14:30:00", "15:00:00", "15:30:00", "16:00:00", "16:30:00", "17:00:00",
                    "17:30:00"
                    ));

    HashMap<String, String> firstWeekMap = new HashMap<String, String>(){{
        put("monday", "2019-01-28");
        put("tuesday", "2019-01-29");
        put("wednesday", "2019-01-30");
        put("thursday", "2019-01-31");
        put("friday", "2019-02-01");
    }};

    FirebaseFirestore db;
    Context context;
    CSVWriter csvWriter;
    ArrayList<Long> writtenIds;



    public CsvGenerator(FirebaseFirestore db, Context context) {
        this.db = db;
        this.context = context;
    }

    public void getTimetableFor(final String targetProf) {
        DocumentReference finalTimetableDoc = db.collection("timetable").document("finalised");
        writtenIds = new ArrayList<>();

        // CSV writer
        setCSVWriterOutput(targetProf); // makes CSV writer output at targetprof.csv

        finalTimetableDoc.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Map<String,Object> data = documentSnapshot.getData();
                for(Map.Entry e : data.entrySet()){
                    String dayName = (String) e.getKey();
                    Map<String,Object> day = (Map<String, Object>) e.getValue();
                    for(Map.Entry ee : day.entrySet()){
                        String locationName = (String) ee.getKey();
                        ArrayList<Object> slots = (ArrayList<Object>) ee.getValue();
                        for (int i = 0; i < slots.size(); i++) {
                            Object slot = slots.get(i);
                            if (slot.getClass() == HashMap.class) {
                                HashMap lesson = (HashMap) slot;
                                ArrayList<String> profs = (ArrayList<String>) lesson.get("profs");
                                ArrayList<String> cohorts = (ArrayList<String>) lesson.get("cohortID");
                                Object[] cohortForPrint = cohorts.toArray();
                                String subject = (String) lesson.get("subject");
                                Long sessionId = (Long) lesson.get("sessionid");

                                if(profs.contains(targetProf) && !writtenIds.contains(sessionId)){
                                    String startTime = timeFromIndex.get(i);
                                    int duration = ((Long) lesson.get("duration")).intValue();
                                    String endTime = timeFromIndex.get(timeFromIndex.indexOf(startTime) + duration);
                                    writtenIds.add(sessionId);

                                    try {
                                        writeFor14Weeks(csvWriter, subject,
                                                Arrays.toString(cohortForPrint),
                                                firstWeekMap.get(dayName), startTime,
                                                endTime, locationName);
                                    } catch (IOException e1) {
                                        e1.printStackTrace();
                                    }

                                }
                            }
                        }
                    }
                }
            }
        });
    }

    private void setCSVWriterOutput(String targetProf){
        try {
            FileOutputStream outputStream = context.openFileOutput(targetProf + ".csv", Context.MODE_PRIVATE);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            csvWriter = new CSVWriter(outputStreamWriter);
            String[] header = {"Subject", "Description", "Start Date", "Start Time", "End Date",  "End Time", "Location"}; // Here is the header
            csvWriter.writeNext(header);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void writeFor14Weeks(CSVWriter csvWriter, String subject, String cohorts,
                                 String firstDate, String startTime, String endTime,
                                 String location) throws IOException {
        for(int i=0; i<14; i++) {
            String dateToWrite = LocalDate.parse(firstDate).plusWeeks(i).toString(); // makes date + i weeks
            String[] nextLine = {subject, "Cohorts: " + cohorts, dateToWrite, startTime, dateToWrite, endTime, location};
            System.out.println("Writing " + Arrays.toString(nextLine));
            csvWriter.writeNext(nextLine);
        }
        csvWriter.flush();

    }
}
