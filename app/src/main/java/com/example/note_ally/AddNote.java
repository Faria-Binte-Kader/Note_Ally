package com.example.note_ally;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddNote extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String TAG = "TAG AddNote";
    Button addNoteBtn;

    FirebaseFirestore fstoreNote;

    private EditText noteTitle, noteDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        addNoteBtn = findViewById(R.id.add_note_btn);
        noteTitle = findViewById(R.id.addNoteTitle);
        noteDetails = findViewById(R.id.addNoteDetails);

        fstoreNote = FirebaseFirestore.getInstance();

        addNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String notetitle = noteTitle.getText().toString();
                final String notedetails = noteDetails.getText().toString();

                DocumentReference documentReference = fstoreNote.collection("Note").document();
                Map<String, Object> note = new HashMap<>();
                note.put("Title", notetitle);
                note.put("Details", notedetails);
                note.put("PostID",documentReference.getId());

                documentReference.set(note).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "onSuccess: note added");
                    }
                });
                Toast.makeText(AddNote.this, "Added Successfully ", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(getApplicationContext(), ViewNote.class));
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