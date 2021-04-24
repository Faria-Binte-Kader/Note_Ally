package com.example.note_ally;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MessageActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextView username;

    String fUser;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    Intent intent;

    androidx.recyclerview.widget.RecyclerView recyclerView;
    EditText msg_editText;
    ImageButton sendBtn;

    MessageAdapter messageAdapter;
    ArrayList<Message> messageArrayList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        username = findViewById(R.id.username_messageActivity);

        sendBtn = findViewById(R.id.btn_send_messageActivity);
        msg_editText = findViewById(R.id.editText_send_messageActivity);

        recyclerView=findViewById(R.id.recycler_view_messageActivity);
        recyclerView.setHasFixedSize(true);

        messageArrayList = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);


       Toolbar toolbar = findViewById(R.id.toolbar_messageActivity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        intent = getIntent();
        String userid = intent.getStringExtra(Allpost.EXTRA_TEXT1);

        fUser = fAuth.getCurrentUser().getUid();
        DocumentReference documentReference = fStore.collection("User").document(userid);

        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null) {
                    username.setText(value.getString("Name"));
                }
            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {

            Long time = System.nanoTime();
            //Long tsLong = System.currentTimeMillis();
            String ts = time.toString();

            @Override
            public void onClick(View view) {
                String msg= msg_editText.getText().toString();
                if(!msg.equals("")){
                    sendMessage(fUser,userid,msg,ts);
                }else{
                    Toast.makeText(MessageActivity.this, "Please send a non empty message", Toast.LENGTH_SHORT).show();
                }
                msg_editText.setText("");
            }
        });

        readMessage(fUser,userid);
    }

    private void sendMessage(String sender, String receiver, String message,String sentTime) {
        fUser = fAuth.getCurrentUser().getUid();
        intent = getIntent();
        String userid = intent.getStringExtra(Allpost.EXTRA_TEXT1);
        final String[] usernameuserid = new String[1];
        DocumentReference documentReferenceUserid = fStore.collection("User").document(userid);

        documentReferenceUserid.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null) {
                    usernameuserid[0] = value.getString("Name");
                    Log.d("TAG MessageActivity","username"+ usernameuserid[0]);

                    DocumentReference documentReference1 = fStore.collection("ChatList").document(fAuth.getUid()).collection(fAuth.getUid()).document(userid);
                    Map<String,Object> recentChattedUser = new HashMap<>();
                    recentChattedUser.put("id",userid);
                    recentChattedUser.put("Name", usernameuserid[0]);
                    documentReference1.set(recentChattedUser).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("TAG MessageActivity", "onSuccess: Recent Chatted User is added");
                        }
                    });
                }
            }
        });

        final String[] usernamemyid = new String[1];
        DocumentReference documentReferenceMyid = fStore.collection("User").document(fAuth.getUid());
        documentReferenceMyid.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null) {
                    usernamemyid[0] = value.getString("Name");
                    Log.d("TAG MessageActivity","username"+ usernamemyid[0]);

                    DocumentReference documentReference2 = fStore.collection("ChatList").document(userid).collection(userid).document(fAuth.getUid());
                    Map<String,Object> recentChattedUser2 = new HashMap<>();
                    recentChattedUser2.put("id",fAuth.getUid());
                    recentChattedUser2.put("Name", usernamemyid[0]);
                    documentReference2.set(recentChattedUser2).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("TAG MessageActivity2", "onSuccess: Recent Chatted User2 is added");
                        }
                    });
                }
            }
        });

        DocumentReference documentReference = fStore.collection("Chats").document();
        Map<String, Object> chat = new HashMap<>();
        chat.put("Sender", sender);
        chat.put("Receiver", receiver);
        chat.put("Message", message);
        chat.put("SentTime",sentTime);
        documentReference.set(chat).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("TAG", "onSuccess: Message is sent");
            }
        });

        DocumentReference documentReference2 = fStore.collection("ChatList").document(fAuth.getUid());

        Map<String, Object> dummy = new HashMap<>();
        dummy.put("Dummy", "dummy");

        documentReference2.set(dummy).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("TAG MessageActivity", "onSuccess: Recent Chatted User is added");
            }

        });
    }

    private void readMessage(String myid,String userid){
        Log.v("TAG ---I---",myid);

        fStore.collection("Chats")
                .orderBy("SentTime")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    String sender,receiver,msg;

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(messageArrayList.size()>0)
                            messageArrayList.clear();
                        Log.v("TAG ---I---onComplete1",myid);
                        for(DocumentSnapshot querySnapshot:task.getResult()){
                            sender=querySnapshot.getString("Sender");
                            receiver=querySnapshot.getString("Receiver");
                            msg=querySnapshot.getString("Message");

                            Log.v("TAG ---I---onComplete",myid);
                            Message message = new Message(sender,receiver,msg);
                            if((message.getReceiver().equals(myid) && message.getSender().equals(userid))||
                                    (message.getReceiver().equals(userid) && message.getSender().equals(myid))){
                               messageArrayList.add(message);
                            }
                        }
                        messageAdapter = new MessageAdapter(MessageActivity.this, messageArrayList);
                        recyclerView.setAdapter(messageAdapter);
                        Log.v("TAG ---I---",myid);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MessageActivity.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
                        Log.v("TAG ---I---", e.getMessage());
                    }
                });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


   /* private void readMessage(String myid,String userid,String imageurl){
        messageArrayList=new ArrayList<>();

        if(messageArrayList.size()>0) messageArrayList.clear();
        fStore.collection("Chats")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    String sender,receiver,msg;

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(DocumentSnapshot querySnapshot:task.getResult()){
                            sender=querySnapshot.getString("Sender");
                            receiver=querySnapshot.getString("Receiver");
                            msg=querySnapshot.getString("Message");

                            Message message = new Message(sender,receiver,msg);
                            if(message.getReceiver().equals(myid) && message.getSender().equals(userid)||
                            message.getReceiver().equals(userid) && message.getSender().equals(myid)){
                               messageArrayList.add(message);
                            }
                        }
                        //messageAdapter = new MessageAdapter(MessageActivity.this, messageArrayList,imageurl);
                        messageAdapter = new MessageAdapter(MessageActivity.this, messageArrayList);
                        recyclerView.setAdapter(messageAdapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MessageActivity.this, "Problem ---I---", Toast.LENGTH_SHORT).show();
                        Log.v("---I---", e.getMessage());
                    }
                });
    }*/

}