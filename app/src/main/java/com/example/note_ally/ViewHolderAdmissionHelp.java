package com.example.note_ally;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolderAdmissionHelp extends RecyclerView.ViewHolder {

    public TextView intitutionsubject, details, tag;

    public ViewHolderAdmissionHelp(@NonNull View itemView) {
        super(itemView);
        intitutionsubject = itemView.findViewById(R.id.institutionsubjectTextView);
        details = itemView.findViewById(R.id.detailsTextView3);
    }

}