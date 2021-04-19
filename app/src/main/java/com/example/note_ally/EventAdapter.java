package com.example.note_ally;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<ViewHolderEvent> implements AdapterView.OnItemSelectedListener {

    FindEvent findEvent;
    ArrayList<Event> eventArrayList;

    public EventAdapter(FindEvent findEvent, ArrayList<Event> eventArrayList) {
        this.findEvent = findEvent;
        this.eventArrayList = eventArrayList;
    }

    @NonNull
    @Override
    public ViewHolderEvent onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(findEvent.getBaseContext());
        View view = layoutInflater.inflate(R.layout.event_list, parent, false);
        return new ViewHolderEvent(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderEvent holder, final int position) {
        holder.organizer.setText(eventArrayList.get(position).getOrganizer());
        holder.location.setText(eventArrayList.get(position).getLocation());
        holder.details.setText(eventArrayList.get(position).getDetails());
    }

    @Override
    public int getItemCount() {
        return eventArrayList.size();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

}