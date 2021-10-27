package com.example.note_ally;

import android.content.Intent;
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
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TagsAdapter extends RecyclerView.Adapter<ViewHolderTags> {

    FindTags findTags;
    ArrayList<Tags> tagsArrayList;


    public TagsAdapter(FindTags findTags, ArrayList<Tags> tagsArrayList) {
        this.findTags = findTags;
        this.tagsArrayList = tagsArrayList;
    }

    @NonNull
    @Override
    public ViewHolderTags onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater= LayoutInflater.from(findTags.getBaseContext());
        View view= layoutInflater.inflate(R.layout.tags_list,parent,false);
        return new ViewHolderTags(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolderTags holder, int position) {
        holder.tagname.setText(tagsArrayList.get(position).getTagname());

        final String id = tagsArrayList.get(position).getPostID();
        final String tagname = tagsArrayList.get(position).getTagname();

        holder.tagsave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                FirebaseAuth fAuthtag;
                final FirebaseFirestore fStoretag;
                String userIDtag;

                fAuthtag = FirebaseAuth.getInstance();
                fStoretag = FirebaseFirestore.getInstance();
                userIDtag = fAuthtag.getCurrentUser().getUid();

                DocumentReference documentReference = fStoretag.collection("SavedTags").document(tagname).collection("Tags").document(userIDtag);

                Map<String, Object> usertag = new HashMap<>();
                usertag.put("Jobtag", tagname);
                usertag.put("UserID", userIDtag);

                documentReference.set(usertag).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG","Success");
                    }
                });
            }
        });

    }


    @Override
    public int getItemCount() {
        return tagsArrayList.size();
    }
}