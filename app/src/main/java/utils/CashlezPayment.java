package utils;

//import com.cashlez.android.garuda.library.cashlezlib.ApplicationState;
//import com.cashlez.android.garuda.library.cashlezlib.payment.BasePaymentPresenter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.bintang5.supremie.activity.BasePaymentActivity;
import com.bintang5.supremie.activity.ChooseDiningMethod;
import com.bintang5.supremie.activity.ChoosePaymentMethod;
import com.bintang5.supremie.activity.State;
import com.cashlez.android.sdk.CLPayment;
import com.cashlez.android.sdk.checkcompanion.CLCompanionResponse;
import com.cashlez.android.sdk.checkcompanion.ICLCheckCompanionService;
import com.cashlez.android.sdk.companion.printer.CLPrinterResponse;
import com.cashlez.android.sdk.help.CLHelpResponse;
import com.cashlez.android.sdk.help.ICLHelpMessageService;
import com.cashlez.android.sdk.model.CLPrintObject;
import com.cashlez.android.sdk.payment.CLPaymentResponse;
import com.cashlez.android.sdk.payment.noncash.CLPaymentHandler;
import com.cashlez.android.sdk.payment.noncash.ICLPaymentHandler;
import com.cashlez.android.sdk.payment.noncash.ICLPaymentService;
import com.cashlez.android.sdk.printing.CLPrinterHandler;
import com.cashlez.android.sdk.printing.ICLPrintingService;
import com.cashlez.android.sdk.sendreceipt.CLSendReceiptResponse;
import com.cashlez.android.sdk.sendreceipt.ICLSendReceiptService;

import java.util.ArrayList;

import model.Mie;
import model.MieStock;
import model.Order;

/**
 * Created by Taslim on 12/05/2017.
 */

