package com.example.note_ally;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Addpicture extends AppCompatActivity {
    public static final int PICK_IMAGE = 1;
    public static final String TAG = "TAG Addpicture";
    Button addpictureBtn, choosepicturebtn;
    String uid,name;
    ImageView imageView;
    ProgressDialog dialog;

    private Uri ImageUri;
    Task<Uri> downloadUri;
    String url;


    FirebaseFirestore fstorepicture;
    FirebaseAuth fAuthpicture;

    private EditText pictureTag, pictureDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpicture);


        addpictureBtn = findViewById(R.id.add_picture_btn);
        choosepicturebtn = findViewById(R.id.choosebtn);
        pictureDetails = findViewById(R.id.addpicturedetails);
        pictureTag = findViewById(R.id.addpicturetag);
        imageView = findViewById(R.id.showimage1);


        fAuthpicture = FirebaseAuth.getInstance();
        fstorepicture = FirebaseFirestore.getInstance();
        uid = fAuthpicture.getCurrentUser().getUid();

        DocumentReference documentReference = fstorepicture.collection("User").document(uid);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null) {
                    name= value.getString("Name");

                }
            }
        });

        choosepicturebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(Addpicture.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Addpicture.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            100);
                }
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                //intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                //startActivityForResult(intent,PICK_IMAGE);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(
                        Intent.createChooser(
                                intent,
                                "Select Image from here..."),
                        PICK_IMAGE);
            }
        });
        addpictureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new ProgressDialog(Addpicture.this);
                dialog.setMessage("Uploading");
                dialog.show();
                if (ImageUri != null) {
                    StorageReference storageReference= FirebaseStorage.getInstance().getReference().child("images/" + UUID.randomUUID().toString());
                    Toast.makeText(Addpicture.this, storageReference.getName(), Toast.LENGTH_SHORT).show();
                    storageReference.putFile(ImageUri).continueWithTask(new Continuation() {
                        @Override
                        public Object then(@NonNull Task task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }
                            return storageReference.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                dialog.dismiss();
                                Uri uri = task.getResult();
                                url = uri.toString();
                                final String pictg = pictureTag.getText().toString().toUpperCase();
                                final String picdet= pictureDetails.getText().toString();

                                DocumentReference documentReference = fstorepicture.collection("PICTURE").document();
                                Map<String, Object> pdf = new HashMap<>();
                                pdf.put("Details", picdet);
                                pdf.put("Tag", pictg);
                                pdf.put("Username", name);
                                pdf.put("UserID", uid);
                                pdf.put("Downloadlink", url);

                                documentReference.set(pdf).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "onSuccess: post added");
                                    }
                                });
                                Toast.makeText(Addpicture.this, "Uploaded Successfully ", Toast.LENGTH_SHORT).show();
                            } else {
                                dialog.dismiss();
                                Toast.makeText(Addpicture.this, "UploadedFailed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                startActivity(new Intent(getApplicationContext(), Allpicture.class));
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK
                && data != null && data.getData() != null) {

            ImageUri = data.getData();
            try {

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), ImageUri);
                imageView.setImageBitmap(bitmap);
            }

            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}