package com.banasiak.android.nerdstream.data.api.rx;

import retrofit2.Response;
import retrofit2.adapter.rxjava.Result;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Common Rx {@link Transformers} functions.
 */
public final class Transformers {

    /**
     * Transformer for mapping Rx Result object to the Retrofit response.
     *
     * @param <T> The expected response object from the API.
     * @return Observable that will return the expected data object to onNext when called.
     */
    public static <T> Observable.Transformer<Result<T>, T> resultToResponseData() {
        return observable -> observable.map(Result::response).map(Response::body);
    }

    /**
     * Transformer for applying the common Android scheduler pattern to Observables. That
     * is io as the thread doing work and the thread using the results is the main thread.
     * This is common for Retrofit and many other long running tasks.
     *
     * @param <T> The type of the Observable
     * @return An observable that has the schedulers are applied.
     */
    public static <T> Observable.Transformer<T, T> applyIoToMainSchedulers() {
        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Transformers() {
        throw new AssertionError("No Instances.");
    }
}
