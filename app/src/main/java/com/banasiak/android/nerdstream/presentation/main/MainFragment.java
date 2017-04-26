package com.banasiak.android.nerdstream.presentation.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.banasiak.android.nerdstream.R;
import com.banasiak.android.nerdstream.data.model.Channel;
import com.banasiak.android.nerdstream.presentation.base.BackPressedListener;
import com.banasiak.android.nerdstream.presentation.base.BaseFragment;
import com.banasiak.android.nerdstream.presentation.main.channels.ChannelAdapter;
import com.banasiak.android.nerdstream.presentation.main.channels.ChannelClickListener;
import com.squareup.moshi.Moshi;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import hugo.weaving.DebugLog;
import rx.functions.Action0;


public class MainFragment extends BaseFragment implements MainView, BackPressedListener {

    @Inject MainPresenter presenter;
    @Inject ChannelAdapter adapter;
    @Inject Moshi moshi;

    @BindView(R.id.main_channel_swipe_container) SwipeRefreshLayout channelSwipeRefreshLayout;
    @BindView(R.id.main_channel_recycler) RecyclerView channelRecycler;
    @BindView(R.id.main_channel_recycler_empty) View emptyChannelView;

    private View view;

    @DebugLog
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getApplicationGraph().inject(this);
        setHasOptionsMenu(true);
    }

    @DebugLog
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_main, container, false);
        this.view = fragmentView;
        setUnbinder(ButterKnife.bind(this, fragmentView));
        initializeChannelList();
        return fragmentView;
    }

    @DebugLog
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }

    @DebugLog
    @Override
    public void onResume() {
        super.onResume();
        this.presenter.attachView(this);
        presenter.retrieveChannelList();
    }

    @Override
    public boolean onBackPressed() {
        return true;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_fragment_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_main_clear_cache_and_restart:
                presenter.clearCacheAndRestart();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void updateChannelAdapter(List<Channel> channels) {
        adapter.setChannels(channels);
        if(channels.isEmpty()) {
            channelRecycler.setVisibility(View.GONE);
            emptyChannelView.setVisibility(View.VISIBLE);
        } else {
            channelRecycler.setVisibility(View.VISIBLE);
            emptyChannelView.setVisibility(View.GONE);
        }
    }

    @Override
    public void setAdapterCallbacks(ChannelClickListener clickListener) {
        adapter.setChannelClickListener(clickListener);
    }

    @Override
    public void showApiErrorMessage(Action0 retryAction) {
        String errorMessage = getString(R.string.main_api_retrieve_channels_error);
        String actionMessage = getString(R.string.main_api_retrieve_channels_error_retry);
        getBaseActivity().showSnackbar(view, errorMessage, actionMessage, v -> retryAction.call(), true);
    }

    @Override
    public void showRefreshIcon(boolean show) {
        channelSwipeRefreshLayout.setRefreshing(show);
    }



    private void initializeChannelList() {
        channelSwipeRefreshLayout.setOnRefreshListener(() -> presenter.retrieveChannelList());

        LinearLayoutManager layoutManager = new LinearLayoutManager(channelRecycler.getContext(), LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration decoration = new DividerItemDecoration(channelRecycler.getContext(), layoutManager.getOrientation());
        channelRecycler.setLayoutManager(layoutManager);
        channelRecycler.addItemDecoration(decoration);
        channelRecycler.setItemAnimator(new DefaultItemAnimator());
        channelRecycler.setAdapter(adapter);
    }

}
