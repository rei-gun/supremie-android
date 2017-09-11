package com.cashlez.android.garuda.library.cashlezlib.payment;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.cashlez.android.garuda.library.cashlezlib.BaseViewStateActivity;
import com.hannesdorfmann.mosby3.mvp.viewstate.ViewState;

/**
 * Created by Taslim on 10/17/2016.
 */

public abstract class BasePaymentActivity<V extends BasePaymentView, P extends BasePaymentPresenter<V>,
        VS extends ViewState<V>> extends BaseViewStateActivity<V, P, VS> {

    protected static final int MY_PERMISSIONS_REQUEST = 8;
    protected static final int REQUEST_BT = 3;
    protected BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!bluetoothAdapter.isEnabled())
            return;

        presenter.doRegisterReceiver();
    }

    @NonNull
    @Override
    public P createPresenter() {
        return (P) new BasePaymentPresenter();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!bluetoothAdapter.isEnabled())
            return;

        presenter.doUnRegisterReceiver();
    }

    @NonNull
    @Override
    public VS createViewState() {
        return (VS) new BasePaymentViewState();
    }

    @Override
    public void onNewViewStateInstance() {
        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBt = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBt, REQUEST_BT);
        } else {
            presenter.doCreateHandler(getApplicationState(), getIntent().getExtras());
        }
    }
}
