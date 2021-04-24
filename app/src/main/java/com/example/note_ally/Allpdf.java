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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Allpdf extends AppCompatActivity implements View.OnClickListener{
    androidx.recyclerview.widget.RecyclerView RecyclerView;
    FirebaseFirestore fstorepdf;
    ArrayList<Modelpdf> allpdfArrayList;
    PdfAdapter adapter;

    FirebaseAuth fAuthpdf;

    String TAG = "TAG allpdf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allpdf);
        allpdfArrayList = new ArrayList<>();

        findViewById(R.id.addpdf).setOnClickListener(this);

        RecyclerView = findViewById(R.id.allpdfRecycle);
        RecyclerView.setHasFixedSize(true);
        RecyclerView.setLayoutManager(new LinearLayoutManager(this));

        fAuthpdf = FirebaseAuth.getInstance();
        fstorepdf = FirebaseFirestore.getInstance();

        loadDataFromFirebase();
        searchDataInFirebase();

    }

    private void searchDataInFirebase() {
        if (allpdfArrayList.size() > 0)
            allpdfArrayList.clear();
        SearchView searchView = findViewById(R.id.searchallpdf);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String s) {
                if (allpdfArrayList.size() > 0)
                    allpdfArrayList.clear();
                fstorepdf.collection("PDF")
                        .whereEqualTo("Tag", s.toUpperCase())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            String link, details,tag,name,uid,pname;

                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                for (DocumentSnapshot querySnapshot : task.getResult()) {

                                    tag = querySnapshot.getString("Tag");
                                    details = querySnapshot.getString("Details");
                                    name = querySnapshot.getString("Username");
                                    uid = querySnapshot.getString("UserID");
                                    link = querySnapshot.getString("Downloadlink");
                                    pname = querySnapshot.getString("Productname");


                                    Modelpdf modelpdf = new Modelpdf(tag,details,name,uid,link,pname);
                                    allpdfArrayList.add(modelpdf);
                                }
                                adapter = new PdfAdapter(Allpdf.this, allpdfArrayList);
                                RecyclerView.setAdapter(adapter);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Allpdf.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
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
        if (allpdfArrayList.size() > 0)
            allpdfArrayList.clear();
        fstorepdf.collection("PDF")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    String link, details,tag,name,uid,pname;

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot querySnapshot : task.getResult()) {
                            tag = querySnapshot.getString("Tag");
                            details = querySnapshot.getString("Details");
                            name = querySnapshot.getString("Username");
                            uid = querySnapshot.getString("UserID");
                            link = querySnapshot.getString("Downloadlink");
                            pname = querySnapshot.getString("Productname");


                            Modelpdf modelpdf = new Modelpdf(tag,details,name,uid,link,pname);
                            allpdfArrayList.add(modelpdf);
                        }
                        adapter = new PdfAdapter(Allpdf.this, allpdfArrayList);
                        RecyclerView.setAdapter(adapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Allpdf.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
                        Log.v("---I---", e.getMessage());
                    }
                });
    }
    private void gotoaddpdfpage() {
        SharedPrefManager.getInstance(this).clear();
        Intent intent = new Intent(this, Addpdf.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addpdf:
                gotoaddpdfpage();
                break;
        }

}}