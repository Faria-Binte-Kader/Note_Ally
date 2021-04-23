package com.example.note_ally;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolderNotes extends RecyclerView.ViewHolder {

    public TextView title, details;
    public ImageView delete;

    public ViewHolderNotes(@NonNull View itemView, final NotesAdapter.OnItemClickListener listener) {
        super(itemView);
        title = itemView.findViewById(R.id.titleTextView);
        details = itemView.findViewById(R.id.detailsTextView5);
        delete = itemView.findViewById(R.id.notedelete);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener!=null) {
                    int position = getAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION) {
                        listener.onDeleteClick(position);

                    }
                }
            }
        });
    }

}