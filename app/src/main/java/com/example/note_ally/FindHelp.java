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
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class FindHelp extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    androidx.recyclerview.widget.RecyclerView RecyclerView;
    FirebaseFirestore fstoreAdmissionHelp;
    ArrayList<AdmissionHelp> helpArrayList;
    AdmissionHelpAdapter adapter;

    String TAG = "TAG FindHelp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_help);

        FloatingActionButton fabHelp = findViewById(R.id.addhelp);
        //findViewById(R.id.addevent).setOnClickListener(this);
        fabHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                help();
            }
        });

        helpArrayList = new ArrayList<>();

        RecyclerView = findViewById(R.id.helpRecycle);
        RecyclerView.setHasFixedSize(true);
        RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        fstoreAdmissionHelp = FirebaseFirestore.getInstance();

        loadDataFromFirebase();
        searchDataInFirebase();
    }

    private void searchDataInFirebase() {
        if (helpArrayList.size() > 0)
            helpArrayList.clear();
        SearchView searchView = findViewById(R.id.searchhelp);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String s) {
                if (helpArrayList.size() > 0)
                    helpArrayList.clear();
                fstoreAdmissionHelp.collection("Admissionhelp")
                        .whereGreaterThanOrEqualTo("Tag", s.toUpperCase())
                        .orderBy("Tag").startAt(s.toUpperCase()).endAt(s.toUpperCase() + "\uf8ff")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            String institutionsubject, details, tag;

                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                for (DocumentSnapshot querySnapshot : task.getResult()) {
                                    institutionsubject = querySnapshot.getString("Institutionsubject");
                                    details = querySnapshot.getString("Details");
                                    tag = querySnapshot.getString("Tag");

                                    AdmissionHelp admissionhelp = new AdmissionHelp(institutionsubject,details,tag);
                                    helpArrayList.add(admissionhelp);
                                }
                                adapter = new AdmissionHelpAdapter(FindHelp.this, helpArrayList);
                                RecyclerView.setAdapter(adapter);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(FindHelp.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
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
        if (helpArrayList.size() > 0)
            helpArrayList.clear();
        fstoreAdmissionHelp.collection("Admissionhelp")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    String institutionsubject, details, tag;

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot querySnapshot : task.getResult()) {
                            institutionsubject = querySnapshot.getString("Institutionsubject");
                            details = querySnapshot.getString("Details");
                            tag = querySnapshot.getString("Tag");

                            AdmissionHelp admissionhelp = new AdmissionHelp(institutionsubject,details,tag);
                            helpArrayList.add(admissionhelp);
                        }
                        adapter = new AdmissionHelpAdapter(FindHelp.this, helpArrayList);
                        RecyclerView.setAdapter(adapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(FindHelp.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
                        Log.v("---I---", e.getMessage());
                    }
                });
    }

    private void help() {
        SharedPrefManager.getInstance(this).clear();
        Intent intent = new Intent(this, AddHelp.class);
        startActivity(intent);
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}