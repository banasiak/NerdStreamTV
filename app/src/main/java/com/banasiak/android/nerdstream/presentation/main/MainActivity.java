package com.banasiak.android.nerdstream.presentation.main;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.banasiak.android.nerdstream.presentation.base.BaseActivity;

import com.banasiak.android.nerdstream.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnLongClick;
import hugo.weaving.DebugLog;

public class MainActivity extends BaseActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;

    @DebugLog
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getApplicationGraph().inject(this);

        setSupportActionBar(toolbar);

        addFragment(R.id.fragment_container, new MainFragment(), false);
    }

    @OnLongClick(R.id.toolbar)
    boolean onToolbarLongClicked() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);

            String version = info.versionName;
            int code = info.versionCode;

            Toast.makeText(this, "version: " + version + " | build: " + code, Toast.LENGTH_LONG).show();

        } catch (PackageManager.NameNotFoundException e) {
            // ignore
        }

        return true;
    }

}
