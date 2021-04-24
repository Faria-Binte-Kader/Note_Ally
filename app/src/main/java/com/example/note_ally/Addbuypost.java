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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Addbuypost extends AppCompatActivity {
    public static final int PICK_IMAGE = 1;
    public static final String TAG = "TAG Addbuy";
    Button addbuyBtn, choosepicturebtn;
    String uid,name;
    ImageView imageView;
    ProgressDialog dialog;

    private Uri ImageUri;
    Task<Uri> downloadUri;
    String url;


    FirebaseFirestore fstorebuy;
    FirebaseAuth fAuthbuy;

    private EditText buyTag, buyDetails, buyname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addbuypost);

        addbuyBtn = findViewById(R.id.add_buypost_btn);
        choosepicturebtn = findViewById(R.id.choosebtn2);
        buyDetails = findViewById(R.id.addbuypostdetails);
        buyTag = findViewById(R.id.addbuyposttag);
        buyname = findViewById(R.id.addproductname);
        imageView = findViewById(R.id.showimage2);

        fAuthbuy = FirebaseAuth.getInstance();
        fstorebuy = FirebaseFirestore.getInstance();
        uid = fAuthbuy.getCurrentUser().getUid();

        DocumentReference documentReference = fstorebuy.collection("User").document(uid);
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
                if (ActivityCompat.checkSelfPermission(Addbuypost.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Addbuypost.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            100);
                }
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");

                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(
                        Intent.createChooser(
                                intent,
                                "Select Image from here..."),
                        PICK_IMAGE);
            }
        });
        addbuyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new ProgressDialog(Addbuypost.this);
                dialog.setMessage("Uploading");
                dialog.show();
                if (ImageUri != null) {
                    StorageReference storageReference= FirebaseStorage.getInstance().getReference().child("images/" + UUID.randomUUID().toString());
                    Toast.makeText(Addbuypost.this, storageReference.getName(), Toast.LENGTH_SHORT).show();
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
                                final String pictg = buyTag.getText().toString().toUpperCase();
                                final String picdet= buyDetails.getText().toString();
                                final String proname= buyname.getText().toString();

                                DocumentReference documentReference = fstorebuy.collection("BUYSELLPOSTS").document();
                                Map<String, Object> pdf = new HashMap<>();
                                pdf.put("Details", picdet);
                                pdf.put("Tag", pictg);
                                pdf.put("ProductName",proname);
                                pdf.put("Username", name);
                                pdf.put("UserID", uid);
                                pdf.put("Downloadlink", url);
                                pdf.put("PostID", documentReference.getId());

                                documentReference.set(pdf).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "onSuccess: post added");
                                    }
                                });
                                Toast.makeText(Addbuypost.this, "Uploaded Successfully ", Toast.LENGTH_SHORT).show();
                            } else {
                                dialog.dismiss();
                                Toast.makeText(Addbuypost.this, "UploadedFailed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                startActivity(new Intent(getApplicationContext(), Allbuypost.class));
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