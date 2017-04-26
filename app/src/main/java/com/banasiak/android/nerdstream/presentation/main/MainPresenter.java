package com.banasiak.android.nerdstream.presentation.main;

import android.app.Application;

import com.banasiak.android.nerdstream.data.api.NerdStreamApi;
import com.banasiak.android.nerdstream.data.api.rx.Funcs;
import com.banasiak.android.nerdstream.data.api.rx.Results;
import com.banasiak.android.nerdstream.data.api.rx.Transformers;
import com.banasiak.android.nerdstream.data.model.Channel;
import com.banasiak.android.nerdstream.data.persistence.PersistenceManager;
import com.banasiak.android.nerdstream.presentation.base.mvp.BasePresenter;
import com.banasiak.android.nerdstream.presentation.main.channels.ChannelClickListener;
import com.jakewharton.processphoenix.ProcessPhoenix;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import javax.inject.Inject;

import okhttp3.OkHttpClient;
import retrofit2.adapter.rxjava.Result;
import rx.Observable;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

public class MainPresenter extends BasePresenter<MainView> {

    private final Application app;
    private final NerdStreamApi api;
    private final OkHttpClient httpClient;
    private final PersistenceManager persistenceManager;

    private final CompositeSubscription subscriptions = new CompositeSubscription();
    private final ChannelClickListener channelClickListener;


    @Inject
    MainPresenter(Application app, NerdStreamApi api, OkHttpClient httpClient, PersistenceManager persistenceManager) {
        this.app = app;
        this.api = api;
        this.httpClient = httpClient;
        this.persistenceManager = persistenceManager;
        this.channelClickListener = createStreamClickListener();
    }

    @Override
    public void attachView(MainView view) {
        super.attachView(view);
        getView().setAdapterCallbacks(channelClickListener);
    }

    @Override
    public void detachView() {
        subscriptions.clear();
        super.detachView();
    }

    void retrieveChannelList() {
        getView().showRefreshIcon(true);

        Observable<Result<List<Channel>>> result = api
                .getChannels()
                .compose(Transformers.applyIoToMainSchedulers())
                .share();

        subscriptions.add(result
                .filter(Results.isSuccessful())
                .compose(Transformers.resultToResponseData())
                .subscribe(this::onApiSuccess));

        subscriptions.add(result
                .filter(Funcs.not(Results.isSuccessful()))
                .subscribe(this::onApiFailure));

    }

    private void onApiSuccess(List<Channel> response) {
        Timber.d("Channel list API request successful");
        getView().updateChannelAdapter(response);
        getView().showRefreshIcon(false);
    }

    private void onApiFailure(Result<?> result) {
        Timber.e(result.error(), "API error when retrieving channel list");
        getView().showApiErrorMessage(this::retrieveChannelList);
        getView().showRefreshIcon(false);
    }


    private ChannelClickListener createStreamClickListener() {
        return new ChannelClickListener() {

            @Override
            public void onStreamClick(Stream stream) {

            }
        };
    }

    void clearCacheAndRestart() {
        Timber.d("clearCacheAndRestart() called");

        Timber.i("Clearing all data from PersistenceManager");
        persistenceManager.clearAllPersistedData();

        Timber.i("Deleting HTTP cache");
        try {
            httpClient.cache().delete();
        } catch (IOException e) {
            Timber.e(e, "Unable to delete HTTP cache");
        }

        Timber.i("Restarting app. Goodbye.");
        ProcessPhoenix.triggerRebirth(app);
    }
}
