package com.example.note_ally;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolderJob extends RecyclerView.ViewHolder {

    public TextView company, position, details, tag;
    public ImageView report;

    public ViewHolderJob(@NonNull View itemView, final JobAdapter.OnItemClickListener listener) {
        super(itemView);
        company = itemView.findViewById(R.id.companyTextView);
        position = itemView.findViewById(R.id.positionTextView);
        details = itemView.findViewById(R.id.detailsTextView);
        report = itemView.findViewById(R.id.jobreport);

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