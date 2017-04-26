package com.banasiak.android.nerdstream.data.api.adapter;

import com.squareup.moshi.JsonAdapter;

/**
 * auto-value-moshi can create a single JsonAdapter.Factory so that you don't have to add each
 * generated JsonAdapter to your Moshi instance manually.
 * <p>
 * To generate a JsonAdapter.Factory for all of your auto-value-moshi classes, simply create an
 * abstract class that implements JsonAdapter.Factory and annotate it with MoshiAdapterFactory,
 * and auto-value-moshi will create an implementation for you. You simply need to provide a static
 * factory method, just like your AutoValue classes, and you can use the generated
 * JsonAdapter.Factory to help Moshi de/serialize your types.
 * <p>
 * Then you simply need to register the Factory with Moshi.
 * <code>Moshi moshi = new Moshi.Builder().add(MoshiAdapterFactory.create()).build();</code>
 */
@com.ryanharter.auto.value.moshi.MoshiAdapterFactory
public abstract class MoshiAdapterFactory implements JsonAdapter.Factory {

    // static factory method to access the package-private generated implementation
    public static JsonAdapter.Factory create() {
        return new AutoValueMoshi_MoshiAdapterFactory();
    }

}
