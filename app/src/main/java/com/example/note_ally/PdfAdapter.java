package com.example.note_ally;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class PdfAdapter extends RecyclerView.Adapter<Viewholderpdf>{

    Allpdf allpdf;
    ArrayList<Modelpdf>modelpdfs;

    public PdfAdapter(Allpdf allpdf, ArrayList<Modelpdf>modelpdfs )
    {
        this.allpdf = allpdf;
        this.modelpdfs = modelpdfs;
    }


    @NonNull
    @Override
    public Viewholderpdf onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(allpdf.getBaseContext());
        View view = layoutInflater.inflate(R.layout.pdf_list, null, false);

        return new Viewholderpdf(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholderpdf holder, int position) {
        holder.details.setText(modelpdfs.get(position).getdetails());
        holder.pdfname.setText(modelpdfs.get(position).getUsername());
        holder.downloadLink.setText(modelpdfs.get(position).getDownloadlink());
        holder.mDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadFile(holder.details.getContext(),modelpdfs.get(position).getdetails(),".pdf",DIRECTORY_DOWNLOADS,modelpdfs.get(position).getDownloadlink());
            }
        });
    }

    public void downloadFile(Context context, String fileName, String fileExtension, String destinationDirectory, String url) {

        DownloadManager downloadmanager = (DownloadManager) context.
                getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context, destinationDirectory, fileName + fileExtension);

        downloadmanager.enqueue(request);
    }

    @Override
    public int getItemCount() {
        return modelpdfs.size();
    }


}
