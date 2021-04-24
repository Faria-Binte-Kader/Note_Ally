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

public class ChatUserAdapter extends RecyclerView.Adapter<ViewHolderUser> {

    private Chat chat;
    ArrayList<MessageList> userArrayList;
    private String imageURL;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    String fUser;

    public ChatUserAdapter(Chat chat, ArrayList<MessageList>userArrayList){
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
            holder.username.setText(userArrayList.get(position).getName());
            holder.profile_image.setImageResource(R.drawable.ic_profilechat);
            holder.username.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String userid = userArrayList.get(position).getId();
                    chat.goToChat(userid);
                }
            });
    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }
}
