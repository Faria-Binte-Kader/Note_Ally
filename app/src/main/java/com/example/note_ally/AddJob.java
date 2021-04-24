package com.example.note_ally;

import androidx.annotation.NonNull;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class AddJob extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String TAG = "TAG AddJob";
    Button addJobBtn;
    String id,cnt= "";

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
                job.put("PostID",documentReference.getId());

                DocumentReference documentReference2 = fstoreJob.collection("JobTags").document();
                Map<String, Object> tag = new HashMap<>();
                tag.put("Tagname", jobtag);

                fstoreJob.collection("UserTags")
                        .whereEqualTo("Jobtag", jobtag.toUpperCase())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                for (DocumentSnapshot querySnapshot : task.getResult()) {
                                    id = querySnapshot.getString("UserID");

                                    DocumentReference docRef = fstoreJob.collection("NotificationCount").document(id);
                                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if (task.isSuccessful()) {
                                                DocumentSnapshot doc = task.getResult();
                                                if (doc.exists()) {
                                                    cnt = cnt + doc.getString("Count");
                                                    int temp = Integer.parseInt(cnt) + 1;
                                                    cnt = String.valueOf(temp);

                                                    DocumentReference documentReference4 = fstoreJob.collection("NotificationCount").document(id);
                                                    Map<String, Object> count = new HashMap<>();
                                                    count.put("Count", cnt);

                                                    documentReference4.set(count).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            Log.d(TAG, "onSuccess: notification count added");
                                                        }
                                                    });

                                                    DocumentReference documentReference3 = fstoreJob.collection("Notifications").document(id).collection("Notifs").document();
                                                    Map<String, Object> notif = new HashMap<>();
                                                    notif.put("Company", jobcompany);
                                                    notif.put("Position", jobposition);
                                                    notif.put("Details", jobdetails);
                                                    notif.put("Tag", jobtag);
                                                    notif.put("PostID",documentReference.getId());
                                                    notif.put("Serial",cnt);

                                                    documentReference3.set(notif).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            Log.d(TAG, "onSuccess: notification added");
                                                        }
                                                    });

                                                } else {
                                                    Log.d("TAG", "No such document");
                                                }
                                            } else {
                                                Log.d("TAG", "got failed with ", task.getException());
                                            }
                                        }
                                    });

                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AddJob.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
                                Log.v("---I---", e.getMessage());
                            }
                        });

                documentReference2.set(tag).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "onSuccess: tag added");
                    }
                });

                documentReference.set(job).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "onSuccess: job added");
                    }
                });
                Toast.makeText(AddJob.this, "Added Successfully ", Toast.LENGTH_SHORT).show();

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