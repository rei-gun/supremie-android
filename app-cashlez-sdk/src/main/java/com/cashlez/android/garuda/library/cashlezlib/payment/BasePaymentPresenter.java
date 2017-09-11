package com.cashlez.android.garuda.library.cashlezlib.payment;

import android.os.Bundle;

import com.cashlez.android.garuda.library.cashlezlib.ApplicationState;
import com.cashlez.android.garuda.library.cashlezlib.BasePresenter;
import com.cashlez.android.sdk.payment.CLPaymentResponse;
import com.cashlez.android.sdk.payment.noncash.CLPaymentHandler;
import com.cashlez.android.sdk.payment.noncash.ICLPaymentHandler;
import com.cashlez.android.sdk.payment.noncash.ICLPaymentService;

/**
 * Created by Taslim on 12/05/2017.
 */

public class BasePaymentPresenter<V extends BasePaymentView> extends BasePresenter<V> implements ICLPaymentService {

    protected ICLPaymentHandler paymentHandler;

    protected BasePaymentPresenter() {
    }

    @Override
    public void onGetReaderCompanion(CLPaymentResponse clPayment) {

    }

    @Override
    public void onGetPaymentResponse(CLPaymentResponse clPayment) {

    }

    @Override
    public void onProvideSignature(CLPaymentResponse paymentResponse) {

    }

    public void doCreateHandler(ApplicationState applicationState, Bundle extras) {
        paymentHandler = new CLPaymentHandler(applicationState.getContext(), extras, this);
    }

    public void doRegisterReceiver() {
        paymentHandler.doRegisterReceiver();
    }

    public void doUnRegisterReceiver() {
        paymentHandler.doUnregisterReceiver();
    }

    public void doCloseConnection() {
        paymentHandler.doCloseCompanionConnection();
    }
}
