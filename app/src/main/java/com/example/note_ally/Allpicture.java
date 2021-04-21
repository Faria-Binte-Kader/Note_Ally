package com.example.note_ally;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Allpicture extends AppCompatActivity implements View.OnClickListener{

    public FloatingActionButton floatingActionButton;
    androidx.recyclerview.widget.RecyclerView RecyclerView;
    FirebaseFirestore fstorepicture;
    ArrayList<Modelpicture> allpictureArrayList;
    PictureAdapter adapter;
    String s;

    String uid;

    FirebaseAuth fAuthpicture;

    String TAG = "TAG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allpicture);

        findViewById(R.id.addpicture).setOnClickListener(this);
        allpictureArrayList = new ArrayList<>();

        RecyclerView = findViewById(R.id.allpictureRecycle);
        RecyclerView.setHasFixedSize(true);
        RecyclerView.setLayoutManager(new LinearLayoutManager(this));

        fAuthpicture = FirebaseAuth.getInstance();
        fstorepicture = FirebaseFirestore.getInstance();
        uid = fAuthpicture.getCurrentUser().getUid();


        loadDataFromFirebase();
        searchDataInFirebase();
    }


    private void searchDataInFirebase() {
        if (allpictureArrayList.size() > 0)
            allpictureArrayList.clear();
        SearchView searchView = findViewById(R.id.searchallpicture);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String s) {
                if (allpictureArrayList.size() > 0)
                    allpictureArrayList.clear();
                fstorepicture.collection("PICTURE")
                        .whereEqualTo("Tag", s.toUpperCase())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            String link, details,tag,name,uid;

                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                for (DocumentSnapshot querySnapshot : task.getResult()) {

                                    tag = querySnapshot.getString("Tag");
                                    details = querySnapshot.getString("Details");
                                    name = querySnapshot.getString("Username");
                                    uid = querySnapshot.getString("UserID");
                                    link = querySnapshot.getString("Downloadlink");

                                    Modelpicture modelpicture = new Modelpicture(tag,details,name,uid,link);
                                    allpictureArrayList.add(modelpicture);
                                }
                                adapter = new PictureAdapter(Allpicture.this, allpictureArrayList);
                                RecyclerView.setAdapter(adapter);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Allpicture.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
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
        if (allpictureArrayList.size() > 0)
            allpictureArrayList.clear();
        fstorepicture.collection("PICTURE")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    String link, details,tag,name,uid;

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot querySnapshot : task.getResult()) {
                            tag = querySnapshot.getString("Tag");
                            details = querySnapshot.getString("Details");
                            name = querySnapshot.getString("Username");
                            uid = querySnapshot.getString("UserID");
                            link = querySnapshot.getString("Downloadlink");


                            Modelpicture modelpicture = new Modelpicture(tag,details,name,uid,link);
                            allpictureArrayList.add(modelpicture);
                        }
                        adapter = new PictureAdapter(Allpicture.this, allpictureArrayList);
                        RecyclerView.setAdapter(adapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Allpicture.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
                        Log.v("---I---", e.getMessage());
                    }
                });
    }

    private void addpicture() {
        SharedPrefManager.getInstance(this).clear();
        Intent intent = new Intent(this, Addpicture.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addpicture:
                addpicture();
                break;
        }

    }}