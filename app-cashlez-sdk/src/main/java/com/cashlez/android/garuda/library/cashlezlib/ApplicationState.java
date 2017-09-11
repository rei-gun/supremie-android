package com.cashlez.android.garuda.library.cashlezlib;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.cashlez.android.sdk.payment.noncash.ICLPaymentHandler;
import com.cashlez.android.sdk.printing.ICLPrintingHandler;

/**
 * Created by Taslim on 11/8/2016.
 */

public class ApplicationState extends Application {

    private ICLPrintingHandler printerHandler;
    private ICLPaymentHandler paymentHandler;
    private Context context;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public void setCurrentContext(Context currentContext) {
        this.context = currentContext;
    }

    public Context getContext(){
        return context;
    }

    public void setPrinterHandler(ICLPrintingHandler printerHandler) {
        this.printerHandler = printerHandler;
    }

    public ICLPrintingHandler getPrinterHandler() {
        return printerHandler;
    }

    public void setReaderHandler(ICLPaymentHandler paymentHandler){
        this.paymentHandler = paymentHandler;
    }

    public ICLPaymentHandler getPaymentHandler(){
        return paymentHandler;
    }



}
