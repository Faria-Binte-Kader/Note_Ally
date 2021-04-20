package com.example.note_ally;

import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;

public class AllpostAdapter extends RecyclerView.Adapter<ViewHolderAllPost> {

    Allpost allpost;
    ArrayList<Feedpost> allpostArrayList;


    public AllpostAdapter(Allpost allpost, ArrayList<Feedpost> allpostArrayList) {
        this.allpost= allpost;
        this.allpostArrayList = allpostArrayList;
    }

    @NonNull
    @Override
    public ViewHolderAllPost onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater= LayoutInflater.from(allpost.getBaseContext());
        View view= layoutInflater.inflate(R.layout.allpostlist,parent,false);
        return new ViewHolderAllPost(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolderAllPost holder, int position) {
        holder.postdetails.setText(allpostArrayList.get(position).getdetails());
        holder.username.setText(allpostArrayList.get(position).getUsername());
        holder.likeno.setText(allpostArrayList.get(position).getLike());
        holder.dislikeno.setText(allpostArrayList.get(position).getDisike());

        //final String id= allpostArrayList.get(position).getPostID();


        /*holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth fAuthpost;
                final FirebaseFirestore fStorepost;
                String userIDpost;
                fAuthpost = FirebaseAuth.getInstance();
                fStorepost = FirebaseFirestore.getInstance();
                userIDpost = fAuthpost.getCurrentUser().getUid();
                DocumentReference documentReference = fStorepost.collection("Doctor").document(userIDpost);
                documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(value!=null)
                        {String namepost=value.getString("Name");
                            fStorepost.collection("Questionanswer").document(id)
                                    .update("Doctor",namepost)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d("TAG","Success");
                                        }
                                    });
                        }}
                });
                Editable an=holder.answer.getText();
                fStorepost.collection("Questionanswer").document(id)
                        .update("Answer",an.toString())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("TAG","Success");
                            }
                        });
            }
        });*/

    }


    @Override
    public int getItemCount() {
        return allpostArrayList.size();
    }
}