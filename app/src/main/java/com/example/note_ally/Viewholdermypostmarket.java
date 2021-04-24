package com.example.note_ally;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Viewholdermypostmarket extends RecyclerView.ViewHolder{

    TextView productname;
    ImageView pictureview;
    TextView details;
    Button deletemarket;

    public Viewholdermypostmarket(@NonNull View itemView) {
        super(itemView);
        productname=itemView.findViewById(R.id.productnamemarket);
        details=itemView.findViewById(R.id.detailsmarket);
        pictureview=itemView.findViewById(R.id.pictureview3);
        deletemarket = itemView.findViewById(R.id.deletemarket);


    }
}
