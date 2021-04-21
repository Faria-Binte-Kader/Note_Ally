package com.example.note_ally;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class Addpicture extends AppCompatActivity {
    public static final int PICK_IMAGE = 1;
    Button addpictureBtn, choosepicturebtn;
    String uid,name;
    TextView picselectno;
    ArrayList<Uri> imagelist = new ArrayList<Uri>();
    private Uri ImageUri;
    private int upload_count=0;

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
        picselectno=findViewById(R.id.picselectno);


        fAuthpicture = FirebaseAuth.getInstance();
        fstorepicture = FirebaseFirestore.getInstance();
        uid = fAuthpicture.getCurrentUser().getUid();

        choosepicturebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ActivityCompat.checkSelfPermission(Addpicture.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(Addpicture.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            100);
                }
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(intent,PICK_IMAGE);
            }
        });
        /*addpictureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StorageReference imagefolder = FirebaseStorage.getInstance().getReference().child("ImageFolder");
                for(upload_count=0; upload_count<imagelist.size(); upload_count++)
                { Uri individualimage = imagelist.get(upload_count);
                  StorageReference imagename = imagefolder.child("Image"+individualimage.getLastPathSegment());
                imagename.putFile(individualimage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                      imagename.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                          @Override
                          public void onSuccess(Uri uri) {
                              String url = String.valueOf(uri);
                              Storelink(url);
                          }
                      });
                    }
                });

                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== PICK_IMAGE)
        {
            if(resultCode==RESULT_OK)
            {
                if(data.getClipData()!=null)
                {int countClipData = data.getClipData().getItemCount();
                int currentImageSelect = 0;
                while(currentImageSelect<countClipData)
                {
                    ImageUri=data.getClipData().getItemAt(currentImageSelect).getUri();
                    imagelist.add(ImageUri);
                    currentImageSelect = currentImageSelect+1;
                }
                }
                picselectno.setVisibility(View.VISIBLE);
                picselectno.setText("You have selected "+ imagelist.size()+" images");

            }
            else{
                Toast.makeText(this, "Please select at least two images", Toast.LENGTH_SHORT).show();
            }
        }
    }

private void Storelink(String url)
{

}*/
}}