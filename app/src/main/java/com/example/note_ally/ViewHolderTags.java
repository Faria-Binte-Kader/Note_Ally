package com.example.note_ally;


import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolderTags extends RecyclerView.ViewHolder{
    public TextView  tagname;
    public Button tagsave;

    public ViewHolderTags(@NonNull View itemView) {
        super(itemView);
        tagname = itemView.findViewById(R.id.tagname);
        tagsave = itemView.findViewById(R.id.savetag);
    }
}