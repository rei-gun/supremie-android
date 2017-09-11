package com.cashlez.android.garuda.library.cashlezlib.payment;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.hannesdorfmann.mosby3.mvp.viewstate.RestorableViewState;

/**
 * Created by Taslim on 12/05/2017.
 */

public class BasePaymentViewState implements RestorableViewState<BasePaymentView> {
    @Override
    public void saveInstanceState(@NonNull Bundle out) {

    }

    @Override
    public RestorableViewState<BasePaymentView> restoreInstanceState(Bundle in) {
        return null;
    }

    @Override
    public void apply(BasePaymentView view, boolean retained) {

    }
}
