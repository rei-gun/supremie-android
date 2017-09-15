package com.cashlez.android.garuda.library.cashlezlib;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.hannesdorfmann.mosby3.mvp.MvpView;
import com.hannesdorfmann.mosby3.mvp.viewstate.RestorableViewState;

/**
 * Created by Taslim_Hartmann on 5/14/2017.
 */

public abstract class BaseViewState<V extends MvpView> implements RestorableViewState<V> {
    protected final String CURRENT_STATE = "CURRENT_STATE";
    protected int currentState = 0;
    protected final int STATE_SHOWING_LOADING = 1;
    protected final int STATE_HIDE_LOADING = 2;

    public void setStateShowLoading() {
        currentState = STATE_SHOWING_LOADING;
    }

    public void setStateHideLoading() {
        currentState = STATE_HIDE_LOADING;
    }

    @Override
    public void saveInstanceState(@NonNull Bundle out) {
        out.putInt(CURRENT_STATE, currentState);
    }

    @Override
    public RestorableViewState<V> restoreInstanceState(Bundle in) {
        if (in == null) {
            return null;
        }
        currentState = in.getInt(CURRENT_STATE);
        return this;
    }

    @Override
    public void apply(V view, boolean retained) {
        if (currentState == STATE_SHOWING_LOADING) {
            onShowLoading(view);
        } else if (currentState == STATE_HIDE_LOADING) {
            onHideLoading(view);
        }
    }

    protected abstract void onShowLoading(V view);

    protected abstract void onHideLoading(V view);
}
