package com.banasiak.android.nerdstream.presentation.main.channels;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.banasiak.android.nerdstream.R;
import com.banasiak.android.nerdstream.data.model.Channel;

import butterknife.BindView;
import butterknife.ButterKnife;


class ChannelViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.channel_row_number) TextView channelNumber;
    @BindView(R.id.channel_row_name) TextView channelName;

    private final Context context;
    private Channel channel;
    private ChannelClickListener clickListener;


    ChannelViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        context = itemView.getContext().getApplicationContext();
    }

    void bind(Channel channel, ChannelClickListener clickListener) {
        this.channel = channel;
        this.clickListener = clickListener;

        channelNumber.setText(channel.guideNumber());
        channelName.setText(channel.guideName());

        tintChannelBackground();
    }

    private void tintChannelBackground() {
        if(channel.isHiDef() != null && channel.isHiDef()) {
            ColorStateList colorStateList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.colorAccent));
            channelNumber.setBackgroundTintList(colorStateList);
        } else {
            ColorStateList colorStateList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.colorPrimary));
            channelNumber.setBackgroundTintList(colorStateList);
        }
    }

}
