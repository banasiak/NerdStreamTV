package com.banasiak.android.nerdstream;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DebugApplicationModule {

    @Provides
    @Singleton
    AppInitializer provideAppInitializer() {
        return new DebugAppInitializer();
    }

}
