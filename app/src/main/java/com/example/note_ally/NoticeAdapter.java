package com.example.note_ally;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NoticeAdapter extends RecyclerView.Adapter<ViewHolderNotice> implements AdapterView.OnItemSelectedListener {

    FindNotice findNotice;
    ArrayList<Notice> noticeArrayList;

    public NoticeAdapter(FindNotice findNotice,ArrayList<Notice> noticeArrayList) {
        this.findNotice = findNotice;
        this.noticeArrayList = noticeArrayList;
    }

    @NonNull
    @Override
    public ViewHolderNotice onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(findNotice.getBaseContext());
        View view = layoutInflater.inflate(R.layout.notice_list, parent, false);
        return new ViewHolderNotice(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderNotice holder, final int position) {
        holder.intitution.setText(noticeArrayList.get(position).getInstitution());
        holder.details.setText(noticeArrayList.get(position).getDetails());
    }

    @Override
    public int getItemCount() {
        return noticeArrayList.size();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

}