package com.example.note_ally;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

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

public class FindTags extends AppCompatActivity {

    androidx.recyclerview.widget.RecyclerView RecyclerView;
    FirebaseFirestore fstoretag;
    ArrayList<Tags> tagsArrayList;
    TagsAdapter adapter;

    FirebaseAuth fAuthtag;

    String TAG = "TAG findtags";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_tags);

        tagsArrayList = new ArrayList<>();

        RecyclerView = findViewById(R.id.tagsRecycle);
        RecyclerView.setHasFixedSize(true);
        RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        fstoretag = FirebaseFirestore.getInstance();

        fAuthtag = FirebaseAuth.getInstance();
        fstoretag = FirebaseFirestore.getInstance();

        loadDataFromFirebase();
        searchDataInFirebase();
    }

    private void searchDataInFirebase() {
        if (tagsArrayList.size() > 0)
            tagsArrayList.clear();
        SearchView searchView = findViewById(R.id.searchtags);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String s) {
                if (tagsArrayList.size() > 0)
                    tagsArrayList.clear();
                fstoretag.collection("JobTags")
                        .whereGreaterThanOrEqualTo("Tagname", s.toUpperCase())
                        .orderBy("Tagname").startAt(s.toUpperCase()).endAt(s.toUpperCase() + "\uf8ff")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            String tagname,uid,pid;

                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                for (DocumentSnapshot querySnapshot : task.getResult()) {
                                    tagname = querySnapshot.getString("Tagname");
                                    uid = querySnapshot.getString("UserID");
                                    pid = querySnapshot.getString("PostID");

                                    Tags tags = new Tags(tagname,uid,pid);
                                    tagsArrayList.add(tags);
                                }
                                adapter = new TagsAdapter(FindTags.this, tagsArrayList);
                                RecyclerView.setAdapter(adapter);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(FindTags.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
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
        if (tagsArrayList.size() > 0)
            tagsArrayList.clear();
        fstoretag.collection("JobTags")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    String tagname,uid,pid;

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot querySnapshot : task.getResult()) {
                            tagname = querySnapshot.getString("Tagname");
                            uid = querySnapshot.getString("UserID");
                            pid = querySnapshot.getString("PostID");

                            Tags tags = new Tags(tagname,uid,pid);
                            tagsArrayList.add(tags);
                        }
                        adapter = new TagsAdapter(FindTags.this, tagsArrayList);
                        RecyclerView.setAdapter(adapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(FindTags.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
                        Log.v("---I---", e.getMessage());
                    }
                });
    }
}