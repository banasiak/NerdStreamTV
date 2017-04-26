package com.banasiak.android.nerdstream;

import android.app.Application;
import android.content.Context;

import com.jakewharton.threetenabp.AndroidThreeTen;

/**
 * Class to handle the initialization of the application with build specific requirements.
 */
public abstract class AppInitializer {
    void init(Application application) {
        // any common initialization tasks should go here, like initializing JodaTime
        initializeAndroidThreeTen(application.getApplicationContext());
        variantSpecificInitialization(application);
    }

    private void initializeAndroidThreeTen(Context context) {
        AndroidThreeTen.init(context);
    }

    abstract void variantSpecificInitialization(Application application);
}
