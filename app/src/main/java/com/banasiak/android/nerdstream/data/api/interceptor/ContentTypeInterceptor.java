package com.banasiak.android.nerdstream.data.api.interceptor;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * OkHttp Interceptor which will inject the JSON content type into all API calls.
 */
@Singleton
public final class ContentTypeInterceptor implements Interceptor {

    @Inject
    ContentTypeInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request().newBuilder()
                .addHeader("Content-Type", "application/json;charset=UTF-8")
                .build();
        return chain.proceed(request);
    }
}
