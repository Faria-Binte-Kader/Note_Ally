package com.example.note_ally;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Viewholderbuypost extends RecyclerView.ViewHolder{

    TextView productname, username;
    ImageView pictureview;
    TextView details;
    Button save;

    public Viewholderbuypost(@NonNull View itemView) {
        super(itemView);
        username=itemView.findViewById(R.id.usernamebuy);
        productname=itemView.findViewById(R.id.productname);
        details=itemView.findViewById(R.id.detailsbuy);
        pictureview=itemView.findViewById(R.id.pictureview1);
        save = itemView.findViewById(R.id.downloadpicture1);


    }
}
