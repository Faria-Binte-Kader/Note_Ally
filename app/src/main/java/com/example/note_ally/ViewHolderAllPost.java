package com.example.note_ally;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolderAllPost extends RecyclerView.ViewHolder{
    public TextView  postdetails, username, dislikeno, likeno;
    public ImageView chat, report, like, dislike;

    public ViewHolderAllPost(@NonNull View itemView, final AllpostAdapter.OnItemClickListener listener) {
        super(itemView);
        postdetails = itemView.findViewById(R.id.postdetails);
        chat = itemView.findViewById(R.id.postchat);
        report = itemView.findViewById(R.id.postreport);
        like = itemView.findViewById(R.id.postlike);
        dislike = itemView.findViewById(R.id.postdislike);
        username = itemView.findViewById(R.id.postusername);
        likeno = itemView.findViewById(R.id.allpostlikenumber);
        dislikeno = itemView.findViewById(R.id.allpostdislikenumber);

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener!=null) {
                    int position = getAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION) {
                        listener.onReportClick(position);

                    }
                }
            }
        });

    }
}