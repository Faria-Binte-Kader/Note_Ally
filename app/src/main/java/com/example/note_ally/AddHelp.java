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

public class AddHelp extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String TAG = "TAG AddHelp";
    Button addHelpBtn;

    FirebaseFirestore fstoreHelp;

    private EditText helpInstitutionsubject, helpDetails, helpTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_help);

        addHelpBtn = findViewById(R.id.add_help_btn);
        helpInstitutionsubject = findViewById(R.id.addHelpInstitutionSubject);
        helpDetails = findViewById(R.id.addHelpDetails);
        helpTag = findViewById(R.id.addHelpTag);

        fstoreHelp = FirebaseFirestore.getInstance();

        addHelpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String helpinstitutionsubject = helpInstitutionsubject.getText().toString();
                final String helpdetails = helpDetails.getText().toString();
                final String helptag = helpTag.getText().toString().toUpperCase();

                DocumentReference documentReference = fstoreHelp.collection("Admissionhelp").document();
                Map<String, Object> help = new HashMap<>();
                help.put("Institutionsubject", helpinstitutionsubject);
                help.put("Details", helpdetails);
                help.put("Tag", helptag);

                documentReference.set(help).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "onSuccess: help added");
                    }
                });
                Toast.makeText(AddHelp.this, "Added Successfully ", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(getApplicationContext(), FindHelp.class));
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