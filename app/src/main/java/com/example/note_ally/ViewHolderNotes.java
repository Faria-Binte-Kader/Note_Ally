package com.example.note_ally;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolderNotes extends RecyclerView.ViewHolder {

    public TextView title, details;

    public ViewHolderNotes(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.titleTextView);
        details = itemView.findViewById(R.id.detailsTextView5);
    }

}