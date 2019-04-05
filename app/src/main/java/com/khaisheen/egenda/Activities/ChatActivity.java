package com.khaisheen.egenda.Activities;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.khaisheen.egenda.Adapters.MessageAdapter;
import com.khaisheen.egenda.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Nullable;

public class ChatActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private CollectionReference pillarMessages;

    private Toolbar mToolbar;
    private ImageButton sendButton;

    private TextView messageField;
    private RecyclerView mMessagesList;
    final private List<SingleMessage> messagesList = new ArrayList<>();
    private LinearLayoutManager mLinearLayout;

    private FirebaseFirestore db;
    private MessageAdapter mAdapter;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mToolbar = (Toolbar) findViewById(R.id.chatToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final SharedPreferences sharedPref = ChatActivity.this.getPreferences(Context.MODE_PRIVATE);

        FirebaseApp.initializeApp(ChatActivity.this);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        mAdapter =new MessageAdapter(messagesList);

        messageField = (TextView) findViewById(R.id.messageField);
        mMessagesList = (RecyclerView) findViewById(R.id.messagesList);

        sendButton = (ImageButton) findViewById(R.id.sendButton);

        mLinearLayout = new LinearLayoutManager(this);

        mMessagesList.setHasFixedSize(true);
        mMessagesList.setLayoutManager(mLinearLayout);
        mMessagesList.setAdapter(mAdapter);

        db = FirebaseFirestore.getInstance();
        pillarMessages = db.collection("chat").document("ISTD").collection("istdMessages");

        loadMessages();

        sendButton = (ImageButton) findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = messageField.getText().toString();
                String sentBy = user.getDisplayName();
                Timestamp timestamp = Timestamp.now();

                Map<String, Object> data = new HashMap<>();
                data.put("timestamp", timestamp);
                data.put("sentBy", sentBy);
                data.put("message", message);

                pillarMessages.add(data)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d("", "DocumentSnapshot written with ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("", "Error adding document", e);
                            }
                        });
                messageField.setText("");

            }
        });


    }
    private void loadMessages(){
        db.collection("chat").document("ISTD")
                .collection("istdMessages")
                .orderBy("timestamp", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException e) {
                for (DocumentChange documentChange : snapshot.getDocumentChanges()) {
                    Timestamp timestamp = (Timestamp) documentChange.getDocument().getData().get("timestamp");
                    String sentBy = (String) documentChange.getDocument().getData().get("sentBy");
                    String message = (String) documentChange.getDocument().getData().get("message");
                    SingleMessage someMessage = new SingleMessage(sentBy, timestamp, message);
                    messagesList.add(someMessage);
                    mAdapter.notifyDataSetChanged();

                    mMessagesList.scrollToPosition(messagesList.size() - 1);
                }
            }
        });
    }


    public String convertDate(long timestamp){

        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(timestamp * 1000L);
        String date = DateFormat.format("dd-MM-yyyy hh:mm:ss", cal).toString();
        return date;
    }

}

