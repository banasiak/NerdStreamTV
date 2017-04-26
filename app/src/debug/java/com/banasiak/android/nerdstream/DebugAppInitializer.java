package com.banasiak.android.nerdstream;

import android.app.Application;
import android.os.StrictMode;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.timber.StethoTree;
import com.squareup.leakcanary.LeakCanary;

import timber.log.Timber;

/**
 * ApplicationInitializer that does anything specific to release builds.
 */
public class DebugAppInitializer extends AppInitializer {

    @Override
    void variantSpecificInitialization(Application application) {
        LeakCanary.install(application);
        Timber.plant(new Timber.DebugTree());
        Timber.plant(new StethoTree());
        Stetho.initializeWithDefaults(application);

        // log StrictMode policy violations and kill the app for debug variant
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .penaltyDeath()
                .build());
    }

}
