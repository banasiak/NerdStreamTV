package com.banasiak.android.nerdstream.data.model;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.Json;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.Serializable;

@AutoValue
public abstract class Channel implements Serializable {

    @Json(name = "GuideNumber")
    public abstract String guideNumber();

    @Json(name = "GuideName")
    public abstract String guideName();

    @Json(name = "VideoCodec")
    public abstract String videoCodec();

    @Json(name = "AudioCodec")
    public abstract String audioCodec();

    @Json(name = "HD")
    @Nullable
    public abstract Boolean isHiDef();

    @Json(name = "URL")
    public abstract String url();

    public static Channel create(String guideNumber, String guideName, String videoCodec, String audioCodec, Boolean isHiDef, String url) {
        return builder()
                .guideNumber(guideNumber)
                .guideName(guideName)
                .videoCodec(videoCodec)
                .audioCodec(audioCodec)
                .isHiDef(isHiDef)
                .url(url)
                .build();
    }

    public static Builder builder() {
        return new AutoValue_Channel.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder guideNumber(String guideNumber);

        public abstract Builder guideName(String guideName);

        public abstract Builder videoCodec(String videoCodec);

        public abstract Builder audioCodec(String audioCodec);

        public abstract Builder isHiDef(Boolean isHiDef);

        public abstract Builder url(String url);

        public abstract Channel build();
    }

    public static JsonAdapter<Channel> jsonAdapter(Moshi moshi) {
        return new AutoValue_Channel.MoshiJsonAdapter(moshi);
    }
}
