package com.example.note_ally;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolderMyPost extends RecyclerView.ViewHolder{
    public TextView postdetails, username, likeno, dislikeno;
    public ImageView delete, like, dislike;

    public ViewHolderMyPost(@NonNull View itemView) {
        super(itemView);
        postdetails = itemView.findViewById(R.id.postmydetails);
        delete = itemView.findViewById(R.id.postdelete);
        likeno = itemView.findViewById(R.id.postlikenumber);
        dislikeno = itemView.findViewById(R.id.postdislikenumber);
        like = itemView.findViewById(R.id.mypostlike);
        dislike = itemView.findViewById(R.id.mypostdislike);
        username = itemView.findViewById(R.id.mypostusername);
    }
}
