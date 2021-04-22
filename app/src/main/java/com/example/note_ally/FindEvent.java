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

public class FindEvent extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    RecyclerView RecyclerView;
    FirebaseFirestore fstoreEvent;
    ArrayList<Event> eventArrayList;
    EventAdapter adapter;

    String TAG = "TAG FindEvent";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_event);

        FloatingActionButton fabEvent = findViewById(R.id.addevent);
        //findViewById(R.id.addevent).setOnClickListener(this);
        fabEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                event();
            }
        });

        eventArrayList = new ArrayList<>();

        RecyclerView = findViewById(R.id.eventsRecycle);
        RecyclerView.setHasFixedSize(true);
        RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        fstoreEvent = FirebaseFirestore.getInstance();

        loadDataFromFirebase();
        searchDataInFirebase();
    }

    private void searchDataInFirebase() {
        if (eventArrayList.size() > 0)
            eventArrayList.clear();
        SearchView searchView = findViewById(R.id.searchevent);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String s) {
                if (eventArrayList.size() > 0)
                    eventArrayList.clear();
                fstoreEvent.collection("Event")
                        .whereEqualTo("Tag", s.toUpperCase())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            String organizer, location, details, tag;

                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                for (DocumentSnapshot querySnapshot : task.getResult()) {
                                    organizer = querySnapshot.getString("Organizer");
                                    location = querySnapshot.getString("Location");
                                    details = querySnapshot.getString("Details");
                                    tag = querySnapshot.getString("Tag");

                                    Event event = new Event(organizer,location,details,tag);
                                    eventArrayList.add(event);
                                }
                                adapter = new EventAdapter(FindEvent.this, eventArrayList);
                                RecyclerView.setAdapter(adapter);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(FindEvent.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
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
        if (eventArrayList.size() > 0)
            eventArrayList.clear();
        fstoreEvent.collection("Event")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    String organizer, location, details, tag;

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot querySnapshot : task.getResult()) {
                            organizer = querySnapshot.getString("Organizer");
                            location = querySnapshot.getString("Location");
                            details = querySnapshot.getString("Details");
                            tag = querySnapshot.getString("Tag");

                            Event event = new Event(organizer,location,details,tag);
                            eventArrayList.add(event);
                        }
                        adapter = new EventAdapter(FindEvent.this, eventArrayList);
                        RecyclerView.setAdapter(adapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(FindEvent.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
                        Log.v("---I---", e.getMessage());
                    }
                });
    }

    private void event() {
        SharedPrefManager.getInstance(this).clear();
        Intent intent = new Intent(this, AddEvent.class);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}