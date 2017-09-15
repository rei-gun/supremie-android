package com.cashlez.android.garuda.library.cashlezlib.payment.landing;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.cashlez.android.garuda.library.cashlezlib.BaseViewState;
import com.cashlez.android.sdk.payment.CLPaymentResponse;
import com.hannesdorfmann.mosby3.mvp.viewstate.RestorableViewState;

/**
 * Created by Taslim on 12/05/2017.
 */

class LandingViewState extends BaseViewState<LandingView> implements RestorableViewState<LandingView> {


    private final String KEY_DATA = "LandingViewState-data";
    private final int STATE_START_PAYMENT = 3;
    private final int STATE_READER_STATUS = 4;
    private final int STATE_PAYMENT_SUCCESS = 5;

    private Parcelable data;
    private boolean isConnected;

    @Override
    public void saveInstanceState(@NonNull Bundle out) {
        super.saveInstanceState(out);
        out.putParcelable(KEY_DATA, data);
    }

    @Override
    public RestorableViewState<LandingView> restoreInstanceState(Bundle in) {
        super.restoreInstanceState(in);
        if (in == null) {
            return null;
        }
        data = in.getParcelable(KEY_DATA);
        return this;
    }

    @Override
    public void apply(LandingView view, boolean retained) {
        super.apply(view, retained);
        if (currentState == STATE_START_PAYMENT) {
            view.onReaderStatus(isConnected);
        } else if (currentState == STATE_READER_STATUS) {
            view.onReaderStatus(isConnected);
        } else if (currentState == STATE_PAYMENT_SUCCESS) {
            view.onPaymentResult((CLPaymentResponse) data);
        }
    }

    public void setStatePaymentSuccess(Parcelable data) {
        this.data = data;
        currentState = STATE_PAYMENT_SUCCESS;
    }

    @Override
    protected void onShowLoading(LandingView view) {
        view.onShowLoading();
    }

    @Override
    protected void onHideLoading(LandingView view) {
        view.onHideLoading();
    }

    public void setStateStartPayment() {
        currentState = STATE_START_PAYMENT;
    }

    public void setStateReaderResponse(boolean isConnected) {
        this.isConnected = isConnected;
        currentState = STATE_READER_STATUS;
    }
}
