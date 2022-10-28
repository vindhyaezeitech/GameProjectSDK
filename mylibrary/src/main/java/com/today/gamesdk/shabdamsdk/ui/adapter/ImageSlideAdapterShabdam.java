package com.today.gamesdk.shabdamsdk.ui.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.today.gamesdk.shabdamsdk.R;

import java.util.List;

public class ImageSlideAdapterShabdam extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Integer> list;

    public ImageSlideAdapterShabdam(Context context, List<Integer> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.pager_item_layout, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ((ViewHolder)holder).bindData(list.get(position));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView slide_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            slide_image=itemView.findViewById(R.id.iv_skip_image);
        }

        void bindData(Integer image)
        {
            Glide.with(context).load(image).into(slide_image);
        }
    }
}