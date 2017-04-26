package com.banasiak.android.nerdstream.presentation.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.banasiak.android.nerdstream.ApplicationGraph;
import com.banasiak.android.nerdstream.NerdStreamApp;
import com.banasiak.android.nerdstream.R;

import rx.subscriptions.CompositeSubscription;

/**
 * Extension of AppCompatActivity to handle things like Fragment management and common navigation.
 */
public abstract class BaseActivity extends AppCompatActivity {

    private final CompositeSubscription compositeSubscription = new CompositeSubscription();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getApplicationGraph().inject(this);
        setStatusBarColor();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeSubscription.clear();
    }

    /**
     * Adds a fragment to the specified view id
     */
    public void addFragment(int containerViewId, Fragment fragment) {
        addFragment(containerViewId, fragment, false);
    }

    public void addFragment(int containerViewId, Fragment fragment, boolean addToBackstack) {
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        fragmentTransaction.replace(containerViewId, fragment, fragment.getClass().getSimpleName());

        if (addToBackstack) {
            fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
        }

        fragmentTransaction.commit();
    }

    /**
     * Convenience method to clear the back stack if anything is present
     */
    public void clearBackStack() {
        FragmentManager manager = getSupportFragmentManager();
        if (manager.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry first = manager.getBackStackEntryAt(0);
            manager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    /**
     * Override the back button to let the current fragment handle the event,
     * or pop things off the back stack if there are fragments on top of each other,
     * if not let the system handle it
     */
    @Override
    public void onBackPressed() {
        Fragment fragment = getCurrentFragment();
        if (fragment != null && fragment instanceof BackPressedListener) {
            // if onBackPressed is true then the fragment overrides the Activity's onBackPressed
            if (((BackPressedListener) fragment).onBackPressed()) {
                return;
            }
        }

        if (getFragmentManager().getBackStackEntryCount() == 1) {
            getFragmentManager().popBackStack();
            super.onBackPressed();
        } else if (getFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
        } else {
            getFragmentManager().popBackStack();
        }
    }

    private void setStatusBarColor() {
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
    }

    /**
     * Returns the current fragment from the list of fragments contained in {@link android.support.v4.app.FragmentManager}.
     *
     * @return {@link android.support.v4.app.Fragment}
     */
    @Nullable
    private Fragment getCurrentFragment() {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() != 0) {
            return fragmentManager.findFragmentByTag(fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1).getName());
        } else {
            return null;
        }
    }

    protected void showSnackbar(String message, boolean isError) {
        View view = findViewById(android.R.id.content);
        showSnackbar(view, message, isError);
    }

    protected void showSnackbar(String message, String actionMessage, @Nullable View.OnClickListener onClickListener, boolean isError) {
        View view = findViewById(android.R.id.content);
        showSnackbar(view, message, actionMessage, onClickListener, isError);
    }

    public void showSnackbar(View view, String message, boolean isError) {
        showSnackbar(view, message, null, null, isError);
    }

    public void showSnackbar(@NonNull View view, String message, String actionMessage,
                             @Nullable View.OnClickListener onClickListener, boolean isError) {

        Snackbar snackbar;
        if (isError && onClickListener != null) {
            snackbar = Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE);
        } else {
            snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        }
        View sbView = snackbar.getView();

        TextView text = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        TextViewCompat.setTextAppearance(text, R.style.AppTheme_Text_Snackbar);

        TextView action = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_action);
        TextViewCompat.setTextAppearance(action, R.style.AppTheme_Text_Snackbar);

        sbView.setBackgroundColor(ContextCompat.getColor(this, isError ? R.color.red : R.color.colorPrimary));

        if (onClickListener != null) {
            snackbar.setAction(actionMessage, onClickListener);
        }
        snackbar.show();
    }

    /**
     * Get the Main Application getApplicationGraph for dependency injection.
     */
    protected ApplicationGraph getApplicationGraph() {
        return NerdStreamApp.getApplicationGraph();
    }

}
