package com.example.note_ally;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolderEvent extends RecyclerView.ViewHolder {

    public TextView organizer, location, details, tag;

    public ViewHolderEvent(@NonNull View itemView) {
        super(itemView);
        organizer = itemView.findViewById(R.id.organizerTextView);
        location = itemView.findViewById(R.id.locationTextView);
        details = itemView.findViewById(R.id.detailsTextView2);

    }

}