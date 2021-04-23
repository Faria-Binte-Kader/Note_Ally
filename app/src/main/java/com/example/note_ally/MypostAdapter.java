package com.example.note_ally;

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

public class MypostAdapter extends RecyclerView.Adapter<ViewHolderMyPost> {

    Mypost mypost;
    ArrayList<Feedpost> mypostArrayList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public MypostAdapter(Mypost mypost, ArrayList<Feedpost> mypostArrayList) {
        this.mypost= mypost;
        this.mypostArrayList = mypostArrayList;
    }

    @NonNull
    @Override
    public ViewHolderMyPost onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater= LayoutInflater.from(mypost.getBaseContext());
        View view= layoutInflater.inflate(R.layout.mypostlist,parent,false);
        return new ViewHolderMyPost(view, mListener);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolderMyPost holder, int position) {
        holder.postdetails.setText(mypostArrayList.get(position).getdetails());
        holder.username.setText(mypostArrayList.get(position).getUsername());
        holder.likeno.setText(mypostArrayList.get(position).getLike());
        holder.dislikeno.setText(mypostArrayList.get(position).getDisike());

        /*final String id= mypostArrayList.get(position).getPostID();


        holder.delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FirebaseAuth fAuthpost;
                final FirebaseFirestore fStorepost;
                String userIDpost;

                fAuthpost = FirebaseAuth.getInstance();
                fStorepost = FirebaseFirestore.getInstance();
                userIDpost = fAuthpost.getCurrentUser().getUid();

                fStorepost.collection("FeedPost").document(id)
                        .delete()
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
        return mypostArrayList.size();
    }
}
