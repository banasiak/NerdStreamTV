package com.banasiak.android.nerdstream;

import com.banasiak.android.nerdstream.presentation.base.BaseFragment;
import com.banasiak.android.nerdstream.presentation.main.MainFragment;
import com.banasiak.android.nerdstream.presentation.base.BaseActivity;
import com.banasiak.android.nerdstream.presentation.main.MainActivity;

/**
 * Main Dagger application component for defining which modules are injected into which
 * activities/fragments. If you have access to an object's constructor, you should annotate that
 * with <code>@Inject</code> instead of using this interface.
 *
 * These should be ordered alphabetically and grouped by commonality. DO NOT auto format. (In
 * Android Studio, go to Preferences->Code Style->General->Formatter Control and check "Enable
 * formatter markers in comments" to disable auto-formatting.)
 */
// @formatter:off
public interface ApplicationGraph {

    void inject(NerdStreamApp application);
    void inject(AppInitializer initializer);

    void inject(BaseActivity activity);
    void inject(BaseFragment fragment);

    void inject(MainActivity activity);
    void inject(MainFragment fragment);

}
// @formatter:on