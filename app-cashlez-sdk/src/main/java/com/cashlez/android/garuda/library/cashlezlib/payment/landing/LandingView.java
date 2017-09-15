package com.cashlez.android.garuda.library.cashlezlib.payment.landing;

import com.cashlez.android.garuda.library.cashlezlib.payment.BasePaymentView;
import com.cashlez.android.sdk.payment.CLPaymentResponse;

/**
 * Created by Taslim on 12/05/2017.
 */

public interface LandingView extends BasePaymentView {

    void onUploadedSuccess(String fileName);

    void onUploadFailed(String failedMessage);

}
