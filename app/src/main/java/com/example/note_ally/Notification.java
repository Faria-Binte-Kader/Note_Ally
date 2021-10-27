package com.example.note_ally;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Notification extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    androidx.recyclerview.widget.RecyclerView RecyclerView;
    FirebaseFirestore fstoreNotification;
    FirebaseAuth fAuthNotif;
    ArrayList<JobNotification> jobNotificationArrayList;
    JobNotificationAdapter adapter;
    String uid;
    String jobtag = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        jobNotificationArrayList = new ArrayList<>();

        RecyclerView = findViewById(R.id.notificationRecycle);
        RecyclerView.setHasFixedSize(true);
        RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        fAuthNotif = FirebaseAuth.getInstance();
        fstoreNotification = FirebaseFirestore.getInstance();

        uid = fAuthNotif.getCurrentUser().getUid();

        if (jobNotificationArrayList.size() > 0)
            jobNotificationArrayList.clear();
        fstoreNotification.collection("Notifications").document(uid).collection("Notifs")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    String company, position, details, UserID;

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot querySnapshot : task.getResult()) {
                            company = querySnapshot.getString("Company");
                            position = querySnapshot.getString("Position");
                            details = querySnapshot.getString("Details");
                            UserID = querySnapshot.getString("UserID");

                            JobNotification jobNotification = new JobNotification(company, position, details);
                            jobNotificationArrayList.add(jobNotification);
                        }
                        adapter = new JobNotificationAdapter(Notification.this, jobNotificationArrayList);
                        RecyclerView.setAdapter(adapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Notification.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
                        Log.v("---I---", e.getMessage());
                    }
                });

        /*DocumentReference docRef = fstoreNotification.collection("UserTags").document(uid);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    if (doc.exists()) {
                        jobtag = jobtag + doc.getString("Jobtag");
                        if (jobNotificationArrayList.size() > 0)
                            jobNotificationArrayList.clear();
                        fstoreNotification.collection("Job")
                                .whereEqualTo("Tag",jobtag)
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    String company, position, details;

                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        for (DocumentSnapshot querySnapshot : task.getResult()) {
                                            company = querySnapshot.getString("Company");
                                            position = querySnapshot.getString("Position");
                                            details = querySnapshot.getString("Details");

                                            JobNotification jobNotification = new JobNotification(company,position,details);
                                            jobNotificationArrayList.add(jobNotification);
                                        }
                                        adapter = new JobNotificationAdapter(Notification.this, jobNotificationArrayList);
                                        RecyclerView.setAdapter(adapter);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(Notification.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
                                        Log.v("---I---", e.getMessage());
                                    }
                                });

                    } else {
                        Log.d("TAG", "No such document");
                    }
                } else {
                    Log.d("TAG", "got failed with ", task.getException());
                }
            }
        });*/

        /*if (jobNotificationArrayList.size() > 0)
            jobNotificationArrayList.clear();
        fstoreNotification.collection("Job")
                .whereEqualTo("Tag",jobtag)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    String company, position, details;

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot querySnapshot : task.getResult()) {
                            company = querySnapshot.getString("Company");
                            position = querySnapshot.getString("Position");
                            details = querySnapshot.getString("Details");

                            JobNotification jobNotification = new JobNotification(company,position,details);
                            jobNotificationArrayList.add(jobNotification);
                        }
                        adapter = new JobNotificationAdapter(Notification.this, jobNotificationArrayList);
                        RecyclerView.setAdapter(adapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Notification.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
                        Log.v("---I---", e.getMessage());
                    }
                });*/

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}