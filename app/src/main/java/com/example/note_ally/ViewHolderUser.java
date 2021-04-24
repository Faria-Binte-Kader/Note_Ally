package com.example.note_ally;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolderUser extends RecyclerView.ViewHolder {

    public TextView username;
    public ImageView profile_image;

    public ViewHolderUser(@NonNull View itemView) {
        super(itemView);
        username= itemView.findViewById(R.id.show_username_userList);
        profile_image = itemView.findViewById(R.id.imageView_userList);
    }
}
