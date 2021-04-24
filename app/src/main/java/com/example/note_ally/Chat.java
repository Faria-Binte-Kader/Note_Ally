package com.example.note_ally;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Chat extends AppCompatActivity {

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String fUser;

    private ArrayList<MessageList> usersList;

    RecyclerView recyclerView;
    ChatUserAdapter adapter;

    public static final String EXTRA_TEXT1 = "com.example.application.example.EXTRA_TEXT1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        recyclerView = findViewById(R.id.recycler_view_chat);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser().getUid();
        fStore = FirebaseFirestore.getInstance();

        usersList = new ArrayList<>();

        fStore.collection("ChatList")
                .document(fUser)
                .collection(fUser)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    String recentUserId,recentUsername;

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (usersList.size() > 0)
                            usersList.clear();
                        for (DocumentSnapshot querySnapshot : task.getResult()) {
                            recentUserId = querySnapshot.getString("id");
                            recentUsername=querySnapshot.getString("Name");

                            Log.v("TAG ---I---onComplete", fUser);
                            MessageList messageList = new MessageList(recentUserId,recentUsername);
                            usersList.add(messageList);
                        }
                        //chatList(recentUserId);
                        adapter= new ChatUserAdapter(Chat.this,usersList);
                        recyclerView.setAdapter(adapter);
                    }
                });
    }

    public void goToChat(String s) {
        Intent intent = new Intent(Chat.this, MessageActivity.class);
        intent.putExtra(EXTRA_TEXT1, s);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    /*private void chatList(String recentUserId) {
        final String[] username = new String[1];
        FirebaseFirestore.getInstance().collection("User").document(recentUserId)
                .addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(value!=null)
                           username[0] = value.getString("Name");
                    }
                });
        Log.d("TAG Chat", username[0]);
        MessageList messageList = new MessageList(recentUserId,username[0]);
        usersList.add(messageList);
    }*/
    /*private void chatList(String recentUserId) {
        FirebaseFirestore.getInstance().collection("User").document(recentUserId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    String name,email,gender,description,dateofbirth,phone;
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (usersList.size() > 0)
                            usersList.clear();
                        for (DocumentSnapshot querySnapshot : task.getResult()) {
                            name = querySnapshot.getString("Name");
                            email=querySnapshot.getString("Email");
                            gender=querySnapshot.getString("Gender");
                            description=querySnapshot.getString("Description");
                            dateofbirth=querySnapshot.getString("DOB");
                            phone=querySnapshot.getString("Phone");

                            User user = new User(name,email,gender,description,dateofbirth,phone);
                            usersList.add(user);
                        }
                    }
                });
                adapter= new ChatUserAdapter(Chat.this,usersList);
                        recyclerView.setAdapter(adapter);
    }*/
}