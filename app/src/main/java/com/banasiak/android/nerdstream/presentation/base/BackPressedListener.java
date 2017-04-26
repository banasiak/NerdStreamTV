package com.banasiak.android.nerdstream.presentation.base;

public interface BackPressedListener {
    /**
     * @return true to override activity's onBackPressed, false otherwise
     */
    boolean onBackPressed();
}
