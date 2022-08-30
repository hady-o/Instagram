package com.example.instapp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

class CaptionedImagesAdapter extends RecyclerView.Adapter<CaptionedImagesAdapter.ViewHolder> {

    //for captions and image resources
    private String[] captions;
    private int[] imageIDs;


    public static class ViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        //Each view holder will display a card view
           public ViewHolder(CardView v){
               super(v);
               cardView=v;
           }
    }

    //Pass data to the adapter in its constructor
    public CaptionedImagesAdapter(String[] captions,int[] imageIDs){
        this.captions=captions;
        this.imageIDs=imageIDs;
    }

    @Override
    public int getItemCount() {
        return captions.length;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cv=(CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_captioned_image,parent,false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }
}
