package com.cashlez.android.garuda.library.cashlezlib.history;

import android.content.Context;

import com.cashlez.android.garuda.library.cashlezlib.ApplicationState;
import com.cashlez.android.garuda.library.cashlezlib.BasePresenter;
import com.cashlez.android.sdk.companion.printer.CLPrinterResponse;
import com.cashlez.android.sdk.imagehandler.CLDownloadImageResponse;
import com.cashlez.android.sdk.payment.CLPaymentResponse;
import com.cashlez.android.sdk.payment.voidpayment.CLVoidPaymentHandler;
import com.cashlez.android.sdk.payment.voidpayment.CLVoidResponse;
import com.cashlez.android.sdk.payment.voidpayment.ICLVoidPaymentHandler;
import com.cashlez.android.sdk.payment.voidpayment.ICLVoidService;
import com.cashlez.android.sdk.paymenthistorydetail.CLPaymentHistoryDetailHandler;
import com.cashlez.android.sdk.paymenthistorydetail.CLPaymentHistoryDetailResponse;
import com.cashlez.android.sdk.paymenthistorydetail.ICLPaymentHistoryDetailHandler;
import com.cashlez.android.sdk.paymenthistorydetail.ICLPaymentHistoryDetailService;
import com.cashlez.android.sdk.printing.ICLPrintingService;
import com.cashlez.android.sdk.sendreceipt.CLSendReceiptResponse;
import com.cashlez.android.sdk.sendreceipt.ICLSendReceiptService;

/**
 * Created by Taslim_Hartmann on 5/15/2017.
 */

class HistoryDetailPresenter extends BasePresenter<HistoryDetailView>
        implements ICLVoidService, ICLSendReceiptService, ICLPrintingService, ICLPaymentHistoryDetailService {

    private ICLPaymentHistoryDetailHandler historyDetailHandler;
    private ICLVoidPaymentHandler voidHandler;

    public HistoryDetailPresenter(Context context) {
        historyDetailHandler = new CLPaymentHistoryDetailHandler(context, this);
        voidHandler = new CLVoidPaymentHandler(context, this);
    }

    public void doSalesHistoryDetail(String transactionId) {
        if (isViewAttached()) {
            getView().onShowLoading();
            historyDetailHandler.doGetSalesHistoryDetail(transactionId);
        }
    }

    @Override
    public void onGetSalesHistoryDetailResponse(CLPaymentHistoryDetailResponse historyDetailResponse) {
        if (isViewAttached()) {
            getView().onHideLoading();
            if (historyDetailResponse.isSuccess()) {
                getView().onPaymentHistorySuccess(historyDetailResponse);
            } else {
                getView().onPaymentHistoryError(historyDetailResponse.getMessage());
            }
        }
    }

    @Override
    public void onGetSalesHistoryImage(CLDownloadImageResponse downloadResponse) {
        if (isViewAttached()) {
            getView().onHideLoading();
            getView().onLoadPaymentImage(downloadResponse.getPhoto());
        }
    }

    void doVoidPayment(String userName, String password, CLPaymentResponse paymentResponse) {
        if (isViewAttached()) {
            getView().onShowLoading();
            voidHandler.doVoidPayment(userName, password, paymentResponse);
        }
    }

//    void registerPrinter(ICLPrintingHandler printerHandler) {
//        printerHandler.registerPrinterListener(this);
//    }

    @Override
    public void onGetVoidResponse(CLVoidResponse voidResponse) {
        if (isViewAttached()) {
            getView().onHideLoading();
            getView().onVoidResponse(voidResponse.getMessage());
        }
    }

    @Override
    public void onGetSendReceiptResponse(CLSendReceiptResponse receiptResponse) {
        if (isViewAttached()) {
            getView().onHideLoading();
            getView().onSendReceiptResponse(receiptResponse.getMessage());
        }
    }

    @Override
    public void onGetPrintingResponse(CLPrinterResponse printerResponse) {
        if (isViewAttached()) {
            getView().onHideLoading();
            getView().onPrinterResponse(printerResponse);
        }
    }

    public void doPrint(ApplicationState applicationState, CLPaymentResponse paymentResponse) {
        applicationState.getPrinterHandler().doPrint(paymentResponse);
    }
}
