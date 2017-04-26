package com.banasiak.android.nerdstream;

import com.banasiak.android.nerdstream.data.api.ApiModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        ApiModule.class,
        ReleaseApplicationModule.class
})
public interface ApplicationComponent extends ApplicationGraph {

    void inject(ReleaseAppInitializer initializer);

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