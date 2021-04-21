package com.example.note_ally;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Viewholderpicture extends RecyclerView.ViewHolder{

    TextView picturename;
    ImageView pictureview;
    TextView details;

    public Viewholderpicture(@NonNull View itemView) {
        super(itemView);
        picturename=itemView.findViewById(R.id.pictureuser);
        details=itemView.findViewById(R.id.pituredetails);
        pictureview=itemView.findViewById(R.id.pictureview);


    }
}
