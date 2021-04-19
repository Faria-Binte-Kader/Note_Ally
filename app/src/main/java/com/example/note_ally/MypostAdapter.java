package com.example.note_ally;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MypostAdapter extends RecyclerView.Adapter<ViewHolderMyPost> {

    Mypost mypost;
    ArrayList<Feedpost> mypostArrayList;


    public MypostAdapter(Mypost mypost, ArrayList<Feedpost> mypostArrayList) {
        this.mypost= mypost;
        this.mypostArrayList = mypostArrayList;
    }

    @NonNull
    @Override
    public ViewHolderMyPost onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater= LayoutInflater.from(mypost.getBaseContext());
        View view= layoutInflater.inflate(R.layout.mypostlist,parent,false);
        return new ViewHolderMyPost(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolderMyPost holder, int position) {
        holder.postdetails.setText(mypostArrayList.get(position).getdetails());
        holder.username.setText(mypostArrayList.get(position).getUsername());
        holder.likeno.setText(mypostArrayList.get(position).getLike());
        holder.dislikeno.setText(mypostArrayList.get(position).getDisike());

        //final String id= allpostArrayList.get(position).getPostID();


        /*holder.like.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FirebaseAuth fAuthpost;
                final FirebaseFirestore fStorepost;
                String userIDpost;

                fAuthpost = FirebaseAuth.getInstance();
                fStorepost = FirebaseFirestore.getInstance();
                userIDpost = fAuthpost.getCurrentUser().getUid();
                DocumentReference documentReference = fStorepost.collection("Doctor").document(userIDpost);
                documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(value!=null)
                        {String namepost=value.getString("Name");
                            fStorepost.collection("Questionanswer").document(id)
                                    .update("Doctor",namepost)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d("TAG","Success");
                                        }
                                    });

                        }}
                });
                Editable an=holder.answer.getText();

                fStorepost.collection("Questionanswer").document(id)
                        .update("Answer",an.toString())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("TAG","Success");
                            }
                        });

            }
        });*/

    }


    @Override
    public int getItemCount() {
        return mypostArrayList.size();
    }
}
