package com.example.instapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instapp.Post;
import com.example.instapp.R;

import java.util.ArrayList;
import java.util.List;

public class CaptionedImagesAdapter extends RecyclerView.Adapter<CaptionedImagesAdapter.MyViewHolder> {

    //for captions and image resources
    List<Post> posts=new ArrayList<>();

    public CaptionedImagesAdapter(List<Post> posts) {
        this.posts = posts;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_captioned_image, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            Post post =posts.get(position);
            holder.post.setText(post.getPost());
            holder.postImage.setImageResource(post.getPostImage());
            holder.userImage.setImageResource(post.getUserImage());
            holder.userName.setText(post.getUserName());
    }


    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView userName, post;
        ImageView userImage, postImage;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.tv_name);
            userImage = itemView.findViewById(R.id.circularImageView);
            post = itemView.findViewById(R.id.tv_status);
            postImage = itemView.findViewById(R.id.imgView_postPic);
        }
    }
}