public class CashlezPayment implements ICLPaymentService,
        ICLPrintingService, ICLSendReceiptService, ICLHelpMessageService, ICLCheckCompanionService {

    private ICLPaymentHandler paymentHandler;
    private BasePaymentActivity callingActivity;
    private CLPrinterHandler printerHandler;

    public CashlezPayment(BasePaymentActivity callingActivity) {
        this.callingActivity = callingActivity;
        initHandler();
    }

    public void initHandler() {
        paymentHandler = new CLPaymentHandler(callingActivity.getBaseContext(), callingActivity.getIntent().getExtras(), this);
        initPrinter();
        //TODO: mau taruh enableUserInput di tempat lain saat printer terhubung
//        callingActivity.enableUserInput();
    }

    private void initPrinter() {
        printerHandler = new CLPrinterHandler(callingActivity.getBaseContext());
        printerHandler.registerPrinterListener(this);
        printerHandler.doInitPrinterConnection();
    }

    public void doStartPayment() {
        paymentHandler.doStartPayment();
    }

    @Override
    public void onGetPaymentResponse(CLPaymentResponse clPayment) {
        Log.v("Payment received", clPayment.toString());
        Log.v("BITCH", String.valueOf(clPayment.isSuccess()));
        if (clPayment.getTransactionId() == null) {
            Log.v("Payment", "null");
        } else {
            Log.v("Payment", clPayment.getTransactionId().toString());
        }

        /*if (clPayment.getErrorCode().equals("008090")) {
            Log.v("Transaction cancelled", "oops");

        */if (clPayment.getTransactionType() != null && clPayment.isSuccess()) {
            Log.v("Payment success: ", clPayment.toString());
            MieStock mieStock = State.getInstance().getMieStock();
            Mie mie = new Mie(mieStock.id, mieStock.brand, mieStock.flavour, State.getInstance().getQuantityMie(),
                    1, mieStock.price,
                    State.getInstance().getPedasLevel() , "", State.getInstance().getToppings());
            ArrayList mies = new ArrayList();
            mies.add(mie);
            Order order = new Order(State.getInstance().getGrandTotal(),
                "card", State.getInstance().getDiningMethod(),
                mies, State.getInstance().getDrinks());
            OrderServer.getInstance(callingActivity).createOrder(this, callingActivity, order);
        } else if (clPayment.getErrorCode() != null) {
            Log.v("Transaction cancelled", clPayment.getErrorMessage());
        } else if (clPayment.getMessage() != null) {
            Log.v("Transaction message", clPayment.getMessage());
        }


    }

    @Override
    public void onProvideSignature(CLPaymentResponse paymentResponse) {
//        if (isViewAttached()) {
//            getView().onHideLoading();
//            getView().onProvideSignature(paymentResponse);
//        }
    }

    @Override
    public void onGetReaderCompanion(CLPaymentResponse clPayment) {
        if (clPayment.isSuccess()) {
            callingActivity.enableUserInput();
        }
        Log.v("Payment response", clPayment.isSuccess() + "");
    }

    @Override
    public void onGetPrintingResponse(CLPrinterResponse printerResponse) {
        if (printerResponse.getMessage().equals(
                callingActivity.getResources().getString(com.cashlez.android.sdk.R.string.printing_finished))) {
            State.getInstance().clear();
            Intent intent = new Intent(callingActivity, ChooseDiningMethod.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            callingActivity.startActivity(intent);
        }
    }

    @Override
    public void onGetSendReceiptResponse(CLSendReceiptResponse receiptResponse) {
//        if (isViewAttached()) {
//            getView().onHideLoading();
//            getView().onSendReceiptResponse(receiptResponse.getMessage());
//        }
    }

    @Override
    public void onGetHelpResponse(CLHelpResponse response) {
//        if (isViewAttached()) {
//            getView().onHideLoading();
//            getView().onSendHelpMessage(response.getMessage());
//        }
    }

    @Override
    public void onGetCompanionResponse(CLCompanionResponse cp) {
//        if (isViewAttached()) {
//            getView().onHideLoading();
//            if(cp.isSuccess()) {
//                getView().onCompanionSuccess(cp.getCompanion().getCompanionName());
//            }else{
//                getView().onCompanionError(cp.getMessage());
//            }
//        }
    }

    void doConnectLocationProvider() {
        paymentHandler.doConnectLocationProvider();
    }

    void doSendHelpMessage(String message) {
//        if (isViewAttached()) {
//            getView().onShowLoading();
//            ICLHelpHandler helpFlow = new CLHelpHandler(context, this);
//            helpFlow.doSendMessage(message);
//        }
    }

    void doProceedSignature(String signSvg) {
//        if (isViewAttached()) {
//            getView().onShowLoading();
            paymentHandler.doProceedSignature(signSvg);
//        }
    }

    void doStopLocationProvider() {
        paymentHandler.doStopUpdateLocation();
    }

    void doPrintPaymentResult(CLPaymentResponse paymentResponse) {
        //ga perlu
//        applicationState.getPrinterHandler().doPrint(paymentResponse);
    }

    void doCheckReader() {
//        if (isViewAttached()) {
//            getView().onShowLoading();

//            ICLCheckCompanionHandler checkReader = new CLCheckCompanionHandler(applicationState.getContext(), this);
//            CLReaderCompanion reader = new CLReaderCompanion();
//            reader.setBtAddress("54:7F:54:68:79:4B");
//            reader.setDeviceTypeEnum(CLDeviceTypeEnum.INGENICO_ICMP_122);
//            reader.setSerialNumber("14076PP20188406");
//            reader.setCompanionName("ICMP-Ingenico");
//            checkReader.doCheckCompanion(reader);
//        }
    }

    void doSendReceipt(CLPaymentResponse paymentResponse) {
//        if (isViewAttached()) {
//            getView().onShowLoading();
//
//            ICLSendReceiptHandler sendReceiptFlow = new CLSendReceiptHandler(applicationState.getContext(), this);
//            paymentResponse.setEmailAddress(paymentResponse.getEmailAddress());
//            paymentResponse.setHpNo(paymentResponse.getHpNo());
//            paymentResponse.setHideLocation(false);
//            paymentResponse.setEmailAddressChecked(true);
//            paymentResponse.setHPChecked(true);
//            sendReceiptFlow.doSendReceipt(paymentResponse);
//        }
    }

    void doPrintFreeText(ArrayList<CLPrintObject> clPrintObject) {
//        applicationState.getPrinterHandler().doPrintFreeText(clPrintObject);
        printerHandler.doPrintFreeText(clPrintObject);
    }

    public void doPayDebitPin(CLPayment debitCLPayment) {
//        if (isViewAttached()) {
//            getView().onShowLoading();
        Log.v("PAYMENT", debitCLPayment.getAmount());
        paymentHandler.doProceedPayment(debitCLPayment);
//        }
    }

    public void doPayDebitSign(CLPayment debitSign) {
//        if (isViewAttached()) {
//            getView().onShowLoading();
            paymentHandler.doProceedPayment(debitSign);
//        }
    }

    public void doPayCreditPin(CLPayment creditPin) {
//        if (isViewAttached()) {
//            getView().onShowLoading();
            paymentHandler.doProceedPayment(creditPin);
//        }
    }

    public void doPayCreditSign(CLPayment creditSign) {
//        if (isViewAttached()) {
//            getView().onShowLoading();
            paymentHandler.doProceedPayment(creditSign);
//        }
    }

    public void doPayCash(CLPayment cashCLPayment) {
//        if (isViewAttached()) {
//            getView().onShowLoading();
            paymentHandler.doProceedPayment(cashCLPayment);
//        }
    }

    public void registerReceiver() {
        paymentHandler.doRegisterReceiver();
    }

    public void unregisterReceiver() {
        paymentHandler.doUnregisterReceiver();
    }

    public void initLocation() {
        paymentHandler.doConnectLocationProvider();
    }

    public void stopLocationServices() {
        paymentHandler.doStopUpdateLocation();
    }
}
