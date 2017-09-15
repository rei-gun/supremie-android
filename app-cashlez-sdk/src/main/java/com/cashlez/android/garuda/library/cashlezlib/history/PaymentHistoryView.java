package com.cashlez.android.garuda.library.cashlezlib.history;

import com.cashlez.android.sdk.payment.CLPaymentResponse;
import com.hannesdorfmann.mosby3.mvp.MvpView;
import com.hannesdorfmann.mosby3.mvp.lce.MvpLceView;

import java.util.List;

/**
 * Created by Taslim_Hartmann on 5/15/2017.
 */

interface PaymentHistoryView extends MvpLceView<List<CLPaymentResponse>> {
}
