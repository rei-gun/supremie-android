package com.cashlez.android.garuda.library.cashlezlib.activation;

import android.content.Context;

import com.cashlez.android.garuda.library.cashlezlib.BasePresenter;
import com.cashlez.android.sdk.CLResponse;
import com.cashlez.android.sdk.activation.CLActivationHandler;
import com.cashlez.android.sdk.activation.ICLActivationService;

/**
 * Created by Taslim_Hartmann on 5/16/2017.
 */

class ActivationPresenter extends BasePresenter<ActivationView> implements ICLActivationService {

    private CLActivationHandler managePasswordFlow;

    public ActivationPresenter(Context context) {
        managePasswordFlow = new CLActivationHandler(context, this);
    }

    public void doActivate(String activationCode) {
        if (isViewAttached()) {
            getView().onShowLoading();
            managePasswordFlow.doActivate(activationCode);
        }
    }

    @Override
    public void onGetActivationResponse(CLResponse clResponse) {
        if (isViewAttached()) {
            getView().onHideLoading();
            if (clResponse.isSuccess()) {
                getView().onActivationSuccess(clResponse.getMessage());
            } else {
                getView().onActivationSuccess(clResponse.getMessage());
            }
        }
    }


}
