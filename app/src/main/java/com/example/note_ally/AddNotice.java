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

public class AddNotice extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String TAG = "TAG AddNotice";
    Button addNoticeBtn;

    FirebaseFirestore fstoreNotice;

    private EditText noticeInstitution, noticeDetails, noticeTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notice);

        addNoticeBtn = findViewById(R.id.add_notice_btn);
        noticeInstitution = findViewById(R.id.addNoticeInstitution);
        noticeDetails = findViewById(R.id.addNoticeDetails);
        noticeTag = findViewById(R.id.addNoticeTag);

        fstoreNotice = FirebaseFirestore.getInstance();

        addNoticeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String noticeinstitution = noticeInstitution.getText().toString().toUpperCase();
                final String noticedetails = noticeDetails.getText().toString().toUpperCase();
                final String noticetag = noticeTag.getText().toString().toUpperCase();

                DocumentReference documentReference = fstoreNotice.collection("Notice").document();
                Map<String, Object> notice = new HashMap<>();
                notice.put("Institution", noticeinstitution);
                notice.put("Details", noticedetails);
                notice.put("Tag", noticetag);

                documentReference.set(notice).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "onSuccess: notice added");
                    }
                });

                startActivity(new Intent(getApplicationContext(), FindNotice.class));
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