package com.example.note_ally;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class BuypostAdapter extends RecyclerView.Adapter<Viewholderbuypost>{

    Allbuypost allbuypost;
    ArrayList<Buypost>modelpictures;

    public BuypostAdapter(Allbuypost allbuypost, ArrayList<Buypost>modelpictures)
    {
        this.allbuypost = allbuypost;
        this.modelpictures = modelpictures;
    }


    @NonNull
    @Override
    public Viewholderbuypost onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(allbuypost.getBaseContext());
        View view = layoutInflater.inflate(R.layout.buypost_list, null, false);

        return new Viewholderbuypost(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholderbuypost holder, int position) {
        holder.details.setText(modelpictures.get(position).getdetails());
        holder.productname.setText(modelpictures.get(position).getProductname());
        holder.username.setText(modelpictures.get(position).getUsername());
        String imguri=null;
        imguri=modelpictures.get(position).getDownloadlink();
        Picasso.get().load(imguri).into(holder.pictureview);
        holder.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadFile(holder.productname.getContext(),modelpictures.get(position).getdetails(),".jpg",DIRECTORY_DOWNLOADS,modelpictures.get(position).getDownloadlink());
            }
        });
        holder.chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = modelpictures.get(position).getUserId();
                allbuypost.chat(message);
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
        return modelpictures.size();
    }


}
