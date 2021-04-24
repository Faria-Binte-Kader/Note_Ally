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

public class Allbuypost extends AppCompatActivity implements View.OnClickListener{

    androidx.recyclerview.widget.RecyclerView RecyclerView;
    FirebaseFirestore fstorebuy;
    ArrayList<Buypost> allbuyArrayList;
    BuypostAdapter adapter;

    FirebaseAuth fAuthbuy;
    public static final String EXTRA_TEXT1 = "com.example.application.example.EXTRA_TEXT1_Allpost";

    String TAG = "TAG allbuypost";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allbuypost);
        allbuyArrayList = new ArrayList<>();

        findViewById(R.id.addsellbtn).setOnClickListener(this);

        RecyclerView = findViewById(R.id.allbuypostRecycle);
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
        SearchView searchView = findViewById(R.id.searchallbuypost);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String s) {
                if (allbuyArrayList.size() > 0)
                    allbuyArrayList.clear();
                fstorebuy.collection("BUYSELLPOSTS")
                        .whereEqualTo("Tag", s.toUpperCase())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            String link, details,tag,name,uid,pname;

                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                for (DocumentSnapshot querySnapshot : task.getResult()) {

                                    tag = querySnapshot.getString("Tag");
                                    details = querySnapshot.getString("Details");
                                    pname = querySnapshot.getString("ProductName");
                                    name = querySnapshot.getString("Username");
                                    uid = querySnapshot.getString("UserID");
                                    link = querySnapshot.getString("Downloadlink");

                                    Buypost buypost = new Buypost(tag,details,name,uid,link,pname);
                                    allbuyArrayList.add(buypost);
                                }
                                adapter = new BuypostAdapter(Allbuypost.this, allbuyArrayList);
                                RecyclerView.setAdapter(adapter);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Allbuypost.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
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
        fstorebuy.collection("BUYSELLPOSTS")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    String link, details,tag,name,uid,pname;

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot querySnapshot : task.getResult()) {
                            tag = querySnapshot.getString("Tag");
                            details = querySnapshot.getString("Details");
                            pname = querySnapshot.getString("ProductName");
                            name = querySnapshot.getString("Username");
                            uid = querySnapshot.getString("UserID");
                            link = querySnapshot.getString("Downloadlink");


                            Buypost buypost = new Buypost(tag,details,name,uid,link,pname);
                            allbuyArrayList.add(buypost);
                        }
                        adapter = new BuypostAdapter(Allbuypost.this, allbuyArrayList);
                        RecyclerView.setAdapter(adapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Allbuypost.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
                        Log.v("---I---", e.getMessage());
                    }
                });
    }
    private void gotoaddbuypostpage() {
        SharedPrefManager.getInstance(this).clear();
        Intent intent = new Intent(this, Addbuypost.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addsellbtn:
                gotoaddbuypostpage();
                break;
        }

    }

    public void chat(String s) {
        Intent intent = new Intent(Allbuypost.this, MessageActivity.class);
        intent.putExtra(EXTRA_TEXT1, s);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    }
