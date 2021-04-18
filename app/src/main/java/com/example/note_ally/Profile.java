package com.example.note_ally;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Profile extends AppCompatActivity {

    private TextView name, description, email, dob, gender, phone;
    private Button updateBtn;
    FirebaseAuth fAuthTestCenter;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name = findViewById(R.id.username);
        gender = findViewById(R.id.usergender);
        description = findViewById(R.id.userdescription);
        email = findViewById(R.id.useremail);
        phone = findViewById(R.id.userphone);
        dob = findViewById(R.id.userdob);
        updateBtn = (Button) findViewById(R.id.update_btn);

        fAuthTestCenter = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuthTestCenter.getCurrentUser().getUid();

        DocumentReference documentReference = fStore.collection("User").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null) {
                    name.setText(value.getString("Name"));
                    gender.setText(value.getString("Gender"));
                    description.setText(value.getString("Description"));
                    email.setText(value.getString("Email"));
                    dob.setText(value.getString("DOB"));
                    phone.setText(value.getString("Phone"));

                }
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Profile.this, Updateprofile.class));
            }
        });
    }
}

