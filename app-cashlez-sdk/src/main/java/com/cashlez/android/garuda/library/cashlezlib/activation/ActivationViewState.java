package com.cashlez.android.garuda.library.cashlezlib.activation;

import android.os.Bundle;

import com.cashlez.android.garuda.library.cashlezlib.BaseViewState;
import com.hannesdorfmann.mosby3.mvp.viewstate.RestorableViewState;

/**
 * Created by Taslim_Hartmann on 5/16/2017.
 */

class ActivationViewState extends BaseViewState<ActivationView> implements RestorableViewState<ActivationView> {

    private final int STATE_SHOWING_ACTIVATION_FAILED = 3;
    private String failedMessage;

    @Override
    public void saveInstanceState(Bundle out) {
        super.saveInstanceState(out);
    }

    @Override
    public RestorableViewState<ActivationView> restoreInstanceState(Bundle in) {
        super.restoreInstanceState(in);
        return this;
    }

    @Override
    public void apply(ActivationView view, boolean retained) {
        super.apply(view, retained);
        if (currentState == STATE_SHOWING_ACTIVATION_FAILED) {
            view.onActivationError(failedMessage);
        }
    }

    @Override
    protected void onShowLoading(ActivationView view) {
        view.onShowLoading();
    }

    @Override
    protected void onHideLoading(ActivationView view) {
        view.onHideLoading();
    }

    public void setStateActivationFailed(String failedMessage) {
        this.failedMessage = failedMessage;
        currentState = STATE_SHOWING_ACTIVATION_FAILED;
    }
}
