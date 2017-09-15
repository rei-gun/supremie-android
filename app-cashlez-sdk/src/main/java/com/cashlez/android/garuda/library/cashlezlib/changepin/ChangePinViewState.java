package com.cashlez.android.garuda.library.cashlezlib.changepin;

import android.os.Bundle;

import com.cashlez.android.garuda.library.cashlezlib.BaseViewState;
import com.hannesdorfmann.mosby3.mvp.viewstate.RestorableViewState;

/**
 * Created by Taslim_Hartmann on 5/16/2017.
 */

class ChangePinViewState extends BaseViewState<ChangePinView> implements RestorableViewState<ChangePinView>{
    private final int STATE_SHOWING_CHANGE_PIN_FAILED = 3;
    private String failedMessage;

    @Override
    public void saveInstanceState(Bundle out) {
        super.saveInstanceState(out);
    }

    @Override
    public RestorableViewState<ChangePinView> restoreInstanceState(Bundle in) {
        super.restoreInstanceState(in);
        return this;
    }

    @Override
    public void apply(ChangePinView view, boolean retained) {
        super.apply(view, retained);
        if (currentState == STATE_SHOWING_CHANGE_PIN_FAILED) {
            view.onChangePinError(failedMessage);
        }
    }

    @Override
    protected void onShowLoading(ChangePinView view) {
        view.onShowLoading();
    }

    @Override
    protected void onHideLoading(ChangePinView view) {
        view.onHideLoading();
    }

    public void setStateChangePinFailed(String failedMessage) {
        this.failedMessage = failedMessage;
        currentState = STATE_SHOWING_CHANGE_PIN_FAILED;
    }
}
