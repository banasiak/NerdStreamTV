package com.banasiak.android.nerdstream.presentation.base;

import android.support.v4.app.Fragment;

import com.banasiak.android.nerdstream.ApplicationGraph;
import com.banasiak.android.nerdstream.NerdStreamApp;

import butterknife.Unbinder;

/**
 * Extension of Fragment that will handle managing common fragment-related tasks.
 */
public abstract class BaseFragment extends Fragment {

    private Unbinder unbinder = Unbinder.EMPTY;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * Unbind Butterknife from views when out-of-scope to avoid memory leaks.
     *
     * @param unbinder Butterknife unbinder
     */
    protected void setUnbinder(Unbinder unbinder) {
        this.unbinder = unbinder;
    }

    protected BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

    /**
     * Get the Main Application Application Graph for dependency injection.
     */
    protected ApplicationGraph getApplicationGraph() {
        return NerdStreamApp.getApplicationGraph();
    }

}
