package com.banasiak.android.nerdstream.data.persistence;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

import timber.log.Timber;

/**
 * SharedPreferences wrapper to simplify writing and reading local persistence data.
 */
@Singleton
public class PersistenceManager {

    private static final String PREFIX = PersistenceManager.class.getSimpleName();


    private final SharedPreferences sharedPrefs;

    @Inject
    PersistenceManager(SharedPreferences sharedPrefs) {
        this.sharedPrefs = sharedPrefs;
    }

    @SuppressLint("ApplySharedPref")
    public void clearAllPersistedData() {
        Timber.w("clearAllData() called");
        sharedPrefs.edit().clear().commit();
    }

}
