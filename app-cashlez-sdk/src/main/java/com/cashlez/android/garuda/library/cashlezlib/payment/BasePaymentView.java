package com.cashlez.android.garuda.library.cashlezlib.payment;

import com.cashlez.android.garuda.library.cashlezlib.BaseMvpView;
import com.cashlez.android.sdk.payment.CLPaymentResponse;

/**
 * Created by Taslim on 12/05/2017.
 */

public interface BasePaymentView extends BaseMvpView {

    void onReaderStatus(boolean isConnected);

    void onPrintingResponse(String message);

    void onPaymentResult(CLPaymentResponse paymentResponse);

}
