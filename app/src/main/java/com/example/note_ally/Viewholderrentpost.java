package com.example.note_ally;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Viewholderrentpost extends RecyclerView.ViewHolder{

    TextView productname, username;
    ImageView pictureview;
    TextView details;
    ImageView save, chat;

    public Viewholderrentpost(@NonNull View itemView) {
        super(itemView);
        username=itemView.findViewById(R.id.usernamerent);
        productname=itemView.findViewById(R.id.productnamerent);
        details=itemView.findViewById(R.id.detailsrent);
        pictureview=itemView.findViewById(R.id.pictureview2);
        save = itemView.findViewById(R.id.downloadpicture2);
        chat = itemView.findViewById(R.id.rentpostchat);



    }
}
