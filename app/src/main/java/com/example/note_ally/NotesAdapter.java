package com.example.note_ally;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<ViewHolderNotes> implements AdapterView.OnItemSelectedListener {

    ViewNote viewNote;
    ArrayList<Notes> notesArrayList;

    private NotesAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(NotesAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    public NotesAdapter(ViewNote viewNote, ArrayList<Notes> notesArrayList) {
        this.viewNote = viewNote;
        this.notesArrayList = notesArrayList;
    }

    @NonNull
    @Override
    public ViewHolderNotes onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewNote.getBaseContext());
        View view = layoutInflater.inflate(R.layout.notes_list, parent, false);
        return new ViewHolderNotes(view,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderNotes holder, final int position) {
        holder.title.setText(notesArrayList.get(position).getTitle());
        holder.details.setText(notesArrayList.get(position).getDetails());
    }

    @Override
    public int getItemCount() {
        return notesArrayList.size();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}
