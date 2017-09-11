package com.cashlez.android.garuda.library.cashlezlib.payment.activity;

import com.cashlez.android.garuda.library.cashlezlib.payment.BasePaymentView;
import com.cashlez.android.sdk.payment.CLPaymentResponse;

/**
 * Created by Taslim on 12/05/2017.
 */

public interface PaymentView extends BasePaymentView {
    void onPrinterResponse(String printerMessage);

    void onSendReceiptResponse(String message);

    void onSendHelpMessage(String message);

    void onCompanionSuccess(String companionName);

    void onProvideSignature(CLPaymentResponse paymentResponse);

    void onCompanionError(String message);
}
