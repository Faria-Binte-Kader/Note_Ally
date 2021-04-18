package com.example.note_ally;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;

public class Updateprofile extends AppCompatActivity {
    Button updatebtn;
    private EditText name, email,oldpass, newpass, conpass,description, phone;
    FirebaseAuth fAuthtc;
    private FirebaseUser user;
    FirebaseFirestore fStore;
    String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateprofile);

        name = findViewById(R.id.updatename);
        email = findViewById(R.id.mailuser);
        oldpass = findViewById(R.id.oldpass);
        newpass = findViewById(R.id.updatepass);
        conpass= findViewById(R.id.confirmupdatepass);
        description = findViewById(R.id.updatedesc);
        phone = findViewById(R.id.updatephone);
        fAuthtc = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuthtc.getCurrentUser().getUid();
        updatebtn = findViewById(R.id.Update_button);


        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Updateuser();
            }
        });
    }

    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }

    private void Updateuser() {
        final String nam = name.getText().toString();
        final String mail = email.getText().toString();
        final String oldpassword = oldpass.getText().toString();
        final String Password = newpass.getText().toString();
        String conPassword = conpass.getText().toString();
        final String desc = description.getText().toString();
        final String phon = phone.getText().toString();

        if ( !nam.isEmpty() && nam.length() < 7) {
            showError(name, "Your Name is not valid");
            return;
        }
        if((mail.isEmpty() || oldpassword.isEmpty()) && !Password.isEmpty())
        {  showError(email, "Please input mail and old password");
            return;
        }
        if (!Password.isEmpty() && Password.length() < 7) {
            showError(newpass, "Password must be at least 7 characters");
            return;
        }
        if ((conPassword.isEmpty() || !conPassword.equals(Password))&& !Password.isEmpty()) {
            showError(conpass, "Password does not match");
            return;
        }

        if(!mail.isEmpty() && !oldpassword.isEmpty() && !conPassword.isEmpty())
        {

            user = FirebaseAuth.getInstance().getCurrentUser();
            AuthCredential credential = EmailAuthProvider.getCredential(mail,oldpassword);

            user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        user.updatePassword(Password).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                View coordinatorLayout;
                                if(!task.isSuccessful()){
                                    Log.d("TAG", "Error");
                                }else {
                                    Toast.makeText(Updateprofile.this, "Password changed ", Toast.LENGTH_SHORT).show();
                                    oldpass.setText("");
                                    newpass.setText("");
                                    conpass.setText("");
                                    email.setText("");

                                }
                            }
                        });
                    }else {
                        Toast.makeText(Updateprofile.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }

        if(!nam.isEmpty())
        {
            fStore.collection("User").document(userID)
                    .update("Name",nam.toUpperCase())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(Updateprofile.this, "Updated Name", Toast.LENGTH_SHORT).show();
                            name.setText("");
                        }
                    });
        }

        if(!desc.isEmpty())
        {
            fStore.collection("User").document(userID)
                    .update("Description",desc)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(Updateprofile.this, "Updated Description", Toast.LENGTH_SHORT).show();
                            description.setText("");
                        }
                    });
        }

        if(!phon.isEmpty())
        {
            fStore.collection("User").document(userID)
                    .update("Phone",phon)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(Updateprofile.this, "Updated phone number", Toast.LENGTH_SHORT).show();
                           phone.setText("");
                        }
                    });
        }


    }


}