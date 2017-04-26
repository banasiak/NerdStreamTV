package com.banasiak.android.nerdstream;

import android.app.Application;

/**
 * ApplicationInitializer that does anything specific to release builds.
 */
public class ReleaseAppInitializer extends AppInitializer {

    @Override
    void variantSpecificInitialization(Application application) {
        // no release specific initialization right now
    }

}
