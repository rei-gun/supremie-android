package com.bintang5.supremie.activity;

import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import javax.annotation.Nullable;

import utils.CashlezPayment;

/**
 * Created by rei on 11/2/17.
 */

public class BasePaymentActivity extends AppCompatActivity {

    protected BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    protected CashlezPayment cashlezPayment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!bluetoothAdapter.isEnabled()) {
            //TODO: bluetooth not enabled
        } else {
            cashlezPayment = new CashlezPayment(this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        cashlezPayment.unregisterReceiver();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cashlezPayment.registerReceiver();
    }

    /**
     * Disables the entire screen from user input
     */
    public void disableUserInput() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    /**
     * Enables the entire screen for user input
     */
    public void enableUserInput() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }
}
