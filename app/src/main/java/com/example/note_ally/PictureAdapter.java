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

public class PictureAdapter extends RecyclerView.Adapter<Viewholderpicture>{

    Allpicture allpicture;
    ArrayList<Modelpicture>modelpictures;

    public PictureAdapter(Allpicture allpicture, ArrayList<Modelpicture>modelpictures)
    {
        this.allpicture = allpicture;
        this.modelpictures = modelpictures;
    }


    @NonNull
    @Override
    public Viewholderpicture onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(allpicture.getBaseContext());
        View view = layoutInflater.inflate(R.layout.picture_list, null, false);

        return new Viewholderpicture(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholderpicture holder, int position) {
        holder.details.setText(modelpictures.get(position).getdetails());
        holder.picturename.setText(modelpictures.get(position).getUsername());
        String imguri=null;
        imguri=modelpictures.get(position).getDownloadlink();
        Picasso.get().load(imguri).into(holder.pictureview);

    }


    @Override
    public int getItemCount() {
        return modelpictures.size();
    }


}
