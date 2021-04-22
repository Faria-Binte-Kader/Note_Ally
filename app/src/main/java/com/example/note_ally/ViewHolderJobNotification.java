package com.example.note_ally;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolderJobNotification extends RecyclerView.ViewHolder{
    public TextView  njcompany, njposition, njdetails;

    public ViewHolderJobNotification(@NonNull View itemView) {
        super(itemView);
        njcompany = itemView.findViewById(R.id.companynotifTextView);
        njposition = itemView.findViewById(R.id.positionnotifTextView);
        njdetails = itemView.findViewById(R.id.detailsnotifTextView);
    }
}