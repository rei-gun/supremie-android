package com.cashlez.android.garuda.library.cashlezlib.history;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.cashlez.android.garuda.library.cashlezlib.BaseViewState;
import com.cashlez.android.sdk.paymenthistorydetail.CLPaymentHistoryDetailResponse;
import com.hannesdorfmann.mosby3.mvp.viewstate.RestorableViewState;

/**
 * Created by Taslim_Hartmann on 5/15/2017.
 */

class HistoryDetailViewState extends BaseViewState<HistoryDetailView> implements RestorableViewState<HistoryDetailView> {

    private final String KEY_DATA = "HistoryDetailViewState-data";
    private static final int HISTORY_DETAIL_SUCCESS = 3;
    private Parcelable data;

    @Override
    public void saveInstanceState(@NonNull Bundle out) {
        super.saveInstanceState(out);
        out.putParcelable(KEY_DATA, data);
    }

    @Override
    public RestorableViewState<HistoryDetailView> restoreInstanceState(Bundle in) {
        super.restoreInstanceState(in);
        if (in == null) {
            return null;
        }
        data = in.getParcelable(KEY_DATA);
        return this;
    }

    @Override
    public void apply(HistoryDetailView view, boolean retained) {
        super.apply(view, retained);
        if (currentState == HISTORY_DETAIL_SUCCESS) {
            view.onPaymentHistorySuccess((CLPaymentHistoryDetailResponse) data);
        }
    }

    @Override
    protected void onShowLoading(HistoryDetailView view) {
        view.onShowLoading();
    }

    @Override
    protected void onHideLoading(HistoryDetailView view) {
        view.onHideLoading();
    }

    void setHistoryResponse(CLPaymentHistoryDetailResponse historyDetailResponse) {
        data = historyDetailResponse;
        currentState = HISTORY_DETAIL_SUCCESS;
    }
}
