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

public class AddJob extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String TAG = "TAG AddJob";
    Button addJobBtn;

    FirebaseFirestore fstoreJob;

    private EditText jobCompany, jobPosition, jobDetails, jobTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_job);

        addJobBtn = findViewById(R.id.add_job_btn);
        jobCompany = findViewById(R.id.addJobCompany);
        jobPosition = findViewById(R.id.addJobPosition);
        jobDetails = findViewById(R.id.addJobDetails);
        jobTag = findViewById(R.id.addJobTag);

        fstoreJob = FirebaseFirestore.getInstance();

        addJobBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String jobcompany = jobCompany.getText().toString();
                final String jobposition = jobPosition.getText().toString();
                final String jobdetails = jobDetails.getText().toString();
                final String jobtag = jobTag.getText().toString().toUpperCase();

                DocumentReference documentReference = fstoreJob.collection("Job").document();
                Map<String, Object> job = new HashMap<>();
                job.put("Company", jobcompany);
                job.put("Position", jobposition);
                job.put("Details", jobdetails);
                job.put("Tag", jobtag);

                documentReference.set(job).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "onSuccess: job added");
                    }
                });

                startActivity(new Intent(getApplicationContext(), FindJob.class));
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