package com.example.note_ally;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class JobAdapter extends RecyclerView.Adapter<ViewHolderJob> implements AdapterView.OnItemSelectedListener {

    FindJob findJob;
    ArrayList<Job> jobArrayList;

    public JobAdapter(FindJob findJob, ArrayList<Job> jobArrayList) {
        this.findJob = findJob;
        this.jobArrayList = jobArrayList;
    }

    @NonNull
    @Override
    public ViewHolderJob onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(findJob.getBaseContext());
        View view = layoutInflater.inflate(R.layout.job_list, parent, false);
        return new ViewHolderJob(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderJob holder, final int position) {
        holder.company.setText(jobArrayList.get(position).getCompany());
        holder.position.setText(jobArrayList.get(position).getPosition());
        holder.details.setText(jobArrayList.get(position).getDetails());
    }

    @Override
    public int getItemCount() {
        return jobArrayList.size();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

}