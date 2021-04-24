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

public class MypostmarketAdapter extends RecyclerView.Adapter<Viewholdermypostmarket>{

   Mypostmarket mypostmarket;
    ArrayList<Buypost>modelpictures;

    public MypostmarketAdapter(Mypostmarket mypostmarket, ArrayList<Buypost>modelpictures)
    {
        this.mypostmarket = mypostmarket;
        this.modelpictures = modelpictures;
    }


    @NonNull
    @Override
    public Viewholdermypostmarket onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mypostmarket.getBaseContext());
        View view = layoutInflater.inflate(R.layout.mypostmarket_list, null, false);

        return new Viewholdermypostmarket(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholdermypostmarket holder, int position) {
        holder.details.setText(modelpictures.get(position).getdetails());
        holder.productname.setText(modelpictures.get(position).getProductname());
        String imguri=null;
        imguri=modelpictures.get(position).getDownloadlink();
        Picasso.get().load(imguri).into(holder.pictureview);
    }


    @Override
    public int getItemCount() {
        return modelpictures.size();
    }


}
