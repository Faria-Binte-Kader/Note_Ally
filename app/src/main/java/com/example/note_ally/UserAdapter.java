package com.example.note_ally;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<ViewHolderUser> {

    private Chat chat;
    ArrayList<User> userArrayList;
    private String imageURL;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    String fUser;

    public UserAdapter(Chat chat, ArrayList<User>userArrayList){
        this.chat=chat;
        this.userArrayList=userArrayList;
    }

    @NonNull
    @Override
    public ViewHolderUser onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(chat.getBaseContext());
        View view = layoutInflater.inflate(R.layout.user_list,parent,false);
        return new ViewHolderUser(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderUser holder, int position) {
        if(holder.username.getText()!=null){
            holder.username.setText(userArrayList.get(position).getName());
            holder.profile_image.setImageResource(R.mipmap.ic_launcher_round);
        }

    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }
}
