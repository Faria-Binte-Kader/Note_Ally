package com.example.note_ally;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolderMessage extends RecyclerView.ViewHolder {

    public TextView show_msg;
    public ImageView profile_image;

    public ViewHolderMessage(@NonNull View itemView) {
        super(itemView);
        show_msg = itemView.findViewById(R.id.show_message);
        //profile_image = itemView.findViewById(R.id.imageView_messageList);
    }
}
