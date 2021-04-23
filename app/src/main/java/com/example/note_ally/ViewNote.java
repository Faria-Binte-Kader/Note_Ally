package com.example.note_ally;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ViewNote extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    RecyclerView RecyclerView;
    FirebaseFirestore fstoreNote;
    ArrayList<Notes> notesArrayList;
    NotesAdapter adapter;

    public void removeItem(int position) {

        final String id= notesArrayList.get(position).getPostID();
        fstoreNote.collection("Note").document(id)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG","Success");
                    }
                });

        notesArrayList.remove(position);
        adapter.notifyItemRemoved(position);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_note);

        notesArrayList = new ArrayList<>();

        RecyclerView = findViewById(R.id.notesRecycle);
        RecyclerView.setHasFixedSize(true);
        RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        fstoreNote = FirebaseFirestore.getInstance();

        loadDataFromFirebase();
    }

    private void loadDataFromFirebase() {
        if (notesArrayList.size() > 0)
            notesArrayList.clear();
        fstoreNote.collection("Note")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    String title, details,pid;

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot querySnapshot : task.getResult()) {
                            title = querySnapshot.getString("Title");
                            details = querySnapshot.getString("Details");
                            pid = querySnapshot.getString("PostID");

                            Notes notes = new Notes(title,details,pid);
                            notesArrayList.add(notes);
                        }
                        adapter = new NotesAdapter(ViewNote.this, notesArrayList);
                        RecyclerView.setAdapter(adapter);
                        adapter.setOnItemClickListener(new NotesAdapter.OnItemClickListener() {
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
                        Toast.makeText(ViewNote.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
                        Log.v("---I---", e.getMessage());
                    }
                });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}