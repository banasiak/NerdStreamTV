package com.banasiak.android.nerdstream.data.api.interceptor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Response;

@Singleton
public class CacheControlInterceptor implements Interceptor {

    private static final int MAX_CACHE_AGE = 10;
    private static final TimeUnit MAX_CACHE_AGE_UNIT = TimeUnit.MINUTES;

    @Inject
    public CacheControlInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Response response = chain.proceed(chain.request());

        // re-write response header to force use of cache
        CacheControl cacheControl = new CacheControl.Builder()
                .maxAge(MAX_CACHE_AGE, MAX_CACHE_AGE_UNIT)
                .build();

        return response.newBuilder()
                .header("Cache-Control", cacheControl.toString())
                .build();

    }
}
