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

public class FindNotice extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    RecyclerView RecyclerView;
    FirebaseFirestore fstoreNotice;
    ArrayList<Notice> noticeArrayList;
    NoticeAdapter adapter;

    String TAG = "TAG FindNotice";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_notice);

        FloatingActionButton fabNotice = findViewById(R.id.addnotice);
        //findViewById(R.id.addevent).setOnClickListener(this);
        fabNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notice();
            }
        });

        noticeArrayList = new ArrayList<>();

        RecyclerView = findViewById(R.id.noticeRecycle);
        RecyclerView.setHasFixedSize(true);
        RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        fstoreNotice = FirebaseFirestore.getInstance();

        loadDataFromFirebase();
        searchDataInFirebase();
    }

    private void searchDataInFirebase() {
        if (noticeArrayList.size() > 0)
            noticeArrayList.clear();
        SearchView searchView = findViewById(R.id.searchnotice);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String s) {
                if (noticeArrayList.size() > 0)
                    noticeArrayList.clear();
                fstoreNotice.collection("Notice")
                        .whereGreaterThanOrEqualTo("Tag", s.toUpperCase())
                        .orderBy("Tag").startAt(s.toUpperCase()).endAt(s.toUpperCase() + "\uf8ff")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            String institution, details, tag;

                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                for (DocumentSnapshot querySnapshot : task.getResult()) {
                                    institution = querySnapshot.getString("Institution");
                                    details = querySnapshot.getString("Details");
                                    tag = querySnapshot.getString("Tag");

                                    Notice notice = new Notice(institution,details,tag);
                                    noticeArrayList.add(notice);
                                }
                                adapter = new NoticeAdapter(FindNotice.this, noticeArrayList);
                                RecyclerView.setAdapter(adapter);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(FindNotice.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
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
        if (noticeArrayList.size() > 0)
            noticeArrayList.clear();
        fstoreNotice.collection("Notice")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    String institution, details, tag;

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot querySnapshot : task.getResult()) {
                            institution = querySnapshot.getString("Institution");
                            details = querySnapshot.getString("Details");
                            tag = querySnapshot.getString("Tag");

                            Notice notice = new Notice(institution,details,tag);
                            noticeArrayList.add(notice);
                        }
                        adapter = new NoticeAdapter(FindNotice.this, noticeArrayList);
                        RecyclerView.setAdapter(adapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(FindNotice.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
                        Log.v("---I---", e.getMessage());
                    }
                });
    }

    private void notice() {
        SharedPrefManager.getInstance(this).clear();
        Intent intent = new Intent(this, AddNotice.class);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}