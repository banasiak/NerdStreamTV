package com.banasiak.android.nerdstream.data.api;


import com.banasiak.android.nerdstream.data.model.Channel;

import java.util.List;

import retrofit2.adapter.rxjava.Result;
import retrofit2.http.GET;
import rx.Observable;

/**
 * This interface defines all endpoints for interfacing with the backend REST API.
 */
public interface NerdStreamApi {

    @GET("lineup.json")
    Observable<Result<List<Channel>>> getChannels();

}
