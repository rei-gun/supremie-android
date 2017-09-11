package com.cashlez.android.garuda.library.cashlezlib.payment.activity;

import com.cashlez.android.garuda.library.cashlezlib.ApplicationState;
import com.cashlez.android.garuda.library.cashlezlib.payment.BasePaymentPresenter;
import com.cashlez.android.sdk.CLPayment;
import com.cashlez.android.sdk.checkcompanion.CLCheckCompanionHandler;
import com.cashlez.android.sdk.checkcompanion.CLCompanionResponse;
import com.cashlez.android.sdk.checkcompanion.ICLCheckCompanionHandler;
import com.cashlez.android.sdk.checkcompanion.ICLCheckCompanionService;
import com.cashlez.android.sdk.companion.printer.CLPrinterResponse;
import com.cashlez.android.sdk.companion.reader.CLReaderCompanion;
import com.cashlez.android.sdk.help.CLHelpHandler;
import com.cashlez.android.sdk.help.CLHelpResponse;
import com.cashlez.android.sdk.help.ICLHelpHandler;
import com.cashlez.android.sdk.help.ICLHelpMessageService;
import com.cashlez.android.sdk.model.CLDeviceTypeEnum;
import com.cashlez.android.sdk.model.CLPrintObject;
import com.cashlez.android.sdk.payment.CLPaymentResponse;
import com.cashlez.android.sdk.payment.noncash.ICLPaymentService;
import com.cashlez.android.sdk.printing.ICLPrintingService;
import com.cashlez.android.sdk.sendreceipt.CLSendReceiptHandler;
import com.cashlez.android.sdk.sendreceipt.CLSendReceiptResponse;
import com.cashlez.android.sdk.sendreceipt.ICLSendReceiptHandler;
import com.cashlez.android.sdk.sendreceipt.ICLSendReceiptService;

import java.util.ArrayList;

/**
 * Created by Taslim on 12/05/2017.
 */

class PaymentPresenter extends BasePaymentPresenter<PaymentView> implements ICLPaymentService,
        ICLPrintingService, ICLSendReceiptService, ICLHelpMessageService, ICLCheckCompanionService {

    private ApplicationState applicationState;

    PaymentPresenter() {
    }

    void doStartPayment() {
        paymentHandler.doStartPayment();
    }

    @Override
    public void onGetPaymentResponse(CLPaymentResponse clPayment) {
        if (isViewAttached()) {
            if (clPayment.getTransactionStatus() != null || !clPayment.isSuccess()) {
                getView().onHideLoading();
            }
            getView().onPaymentResult(clPayment);
        }
    }

    @Override
    public void onProvideSignature(CLPaymentResponse paymentResponse) {
        if (isViewAttached()) {
            getView().onHideLoading();
            getView().onProvideSignature(paymentResponse);
        }
    }

    @Override
    public void onGetReaderCompanion(CLPaymentResponse clPayment) {
        if (isViewAttached() && clPayment != null) {
            getView().onHideLoading();
            getView().onReaderStatus(clPayment.isSuccess());
        }
    }

    @Override
    public void onGetPrintingResponse(CLPrinterResponse printerResponse) {
        if (isViewAttached()) {
            getView().onHideLoading();
            getView().onPrinterResponse(printerResponse.getMessage());
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
    public void onGetHelpResponse(CLHelpResponse response) {
        if (isViewAttached()) {
            getView().onHideLoading();
            getView().onSendHelpMessage(response.getMessage());
        }
    }

    @Override
    public void onGetCompanionResponse(CLCompanionResponse cp) {
        if (isViewAttached()) {
            getView().onHideLoading();
            if(cp.isSuccess()) {
                getView().onCompanionSuccess(cp.getCompanion().getCompanionName());
            }else{
                getView().onCompanionError(cp.getMessage());
            }
        }
    }

    void doConnectLocationProvider(ApplicationState applicationState) {
        this.applicationState = applicationState;
        paymentHandler.doConnectLocationProvider();
        applicationState.getPrinterHandler().registerPrinterListener(this);
        applicationState.getPrinterHandler().doInitPrinterConnection();
    }

    void doSendHelpMessage(String message) {
        if (isViewAttached()) {
            getView().onShowLoading();
            ICLHelpHandler helpFlow = new CLHelpHandler(applicationState.getContext(), this);
            helpFlow.doSendMessage(message);
        }
    }

    void doProceedSignature(String signSvg) {
        if (isViewAttached()) {
            getView().onShowLoading();
            paymentHandler.doProceedSignature(signSvg);
        }
    }

    void doStopLocationProvider() {
        paymentHandler.doStopUpdateLocation();
    }

    void doPrintPaymentResult(CLPaymentResponse paymentResponse) {
        applicationState.getPrinterHandler().doPrint(paymentResponse);
    }

    void doCheckReader() {
        if (isViewAttached()) {
            getView().onShowLoading();

            ICLCheckCompanionHandler checkReader = new CLCheckCompanionHandler(applicationState.getContext(), this);
            CLReaderCompanion reader = new CLReaderCompanion();
            reader.setBtAddress("54:7F:54:68:79:4B");
            reader.setDeviceTypeEnum(CLDeviceTypeEnum.INGENICO_ICMP_122);
            reader.setSerialNumber("14076PP20188406");
            reader.setCompanionName("ICMP-Ingenico");
            checkReader.doCheckCompanion(reader);
        }
    }

    void doSendReceipt(CLPaymentResponse paymentResponse) {
        if (isViewAttached()) {
            getView().onShowLoading();

            ICLSendReceiptHandler sendReceiptFlow = new CLSendReceiptHandler(applicationState.getContext(), this);
            paymentResponse.setEmailAddress(paymentResponse.getEmailAddress());
            paymentResponse.setHpNo(paymentResponse.getHpNo());
            paymentResponse.setHideLocation(false);
            paymentResponse.setEmailAddressChecked(true);
            paymentResponse.setHPChecked(true);
            sendReceiptFlow.doSendReceipt(paymentResponse);
        }
    }

    void doPrintFreeText(ArrayList<CLPrintObject> clPrintObject) {
        applicationState.getPrinterHandler().doPrintFreeText(clPrintObject);
    }

    void doPayDebitPin(CLPayment debitCLPayment) {
        if (isViewAttached()) {
            getView().onShowLoading();
            paymentHandler.doProceedPayment(debitCLPayment);
        }
    }

    void doPayDebitSign(CLPayment debitSign) {
        if (isViewAttached()) {
            getView().onShowLoading();
            paymentHandler.doProceedPayment(debitSign);
        }
    }

    void doPayCreditPin(CLPayment creditPin) {
        if (isViewAttached()) {
            getView().onShowLoading();
            paymentHandler.doProceedPayment(creditPin);
        }
    }

    void doPayCreditSign(CLPayment creditSign) {
        if (isViewAttached()) {
            getView().onShowLoading();
            paymentHandler.doProceedPayment(creditSign);
        }
    }

    void doPayCash(CLPayment cashCLPayment) {
        if (isViewAttached()) {
            getView().onShowLoading();
            paymentHandler.doProceedPayment(cashCLPayment);
        }
    }
}
