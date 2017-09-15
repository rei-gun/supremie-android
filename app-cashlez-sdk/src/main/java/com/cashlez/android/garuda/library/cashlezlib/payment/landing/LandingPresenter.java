package com.cashlez.android.garuda.library.cashlezlib.payment.landing;

import com.cashlez.android.garuda.library.cashlezlib.ApplicationState;
import com.cashlez.android.garuda.library.cashlezlib.payment.BasePaymentPresenter;
import com.cashlez.android.sdk.companion.printer.CLPrinterResponse;
import com.cashlez.android.sdk.imagehandler.CLUploadHandler;
import com.cashlez.android.sdk.imagehandler.CLUploadResponse;
import com.cashlez.android.sdk.imagehandler.ICLUploadHandler;
import com.cashlez.android.sdk.imagehandler.ICLUploadService;
import com.cashlez.android.sdk.payment.CLPaymentResponse;
import com.cashlez.android.sdk.printing.CLPrinterHandler;
import com.cashlez.android.sdk.printing.ICLPrintingHandler;
import com.cashlez.android.sdk.printing.ICLPrintingService;

/**
 * Created by Taslim on 12/05/2017.
 */

class LandingPresenter extends BasePaymentPresenter<LandingView> implements ICLUploadService, ICLPrintingService {

    private final ICLPrintingHandler printerHandler;

    LandingPresenter(ApplicationState applicationState) {
        printerHandler = new CLPrinterHandler(applicationState.getContext());
        printerHandler.registerPrinterListener(this);
        printerHandler.doInitPrinterConnection();
        applicationState.setPrinterHandler(printerHandler);
    }

    @Override
    public void doRegisterReceiver() {
        super.doRegisterReceiver();
    }

    @Override
    public void doUnRegisterReceiver() {
        super.doUnRegisterReceiver();
    }

    void doStartPayment() {
        paymentHandler.doStartPayment();
    }

    void doUploadImage(ApplicationState applicationState) {
        if (isViewAttached()) {
            getView().onShowLoading();
            ICLUploadHandler uploadHandler = new CLUploadHandler(applicationState.getContext(), this);
            uploadHandler.doUpload("/storage/emulated/0/Cashlez/123.jpg");
        }
    }

    @Override
    public void onGetUploadResponse(CLUploadResponse clUploadResponse) {
        if (isViewAttached()) {
            getView().onHideLoading();
            if (clUploadResponse.isSuccess()) {
                getView().onUploadedSuccess(clUploadResponse.getFileName());
            } else {
                getView().onUploadFailed(clUploadResponse.getMessage());
            }
        }
    }

    @Override
    public void onGetPrintingResponse(CLPrinterResponse message) {
        if (isViewAttached()) {
            getView().onPrintingResponse(message.getMessage());
        }
    }

    @Override
    public void onGetPaymentResponse(CLPaymentResponse clPayment) {
        if (isViewAttached()) {
            getView().onPaymentResult(clPayment);
        }
    }

    @Override
    public void onGetReaderCompanion(CLPaymentResponse clPayment) {
        if (isViewAttached() && clPayment != null) {
            getView().onReaderStatus(clPayment.isSuccess());
        }
    }

    void doClosePrinterConnection() {
        printerHandler.doClosePrinterConnection();
    }
}
