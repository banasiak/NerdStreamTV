package com.banasiak.android.nerdstream.presentation.base.mvp;

/**
 * @author Aaron Weaver
 * @since 11/3/16
 */

public interface Presenter<V extends BaseView> {

    void attachView(V view);

    void detachView();
}
