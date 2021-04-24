package com.example.note_ally;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class login1 extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Button login1Btn;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    TextView signupbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            String user = FirebaseAuth.getInstance().getCurrentUser().getUid();
            DocumentReference documentReference2 = FirebaseFirestore.getInstance().collection("User").document(user);
            documentReference2.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    if (value != null)
                        startActivity(new Intent(login1.this, MainActivity.class));
                }
            });
            finish();
        }

        setContentView(R.layout.activity_login_firstpage);
        login1Btn = (Button) findViewById(R.id.loginbutton_loginfirst);
        signupbtn= findViewById(R.id.text_signup_loginfirst);
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotosignupbutton(v);
            }
        });
        login1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(login1.this, login.class));
            }});
}

    public void gotosignupbutton(View view) {
        Intent intent = new Intent(login1.this, signup.class);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
