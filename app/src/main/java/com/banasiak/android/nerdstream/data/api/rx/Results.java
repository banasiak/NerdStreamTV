package com.banasiak.android.nerdstream.data.api.rx;

import retrofit2.adapter.rxjava.Result;
import rx.functions.Func1;

public final class Results {
    private static final Func1<Result<?>, Boolean> SUCCESSFUL =
            result -> !result.isError() && result.response().isSuccessful();

    public static Func1<Result<?>, Boolean> isSuccessful() {
        return SUCCESSFUL;
    }

    public static int getResponseCode(Result result) {
        return result.response().code();
    }
}
