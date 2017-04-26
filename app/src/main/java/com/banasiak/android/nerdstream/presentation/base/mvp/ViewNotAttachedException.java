package com.banasiak.android.nerdstream.presentation.base.mvp;

/**
 * @author Aaron Weaver
 * @since 11/3/16
 */

public class ViewNotAttachedException extends RuntimeException {

    public ViewNotAttachedException() {
        super("Must attach view to presenter before attempting to perform view operations");
    }
}
