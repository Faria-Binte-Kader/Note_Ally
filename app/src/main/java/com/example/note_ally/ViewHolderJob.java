package com.example.note_ally;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolderJob extends RecyclerView.ViewHolder {

    public TextView company, position, details, tag;

    public ViewHolderJob(@NonNull View itemView) {
        super(itemView);
        company = itemView.findViewById(R.id.companyTextView);
        position = itemView.findViewById(R.id.positionTextView);
        details = itemView.findViewById(R.id.detailsTextView);
    }

}