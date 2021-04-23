package com.example.note_ally;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<ViewHolderMessage> {

    MessageActivity messageActivity;
    ArrayList<Message> messageArrayList;
    private String imageURL;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    String fUser;

    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;

    public MessageAdapter(MessageActivity messageActivity, ArrayList<Message> messageArrayList) {
        this.messageActivity = messageActivity;
        this.messageArrayList = messageArrayList;
    }

    /*
    public MessageAdapter(Context context, ArrayList<Message> messageArrayList, String imageURL) {
        this.context = context;
        this.messageArrayList = messageArrayList;
        this.imageURL = imageURL;
    }
     */

    @NonNull
    @Override
    public ViewHolderMessage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MSG_TYPE_RIGHT) {
            LayoutInflater layoutInflater = LayoutInflater.from(messageActivity.getBaseContext());
            View view = layoutInflater.inflate(R.layout.chat_item_right, parent, false);
            Log.v("TAG ---I---onComplete", String.valueOf(MSG_TYPE_RIGHT));
            return new ViewHolderMessage(view);
        } else {
            LayoutInflater layoutInflater = LayoutInflater.from(messageActivity.getBaseContext());
            //View view = layoutInflater.inflate(R.layout.chat_item_left, parent, false);
            View view = layoutInflater.inflate(R.layout.chat_item_left, parent, false);
            Log.v("TAG ---I---onComplete", String.valueOf(MSG_TYPE_LEFT));
            return new ViewHolderMessage(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderMessage holder, final int position) {
        if(holder.show_msg.getText()!=null)
        {holder.show_msg.setText(messageArrayList.get(position).getMsg());}
    }

    /*
    public void onBindViewHolder(@NonNull final ViewHolderMessage holder, final int position) {
        Message message = messageArrayList.get(position);
        holder.show_msg.setText(message.getMsg());

        if (imageURL.equals("default")) {
            holder.profile_image.setImageResource(R.mipmap.ic_launcher_round);
        } else {
            Glide.with(context).load(imageURL).into(holder.profile_image);
        }
    }
     */

    @Override
    public int getItemCount() {
        return messageArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        fUser = fAuth.getCurrentUser().getUid();
        if (messageArrayList.get(position).getSender().equals(fUser)) {
            return MSG_TYPE_RIGHT;
        } else {
            return MSG_TYPE_LEFT;
        }
    }
}