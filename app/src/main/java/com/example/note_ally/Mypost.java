package com.example.note_ally;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Mypost extends AppCompatActivity implements View.OnClickListener{

    public FloatingActionButton floatingActionButton;
    androidx.recyclerview.widget.RecyclerView RecyclerView;
    FirebaseFirestore fstorepost;
    ArrayList<Feedpost> mypostArrayList;
    MypostAdapter adapter;
    String s;

    String uid;

    FirebaseAuth fAuthpost;

    String TAG = "TAG mypost";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypost);

        findViewById(R.id.addmypost).setOnClickListener(this);
        mypostArrayList = new ArrayList<>();

        RecyclerView = findViewById(R.id.mypostRecycle);
        RecyclerView.setHasFixedSize(true);
        RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        fstorepost = FirebaseFirestore.getInstance();

        fAuthpost = FirebaseAuth.getInstance();
        fstorepost = FirebaseFirestore.getInstance();
        uid = fAuthpost.getCurrentUser().getUid();


        //loadDataFromFirebase();
        searchDataInFirebase();
    }

    private void searchDataInFirebase() {
        if (mypostArrayList.size() > 0)
            mypostArrayList.clear();
                fstorepost.collection("FeedPost")
                        .whereEqualTo("UserID", uid)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            String like, dislike, details,tag,report,name,pid;

                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                for (DocumentSnapshot querySnapshot : task.getResult()) {
                                    like = querySnapshot.getString("Like");
                                    dislike = querySnapshot.getString("Dislike");
                                    details = querySnapshot.getString("Details");
                                    tag = querySnapshot.getString("Tag");
                                    report = querySnapshot.getString("Report");
                                    name = querySnapshot.getString("Username");
                                    pid = querySnapshot.getString("PostID");

                                    Feedpost feedpost = new Feedpost(tag,details,name,uid,like,dislike,report,pid);
                                    mypostArrayList.add(feedpost);
                                }
                                adapter = new MypostAdapter(Mypost.this, mypostArrayList);
                                RecyclerView.setAdapter(adapter);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Mypost.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
                                Log.v("---I---", e.getMessage());
                            }
                        });

    }

    private void loadDataFromFirebase() {
        if (mypostArrayList.size() > 0)
            mypostArrayList.clear();
        fstorepost.collection("FeedPost")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    String like, dislike, details,tag,report,pid;

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot querySnapshot : task.getResult()) {
                            like = querySnapshot.getString("Like");
                            dislike = querySnapshot.getString("Dislike");
                            details = querySnapshot.getString("Details");
                            tag = querySnapshot.getString("Tag");
                            report = querySnapshot.getString("Report");
                            pid = querySnapshot.getString("PostID");

                            Feedpost feedpost = new Feedpost(tag,details,s,uid,like,dislike,report,pid);
                            mypostArrayList.add(feedpost);
                        }
                        adapter = new MypostAdapter(Mypost.this, mypostArrayList);
                        RecyclerView.setAdapter(adapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Mypost.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
                        Log.v("---I---", e.getMessage());
                    }
                });
    }

    private void mypost() {
        SharedPrefManager.getInstance(this).clear();
        Intent intent = new Intent(this, Addmypost.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addmypost:
                mypost();
                break;
        }

    }
}