package com.example.note_ally;

import android.content.Intent;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JobNotificationAdapter extends RecyclerView.Adapter<ViewHolderJobNotification> {

    Notification notification;
    ArrayList<JobNotification> jobNotificationArrayList;


    public JobNotificationAdapter(Notification notification, ArrayList<JobNotification> jobNotificationArrayList) {
        this.notification = notification;
        this.jobNotificationArrayList = jobNotificationArrayList;
    }

    @NonNull
    @Override
    public ViewHolderJobNotification onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater= LayoutInflater.from(notification.getBaseContext());
        View view= layoutInflater.inflate(R.layout.job_notification_list,parent,false);
        return new ViewHolderJobNotification(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolderJobNotification holder, int position) {
        holder.njcompany.setText(jobNotificationArrayList.get(position).getNjcompany());
        holder.njposition.setText(jobNotificationArrayList.get(position).getNjposition());
        holder.njdetails.setText(jobNotificationArrayList.get(position).getNjdetails());
    }


    @Override
    public int getItemCount() {
        return jobNotificationArrayList.size();
    }
}