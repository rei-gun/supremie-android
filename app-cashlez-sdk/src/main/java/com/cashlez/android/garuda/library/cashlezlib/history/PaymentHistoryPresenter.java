package com.cashlez.android.garuda.library.cashlezlib.history;

import android.content.Context;
import android.util.Log;

import com.cashlez.android.sdk.payment.CLPaymentResponse;
import com.cashlez.android.sdk.paymenthistory.CLPaymentHistoryHandler;
import com.cashlez.android.sdk.paymenthistory.CLPaymentHistoryResponse;
import com.cashlez.android.sdk.paymenthistory.ICLPaymentHistoryHandler;
import com.cashlez.android.sdk.paymenthistory.ICLPaymentHistoryService;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taslim_Hartmann on 5/15/2017.
 */

class PaymentHistoryPresenter extends MvpBasePresenter<PaymentHistoryView> implements ICLPaymentHistoryService {

    private ICLPaymentHistoryHandler historyHandler;
    private List<CLPaymentResponse> paymentList = new ArrayList<>();

    PaymentHistoryPresenter(Context context) {
        historyHandler = new CLPaymentHistoryHandler(context, this);
    }

    void doPaymentDetail(int page, boolean pullToRefresh) {
        getView().showLoading(pullToRefresh);
        Log.d("Page: ", page + "");
        historyHandler.doGetSalesHistory(page, "", "");
    }

    void doPaymentDetail(int page, String invoiceNo, String approvalCode) {
        getView().showLoading(true);
        historyHandler.doGetSalesHistory(page, invoiceNo, approvalCode);
    }

    @Override
    public void onGetSalesHistoryResponse(CLPaymentHistoryResponse response) {
        if (isViewAttached()) {
            if (response.getPaymentList().size() > 0) {
                paymentList.addAll(response.getPaymentList());
                getView().setData(paymentList);
                getView().showContent();
            } else {
                getView().showError(new Throwable(response.getMessage()), true);
            }
        }
    }
}