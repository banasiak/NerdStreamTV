package com.banasiak.android.nerdstream.presentation.base.mvp;

/**
 * @author Aaron Weaver
 * @since 11/3/16
 */
public class BasePresenter<V extends BaseView> implements Presenter<V> {

    private V view;

    @Override
    public void attachView(V view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    public boolean isViewAttached() {
        return this.view != null;
    }

    public V getView() {
        if (isViewAttached()) {
            return this.view;
        }

        throw new ViewNotAttachedException();
    }
}
