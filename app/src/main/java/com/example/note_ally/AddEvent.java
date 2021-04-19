package com.example.note_ally;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddEvent extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String TAG = "TAG AddEvent";
    Button addEventBtn;

    FirebaseFirestore fstoreEvent;

    private EditText eventOrganizer, eventLocation, eventDetails, eventTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        addEventBtn = findViewById(R.id.add_event_btn);
        eventOrganizer = findViewById(R.id.addEventOrganizer);
        eventLocation = findViewById(R.id.addEventLocation);
        eventDetails = findViewById(R.id.addEventDetails);
        eventTag = findViewById(R.id.addEventTag);

        fstoreEvent = FirebaseFirestore.getInstance();

        addEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String eventorganizer = eventOrganizer.getText().toString().toUpperCase();
                final String eventlocation = eventLocation.getText().toString().toUpperCase();
                final String eventdetails = eventDetails.getText().toString().toUpperCase();
                final String eventtag = eventTag.getText().toString().toUpperCase();

                DocumentReference documentReference = fstoreEvent.collection("Event").document();
                Map<String, Object> event = new HashMap<>();
                event.put("Organizer", eventorganizer);
                event.put("Location", eventlocation);
                event.put("Details", eventdetails);
                event.put("Tag", eventtag);

                documentReference.set(event).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "onSuccess: event added");
                    }
                });

                startActivity(new Intent(getApplicationContext(), FindEvent.class));
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}