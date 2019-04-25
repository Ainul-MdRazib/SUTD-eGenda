package com.khaisheen.egenda.Activities;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.khaisheen.egenda.Data.CsvGenerator;
import com.khaisheen.egenda.R;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class ViewScheduleActivity extends AppCompatActivity {

    FirebaseFirestore db;
    FirebaseAuth auth;
    CsvGenerator csvGen;
    Button buttonBack, buttonDownload, buttonGet;
    ImageView imageViewOfSchedule;
    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_schedule);

        // auth
        auth = FirebaseAuth.getInstance();
        // db
        db = FirebaseFirestore.getInstance();
        csvGen = new CsvGenerator(db, this.getApplicationContext());
        // Views
        buttonBack = findViewById(R.id.buttonBack);
        // TODO something with imageview and download button (NOT SO SOON)
        imageViewOfSchedule = findViewById(R.id.imageViewOfSchedule);
        buttonGet = findViewById(R.id.btnGet);
        // Go to MainActivity with back button.
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ViewScheduleActivity.this, MainActivity.class));
            }
        });

        buttonGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                csvGen.getTimetableFor(auth.getCurrentUser().getDisplayName().toString()); // This is where i set the prof name for the csvgenerator
                Toast.makeText(ViewScheduleActivity.this, "File saved in: " + csvGen.getSavedFileDirectory(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void download(){
        mStorageRef = FirebaseStorage.getInstance().getReference();
        StorageReference fileRef = mStorageRef.child("test.ics");

        fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Local temp file has been created
                String mUrl = uri.toString();
                downloadFile(ViewScheduleActivity.this, "timetable",".ics",DIRECTORY_DOWNLOADS,mUrl);
                Toast.makeText(ViewScheduleActivity.this, "File downloaded", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });


    }

    public void downloadFile(Context ctx, String filename, String fileExtension, String destDir, String url){
        DownloadManager mDLManager = (DownloadManager) ctx.
                getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request req = new DownloadManager.Request(uri);

        req.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        req.setDestinationInExternalFilesDir(ctx, destDir, filename + fileExtension);

        mDLManager.enqueue(req);

    }
}
