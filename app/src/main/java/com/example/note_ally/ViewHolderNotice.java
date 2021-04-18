package com.example.note_ally;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolderNotice extends RecyclerView.ViewHolder {

    public TextView intitution, details, tag;

    public ViewHolderNotice(@NonNull View itemView) {
        super(itemView);
        intitution = itemView.findViewById(R.id.institutionTextView);
        details = itemView.findViewById(R.id.detailsTextView4);
    }

}