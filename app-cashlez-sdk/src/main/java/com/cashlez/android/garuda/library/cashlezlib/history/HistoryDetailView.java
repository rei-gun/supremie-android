package com.cashlez.android.garuda.library.cashlezlib.history;

import android.graphics.Bitmap;

import com.cashlez.android.garuda.library.cashlezlib.BaseMvpView;
import com.cashlez.android.sdk.companion.printer.CLPrinterResponse;
import com.cashlez.android.sdk.payment.CLPaymentResponse;
import com.cashlez.android.sdk.paymenthistorydetail.CLPaymentHistoryDetailResponse;

/**
 * Created by Taslim_Hartmann on 5/15/2017.
 */

interface HistoryDetailView extends BaseMvpView{
    void onLoadPaymentImage(Bitmap photo);

    void onPaymentHistorySuccess(CLPaymentHistoryDetailResponse paymentHistoryDetailResponse);

    void onPaymentHistoryError(String message);

    void onVoidResponse(String message);

    void onSendReceiptResponse(String message);

    void onPrinterResponse(CLPrinterResponse printerResponse);

}
