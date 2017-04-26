package com.banasiak.android.nerdstream.presentation.main;

import com.banasiak.android.nerdstream.data.model.Channel;
import com.banasiak.android.nerdstream.presentation.base.mvp.BaseView;
import com.banasiak.android.nerdstream.presentation.main.channels.ChannelClickListener;

import java.util.List;

import rx.functions.Action0;

interface MainView extends BaseView {

    void updateChannelAdapter(List<Channel> channels);

    void setAdapterCallbacks(ChannelClickListener clickListener);

    void showApiErrorMessage(Action0 retryAction);

    void showRefreshIcon(boolean show);

}
