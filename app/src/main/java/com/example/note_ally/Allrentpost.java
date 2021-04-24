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

public class Allrentpost extends AppCompatActivity implements View.OnClickListener {

    androidx.recyclerview.widget.RecyclerView RecyclerView;
    FirebaseFirestore fstorebuy;
    ArrayList<Buypost> allbuyArrayList;
    RentpostAdapter adapter;
    public static final String EXTRA_TEXT1 = "com.example.application.example.EXTRA_TEXT1_Allpost";

    FirebaseAuth fAuthbuy;

    String TAG = "TAG allbuypost";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allrentpost);
        allbuyArrayList = new ArrayList<>();

        findViewById(R.id.addrentbtn).setOnClickListener(this);

        RecyclerView = findViewById(R.id.allrentpostRecycle);
        RecyclerView.setHasFixedSize(true);
        RecyclerView.setLayoutManager(new LinearLayoutManager(this));

        fAuthbuy = FirebaseAuth.getInstance();
        fstorebuy = FirebaseFirestore.getInstance();

        loadDataFromFirebase();
        searchDataInFirebase();

    }

    private void searchDataInFirebase() {
        if (allbuyArrayList.size() > 0)
            allbuyArrayList.clear();
        SearchView searchView = findViewById(R.id.searchallrentpost);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String s) {
                if (allbuyArrayList.size() > 0)
                    allbuyArrayList.clear();
                fstorebuy.collection("RENTPOSTS")
                        .whereEqualTo("Tag", s.toUpperCase())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            String link, details,tag,name,uid,pname,pid;

                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                for (DocumentSnapshot querySnapshot : task.getResult()) {

                                    tag = querySnapshot.getString("Tag");
                                    details = querySnapshot.getString("Details");
                                    pname = querySnapshot.getString("ProductName");
                                    name = querySnapshot.getString("Username");
                                    uid = querySnapshot.getString("UserID");
                                    link = querySnapshot.getString("Downloadlink");
                                    pid = querySnapshot.getString("PostID");

                                    Buypost buypost = new Buypost(tag,details,name,uid,link,pname,pid);
                                    allbuyArrayList.add(buypost);
                                }
                                adapter = new RentpostAdapter(Allrentpost.this, allbuyArrayList);
                                RecyclerView.setAdapter(adapter);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Allrentpost.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
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
        if (allbuyArrayList.size() > 0)
            allbuyArrayList.clear();
        fstorebuy.collection("RENTPOSTS")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    String link, details,tag,name,uid,pname,pid;

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot querySnapshot : task.getResult()) {
                            tag = querySnapshot.getString("Tag");
                            details = querySnapshot.getString("Details");
                            pname = querySnapshot.getString("ProductName");
                            name = querySnapshot.getString("Username");
                            uid = querySnapshot.getString("UserID");
                            link = querySnapshot.getString("Downloadlink");
                            pid = querySnapshot.getString("PostID");

                            Buypost buypost = new Buypost(tag,details,name,uid,link,pname,pid);
                            allbuyArrayList.add(buypost);
                        }
                        adapter = new RentpostAdapter(Allrentpost.this, allbuyArrayList);
                        RecyclerView.setAdapter(adapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Allrentpost.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
                        Log.v("---I---", e.getMessage());
                    }
                });
    }
    private void gotoaddrentpostpage() {
        SharedPrefManager.getInstance(this).clear();
        Intent intent = new Intent(this, Addrentpost.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addrentbtn:
                gotoaddrentpostpage();
                break;
        }

    }
    public void chat(String s) {
        Intent intent = new Intent(Allrentpost.this, MessageActivity.class);
        intent.putExtra(EXTRA_TEXT1, s);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}