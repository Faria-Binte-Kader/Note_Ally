package com.example.note_ally;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Viewholderpdf extends RecyclerView.ViewHolder{

    TextView pdfname;TextView downloadLink, pronam;
    TextView details;
    Button mDownload;
    public Viewholderpdf(@NonNull View itemView) {
        super(itemView);
        pdfname=itemView.findViewById(R.id.pdfname);
        details=itemView.findViewById(R.id.pdfdetails);
        downloadLink=itemView.findViewById(R.id.pdfdownloadlink);
        mDownload=itemView.findViewById(R.id.downloadpdf);
        pronam=itemView.findViewById(R.id.pdfproductname);

    }
}
