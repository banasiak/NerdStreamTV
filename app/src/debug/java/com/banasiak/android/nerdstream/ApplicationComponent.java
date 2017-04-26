package com.banasiak.android.nerdstream;

import com.banasiak.android.nerdstream.data.api.ApiModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        ApiModule.class,
        DebugApplicationModule.class
})
public interface ApplicationComponent extends ApplicationGraph {

    void inject(DebugAppInitializer initializer);

    final class Initializer {
        private Initializer() {
            // block instantiation
        }

        public static ApplicationGraph init(NerdStreamApp app) {
            return DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(app))
                    .build();
        }
    }
}