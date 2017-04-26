package com.banasiak.android.nerdstream;

import android.Manifest;
import android.app.Application;

import com.jakewharton.processphoenix.ProcessPhoenix;
import com.squareup.leakcanary.LeakCanary;

import javax.inject.Inject;

/**
 * Extension of application to allow for configuration of debugging libraries like Timber
 * and Stetho. Also provides a place for other global idealizations like an Application
 * level Dagger components.
 */
public class NerdStreamApp extends Application {

    public static final String[] runtimePermissions = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    private static ApplicationGraph componentGraph;
    private static NerdStreamApp instance;

    @Inject AppInitializer appInitializer;

    @Override
    public void onCreate() {
        super.onCreate();

        if (LeakCanary.isInAnalyzerProcess(this) || ProcessPhoenix.isPhoenixProcess(this)) {
            // This process is dedicated to LeakCanary or ProcessPhoenix.
            // You should not init your app in this process.
            return;
        }

        instance = this;
        buildComponentGraph();
        componentGraph.inject(this);

        appInitializer.init(instance);
    }

    public static ApplicationGraph getApplicationGraph() {
        return componentGraph;
    }

    private static void buildComponentGraph() {
        componentGraph = ApplicationComponent.Initializer.init(instance);
    }

}
