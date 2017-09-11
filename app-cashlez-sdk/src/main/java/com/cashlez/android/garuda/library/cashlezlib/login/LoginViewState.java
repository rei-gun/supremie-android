package com.cashlez.android.garuda.library.cashlezlib.login;

import android.os.Bundle;

import com.cashlez.android.garuda.library.cashlezlib.BaseViewState;
import com.hannesdorfmann.mosby3.mvp.viewstate.RestorableViewState;

/**
 * Created by Taslim_Hartmann on 5/14/2017.
 */

class LoginViewState extends BaseViewState<LoginView> implements RestorableViewState<LoginView> {

    private final int STATE_SHOWING_LOGIN_FAILED = 3;
    private String failedMessage;

    @Override
    public void saveInstanceState(Bundle out) {
        super.saveInstanceState(out);
    }

    @Override
    public RestorableViewState<LoginView> restoreInstanceState(Bundle in) {
        super.restoreInstanceState(in);
        return this;
    }

    @Override
    public void apply(LoginView view, boolean retained) {
        super.apply(view, retained);
        if (currentState == STATE_SHOWING_LOGIN_FAILED) {
            view.onLoginFailed(failedMessage);
        }
    }

    @Override
    protected void onShowLoading(LoginView view) {
        view.onShowLoading();
    }

    @Override
    protected void onHideLoading(LoginView view) {
        view.onHideLoading();
    }

    public void setStateLoginFailed(String failedMessage) {
        this.failedMessage = failedMessage;
        currentState = STATE_SHOWING_LOGIN_FAILED;
    }
}
