package com.banasiak.android.nerdstream.presentation.main.channels;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.banasiak.android.nerdstream.R;
import com.banasiak.android.nerdstream.data.model.Channel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ChannelAdapter extends RecyclerView.Adapter<ChannelViewHolder> {

    private List<Channel> channels;
    private ChannelClickListener clickListener;

    @Inject
    ChannelAdapter() {
        this.channels = new ArrayList<>();
    }

    @Override
    public ChannelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_channel, parent, false);
        return new ChannelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChannelViewHolder holder, int position) {
        holder.bind(channels.get(position), clickListener);
    }

    @Override
    public int getItemCount() {
        return channels.size();
    }

    public void setChannels(List<Channel> channels) {
        this.channels = channels;
        notifyDataSetChanged();
    }

    public void setChannelClickListener(ChannelClickListener clickListener) {
        this.clickListener = clickListener;
    }

}
