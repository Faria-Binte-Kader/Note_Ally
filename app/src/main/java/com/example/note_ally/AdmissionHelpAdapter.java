package com.example.note_ally;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdmissionHelpAdapter extends RecyclerView.Adapter<ViewHolderAdmissionHelp> implements AdapterView.OnItemSelectedListener {

    FindHelp findHelp;
    ArrayList<AdmissionHelp> helpArrayList;

    public AdmissionHelpAdapter(FindHelp findHelp, ArrayList<AdmissionHelp> helpArrayList) {
        this.findHelp = findHelp;
        this.helpArrayList = helpArrayList;
    }

    @NonNull
    @Override
    public ViewHolderAdmissionHelp onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(findHelp.getBaseContext());
        View view = layoutInflater.inflate(R.layout.help_list, parent, false);
        return new ViewHolderAdmissionHelp(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderAdmissionHelp holder, final int position) {
        holder.intitutionsubject.setText(helpArrayList.get(position).getInstitutionsubject());
        holder.details.setText(helpArrayList.get(position).getDetails());
    }

    @Override
    public int getItemCount() {
        return helpArrayList.size();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

}