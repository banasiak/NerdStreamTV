package com.banasiak.android.nerdstream.data.api;

import android.app.Application;

import com.banasiak.android.nerdstream.R;
import com.banasiak.android.nerdstream.data.api.adapter.IntegerBooleanJsonAdapter;
import com.banasiak.android.nerdstream.data.api.adapter.MoshiAdapterFactory;
import com.banasiak.android.nerdstream.data.api.interceptor.ContentTypeInterceptor;
import com.squareup.moshi.Moshi;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

@Module
public class ApiModule {

    private static final int HTTP_DISK_CACHE_SIZE = 10 * 1024 * 1024; // 10 megabytes

    @Provides
    @Singleton
    Moshi provideMoshi() {
        return new Moshi.Builder()
                .add(MoshiAdapterFactory.create())
                .add(Boolean.class, new IntegerBooleanJsonAdapter())
                .build();
    }

    @Provides
    @Singleton
    MoshiConverterFactory provideMoshiConverterFactory(Moshi moshi) {
        return MoshiConverterFactory.create(moshi);
    }

    @Provides
    @Singleton
    OkHttpClient provideHttpClient(Application context,
                                   ContentTypeInterceptor contentTypeInterceptor) {

        File cacheDir = new File(context.getCacheDir(), "http");
        Cache cache = new Cache(cacheDir, HTTP_DISK_CACHE_SIZE);

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .addInterceptor(contentTypeInterceptor)
                .addInterceptor(loggingInterceptor)
                .cache(cache)
                .build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(Application context, OkHttpClient client, MoshiConverterFactory moshiConverterFactory) {
        String url = context.getString(R.string.api_path);
        return new Retrofit.Builder()
                .addConverterFactory(moshiConverterFactory)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .baseUrl(url)
                .build();
    }

    @Provides
    @Singleton
    NerdStreamApi provideApi(Retrofit retrofit) {
        return retrofit.create(NerdStreamApi.class);
    }

}
