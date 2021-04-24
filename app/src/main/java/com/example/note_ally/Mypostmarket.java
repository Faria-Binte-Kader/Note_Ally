package com.example.note_ally;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Mypostmarket extends AppCompatActivity{

    androidx.recyclerview.widget.RecyclerView RecyclerView;
    FirebaseFirestore fstorepost;
    ArrayList<Buypost> mypostArrayList;
    MypostmarketAdapter adapter;
    String s;

    String uid;

    FirebaseAuth fAuthpost;

    String TAG = "TAG mypostmarket";

    public void removeItem(int position) {

        final String id= mypostArrayList.get(position).getPostID();

        fstorepost.collection("BUYSELLPOSTS").document(id)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG","Success");
                    }
                });

        fstorepost.collection("RENTPOSTS").document(id)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG","Success");
                    }
                });

        mypostArrayList.remove(position);
        adapter.notifyItemRemoved(position);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypostmarket);

        mypostArrayList = new ArrayList<>();

        RecyclerView = findViewById(R.id.mypostmarketRecycle);
        RecyclerView.setHasFixedSize(true);
        RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        fstorepost = FirebaseFirestore.getInstance();

        fAuthpost = FirebaseAuth.getInstance();
        fstorepost = FirebaseFirestore.getInstance();
        uid = fAuthpost.getCurrentUser().getUid();


        loadDataFromFirebase();
    }

    private void loadDataFromFirebase() {
        if (mypostArrayList.size() > 0)
            mypostArrayList.clear();
        fstorepost.collection("BUYSELLPOSTS")
                .whereEqualTo("UserID", uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    String link, details,tag,name,pname,pid;

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
                            mypostArrayList.add(buypost);
                        }
                        adapter = new MypostmarketAdapter(Mypostmarket.this, mypostArrayList);
                        RecyclerView.setAdapter(adapter);
                        adapter.setOnItemClickListener(new MypostmarketAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(int position) {

                            }
                            @Override
                            public void onDeleteClick(int position) {
                                removeItem(position);
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Mypostmarket.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
                        Log.v("---I---", e.getMessage());
                    }
                });
        if (mypostArrayList.size() > 0)
            mypostArrayList.clear();
        fstorepost.collection("RENTPOSTS")
                .whereEqualTo("UserID", uid)
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
                            mypostArrayList.add(buypost);
                        }
                        adapter = new MypostmarketAdapter(Mypostmarket.this, mypostArrayList);
                        RecyclerView.setAdapter(adapter);
                        adapter.setOnItemClickListener(new MypostmarketAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(int position) {

                            }
                            @Override
                            public void onDeleteClick(int position) {
                                removeItem(position);
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Mypostmarket.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
                        Log.v("---I---", e.getMessage());
                    }
                });
    }

}