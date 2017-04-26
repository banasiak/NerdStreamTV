package com.banasiak.android.nerdstream.presentation.stream;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.banasiak.android.nerdstream.R;
import com.banasiak.android.nerdstream.presentation.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import hugo.weaving.DebugLog;

public class StreamActivity extends BaseActivity {

    public static final String EXTRA_STREAM_URL = "stream_url_extra";

    @BindView(R.id.stream_loading_indicator) ProgressBar loadingIndicator;
    @BindView(R.id.stream_video_view) VideoView videoView;

    private String streamUrl;

    @DebugLog
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stream);
        ButterKnife.bind(this);
        getApplicationGraph().inject(this);

        hideSystemUi();

        Intent intent = getIntent();
        streamUrl = intent.getStringExtra(EXTRA_STREAM_URL);

        if(streamUrl == null) {
            Toast.makeText(this, R.string.stream_no_url, Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        startVideoStream();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopVideoStream();
    }

    private void hideSystemUi() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE
        );
    }

    private void startVideoStream(){
        loadingIndicator.setVisibility(View.VISIBLE);

        Uri video = Uri.parse(streamUrl + "?transcode=mobile");
        videoView.setMediaController(null);
        videoView.setVideoURI(video);
        videoView.requestFocus();

        videoView.setOnPreparedListener(mp -> {
            loadingIndicator.setVisibility(View.GONE);
            videoView.start();
        });

        videoView.setOnErrorListener((mp, what, extra) -> true);
    }

    private void stopVideoStream() {
        videoView.stopPlayback();
    }

}
