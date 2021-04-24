package com.example.note_ally;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class FindJob extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    androidx.recyclerview.widget.RecyclerView RecyclerView;
    FirebaseFirestore fstoreJob;
    ArrayList<Job> jobArrayList;
    JobAdapter adapter;

    String TAG = "TAG FindJob";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_job);

        Button tags = findViewById(R.id.job_tag_btn);
        tags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tag();
            }
        });

        FloatingActionButton fabJob = findViewById(R.id.addjob);
        //findViewById(R.id.addevent).setOnClickListener(this);
        fabJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                job();
            }
        });

        jobArrayList = new ArrayList<>();

        RecyclerView = findViewById(R.id.jobsRecycle);
        RecyclerView.setHasFixedSize(true);
        RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        fstoreJob = FirebaseFirestore.getInstance();

        loadDataFromFirebase();
        searchDataInFirebase();
    }

    private void searchDataInFirebase() {
        if (jobArrayList.size() > 0)
            jobArrayList.clear();
        SearchView searchView = findViewById(R.id.searchjob);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String s) {
                if (jobArrayList.size() > 0)
                    jobArrayList.clear();
                fstoreJob.collection("Job")
                        .whereGreaterThanOrEqualTo("Tag", s.toUpperCase())
                        .orderBy("Tag").startAt(s.toUpperCase()).endAt(s.toUpperCase() + "\uf8ff")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            String company, position, details, tag;

                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                for (DocumentSnapshot querySnapshot : task.getResult()) {
                                    company = querySnapshot.getString("Company");
                                    position = querySnapshot.getString("Position");
                                    details = querySnapshot.getString("Details");
                                    tag = querySnapshot.getString("Tag");

                                    Job job = new Job(company,position,details,tag);
                                    jobArrayList.add(job);
                                }
                                adapter = new JobAdapter(FindJob.this, jobArrayList);
                                RecyclerView.setAdapter(adapter);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(FindJob.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
                                Log.v("---I---", e.getMessage());
                            }
                        });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    private void loadDataFromFirebase() {
        if (jobArrayList.size() > 0)
            jobArrayList.clear();
        fstoreJob.collection("Job")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    String company, position, details, tag;

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot querySnapshot : task.getResult()) {
                            company = querySnapshot.getString("Company");
                            position = querySnapshot.getString("Position");
                            details = querySnapshot.getString("Details");
                            tag = querySnapshot.getString("Tag");

                            Job job = new Job(company,position,details,tag);
                            jobArrayList.add(job);
                        }
                        adapter = new JobAdapter(FindJob.this, jobArrayList);
                        RecyclerView.setAdapter(adapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(FindJob.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
                        Log.v("---I---", e.getMessage());
                    }
                });
    }

    private void job() {
        SharedPrefManager.getInstance(this).clear();
        Intent intent = new Intent(this, AddJob.class);
        startActivity(intent);
    }


    private void tag() {
        SharedPrefManager.getInstance(this).clear();
        Intent intent = new Intent(this, FindTags.class);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}