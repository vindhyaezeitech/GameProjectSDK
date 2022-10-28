package com.today.gamesdk.shabdamsdk.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.today.gamesdk.shabdamsdk.R;
import com.today.gamesdk.shabdamsdk.model.leaderboard.LeaderboardListModel;

import java.util.List;


public class GetLeaderboardListAdapter extends RecyclerView.Adapter<GetLeaderboardListAdapter.ViewHolder> {
    private Context context;
    private List<LeaderboardListModel> list;
    private long minutes, seconds;
    private String minute, second;


    public GetLeaderboardListAdapter(Context context, List<LeaderboardListModel> listData) {
        this.context = context;
        this.list=listData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.getleaderboard_item_list_layout, parent, false);
        return new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_rank.setText(list.get(position).getRank());
        holder.tv_name.setText(list.get(position).getName());
        minutes = Long.parseLong(list.get(position).getTime())/60;
        seconds = Integer.parseInt(list.get(position).getTime())% 60;
        minute = String.format("%02d", minutes);
        second = String.format("%02d", seconds);
        holder.tv_time.setText(minute + " " + ":" + " " + second);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv_name, tv_rank, tv_time;

        private ViewHolder(View itemView) {
            super(itemView);

            tv_name=itemView.findViewById(R.id.tv_name);
            tv_rank=itemView.findViewById(R.id.tv_rank);
            tv_time=itemView.findViewById(R.id.tv_time);

        }
    }

}