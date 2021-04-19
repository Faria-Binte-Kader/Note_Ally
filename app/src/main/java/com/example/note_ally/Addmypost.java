package com.example.note_ally;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class Addmypost extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String TAG = "TAG Addpost";
    Button addmypostBtn;
    String uid,name;

    FirebaseFirestore fstorepost;
    FirebaseAuth fAuthpost;

    private EditText postTag, postDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addmypost);

        addmypostBtn = findViewById(R.id.add_post_btn);
        postDetails = findViewById(R.id.addpostdetails);
        postTag = findViewById(R.id.addposttag);

        fAuthpost = FirebaseAuth.getInstance();
        fstorepost = FirebaseFirestore.getInstance();
        uid = fAuthpost.getCurrentUser().getUid();

        DocumentReference documentReference = fstorepost.collection("User").document(uid);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null) {
                    name= value.getString("Name");

                }
            }
        });

        addmypostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String postdetails = postDetails.getText().toString();
                final String posttag = postTag.getText().toString().toUpperCase();

                DocumentReference documentReference = fstorepost.collection("FeedPost").document();
                Map<String, Object> feedpost = new HashMap<>();
                feedpost .put("Username", name);
                feedpost .put("UserID", uid);
                feedpost .put("Details", postdetails);
                feedpost .put("Tag", posttag);
                feedpost .put("Like", "0");
                feedpost .put("Dislike", "0");
                feedpost .put("Report", "0");
                feedpost.put("PostID", documentReference.getId());

                documentReference.set(feedpost).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "onSuccess: post added");
                        Toast.makeText(Addmypost.this, "Posted!", Toast.LENGTH_SHORT).show();
                    }
                });

                startActivity(new Intent(getApplicationContext(), Mypost.class));
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