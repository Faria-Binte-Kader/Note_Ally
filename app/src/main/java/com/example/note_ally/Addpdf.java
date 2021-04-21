package com.example.note_ally;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class Addpdf extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    public static final String TAG = "TAG Addpdf";
    Button addpdfbtn, choosepdfbtn;

    FirebaseFirestore fstorepdf;
    FirebaseAuth fAuthpdf;
    String uid;
    Uri imageuri = null;
    ProgressDialog dialog;
    String myurl,name;

    private EditText pdftag,pdfdetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpdf);

        addpdfbtn = findViewById(R.id.add_pdf_btn);
        pdfdetails = findViewById(R.id.addpdfdetails);
        pdftag = findViewById(R.id.addpdftag);
        choosepdfbtn= findViewById(R.id.choosepdfbtn);

        fstorepdf = FirebaseFirestore.getInstance();
        fAuthpdf = FirebaseAuth.getInstance();
        uid = fAuthpdf.getCurrentUser().getUid();


        DocumentReference documentReference = fstorepdf.collection("User").document(uid);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null) {
                    name= value.getString("Name");

                }
            }
        });

        choosepdfbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

                galleryIntent.setType("application/pdf");
                startActivityForResult(galleryIntent, 1);
            }
        });

        addpdfbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String pdftg = pdftag.getText().toString().toUpperCase();
                final String pdfdet= pdfdetails.getText().toString();

                DocumentReference documentReference = fstorepdf.collection("PDF").document();
                Map<String, Object> pdf = new HashMap<>();
                pdf.put("Details", pdfdet);
                pdf.put("Tag", pdftg);
                pdf.put("Username", name);
                pdf.put("UserID", uid);
                pdf.put("Downloadlink", myurl);

                documentReference.set(pdf).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "onSuccess: pdf added");
                    }
                });

                startActivity(new Intent(getApplicationContext(), Allpdf.class));
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            // Here we are initialising the progress dialog box
            dialog = new ProgressDialog(this);
            dialog.setMessage("Uploading");
            dialog.show();
            imageuri = data.getData();
            final String timestamp = "" + System.currentTimeMillis();
            StorageReference storageReference = FirebaseStorage.getInstance().getReference();
            final String messagePushID = timestamp;
            Toast.makeText(Addpdf.this, imageuri.toString(), Toast.LENGTH_SHORT).show();

            final StorageReference filepath = storageReference.child("PDFs/"+messagePushID + "." + "pdf");
            Toast.makeText(Addpdf.this, filepath.getName(), Toast.LENGTH_SHORT).show();
            filepath.putFile(imageuri).continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return filepath.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        dialog.dismiss();
                        Uri uri = task.getResult();
                        myurl = uri.toString();
                        Toast.makeText(Addpdf.this, "Uploaded Successfully "+myurl, Toast.LENGTH_SHORT).show();
                    } else {
                        dialog.dismiss();
                        Toast.makeText(Addpdf.this, "UploadedFailed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }}