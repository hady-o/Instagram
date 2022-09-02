package com.example.instapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.instapp.R;
import com.example.instapp.classes.Post;
import com.example.instapp.classes.User;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {

    //for captions and image resources
    List<User> users=new ArrayList<>();

    public SearchAdapter(List<User> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_card, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            User user =users.get(position);
            holder.userName.setText(user.getName());
            Glide.with(holder.itemView.getContext())
                .load(user.getPhoto())
                .into(holder.userImage);


            holder.email.setText(user.getEmail());


    }


    @Override
    public int getItemCount() {
        return users.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView userName, email;
        ImageView userImage;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            email = itemView.findViewById(R.id.searchEmailId);
            userName = itemView.findViewById(R.id.searchNameId);
            userImage = itemView.findViewById(R.id.searchImageId);

        }
    }
}
