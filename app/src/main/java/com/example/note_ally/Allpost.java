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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Allpost extends AppCompatActivity {

    androidx.recyclerview.widget.RecyclerView RecyclerView;
    FirebaseFirestore fstorepost;
    ArrayList<Feedpost> allpostArrayList;
    AllpostAdapter adapter;
    String s;

    String uid, puid;

    FirebaseAuth fAuthpost;

    String TAG = "TAG allpost";

    public static final String EXTRA_TEXT1 = "com.example.application.example.EXTRA_TEXT1";

    public void reportItem(int position) {

        final String id= allpostArrayList.get(position).getPostID();
        uid = fAuthpost.getCurrentUser().getUid();

        DocumentReference documentReference = fstorepost.collection("FeedPost").document(id);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null) {
                    puid = value.getString("UserID");

                    if(uid!=puid) {

                        DocumentReference documentReference2 = fstorepost.collection("ReportedPost").document(id);
                        Map<String, Object> report = new HashMap<>();
                        report.put("Type", "Post");
                        report.put("UserID", uid);

                        documentReference2.set(report).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "onSuccess: report added");
                            }
                        });
                    }

                }
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allpost);

        allpostArrayList = new ArrayList<>();

        RecyclerView = findViewById(R.id.allpostRecycle);
        RecyclerView.setHasFixedSize(true);
        RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        fstorepost = FirebaseFirestore.getInstance();

        fAuthpost = FirebaseAuth.getInstance();
        fstorepost = FirebaseFirestore.getInstance();


        loadDataFromFirebase();
        searchDataInFirebase();
    }

    private void searchDataInFirebase() {
        if (allpostArrayList.size() > 0)
            allpostArrayList.clear();
        SearchView searchView = findViewById(R.id.searchallpost);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String s) {
                if (allpostArrayList.size() > 0)
                    allpostArrayList.clear();
                fstorepost.collection("FeedPost")
                        .whereGreaterThanOrEqualTo("Tag", s.toUpperCase())
                        .orderBy("Tag").startAt(s.toUpperCase()).endAt(s.toUpperCase() + "\uf8ff")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            String like, dislike, details,tag,report,name,uid,pid;

                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                for (DocumentSnapshot querySnapshot : task.getResult()) {
                                    like = querySnapshot.getString("Like");
                                    dislike = querySnapshot.getString("Dislike");
                                    details = querySnapshot.getString("Details");
                                    tag = querySnapshot.getString("Tag");
                                    report = querySnapshot.getString("Report");
                                    name = querySnapshot.getString("Username");
                                    uid = querySnapshot.getString("UserID");
                                    pid = querySnapshot.getString("PostID");

                                    Feedpost feedpost = new Feedpost(tag,details,name,uid,like,dislike,report,pid);
                                    allpostArrayList.add(feedpost);
                                }
                                adapter = new AllpostAdapter(Allpost.this, allpostArrayList);
                                RecyclerView.setAdapter(adapter);
                                adapter.setOnItemClickListener(new AllpostAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(int position) {

                                    }

                                    @Override
                                    public void onReportClick(int position) {
                                        reportItem(position);
                                    }
                                });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Allpost.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
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
        if (allpostArrayList.size() > 0)
            allpostArrayList.clear();
        fstorepost.collection("FeedPost")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    String like, dislike, details,tag,report,name,uid,pid;

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot querySnapshot : task.getResult()) {
                            like = querySnapshot.getString("Like");
                            dislike = querySnapshot.getString("Dislike");
                            details = querySnapshot.getString("Details");
                            tag = querySnapshot.getString("Tag");
                            report = querySnapshot.getString("Report");
                            name = querySnapshot.getString("Username");
                            uid = querySnapshot.getString("UserID");
                            pid = querySnapshot.getString("PostID");

                            Feedpost feedpost = new Feedpost(tag,details,name,uid,like,dislike,report,pid);
                            allpostArrayList.add(feedpost);
                        }
                        adapter = new AllpostAdapter(Allpost.this, allpostArrayList);
                        RecyclerView.setAdapter(adapter);
                        adapter.setOnItemClickListener(new AllpostAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(int position) {

                            }

                            @Override
                            public void onReportClick(int position) {
                                reportItem(position);
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Allpost.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
                        Log.v("---I---", e.getMessage());
                    }
                });
    }

    public void chat(String s) {
        Intent intent = new Intent(Allpost.this, MessageActivity.class);
        intent.putExtra(EXTRA_TEXT1, s);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}