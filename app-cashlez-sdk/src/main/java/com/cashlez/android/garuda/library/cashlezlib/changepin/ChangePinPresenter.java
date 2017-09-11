package com.cashlez.android.garuda.library.cashlezlib.changepin;

import android.content.Context;

import com.cashlez.android.garuda.library.cashlezlib.BasePresenter;
import com.cashlez.android.sdk.managepassword.CLManagePasswordHandler;
import com.cashlez.android.sdk.managepassword.CLManagePasswordResponse;
import com.cashlez.android.sdk.managepassword.ICLManagePasswordHandler;
import com.cashlez.android.sdk.managepassword.ICLManagePasswordService;

/**
 * Created by Taslim_Hartmann on 5/16/2017.
 */

class ChangePinPresenter extends BasePresenter<ChangePinView> implements ICLManagePasswordService {

    private CLManagePasswordHandler managePasswordHandler;

    public ChangePinPresenter(Context context) {
        managePasswordHandler = new CLManagePasswordHandler(context, this);
    }

    public void doChangePin(String userName) {
        if (isViewAttached()) {
            getView().onShowLoading();
            managePasswordHandler.doChangePassword(userName);
        }
    }

    @Override
    public void onGetManagePasswordResponse(CLManagePasswordResponse response) {
        if (isViewAttached()) {
            getView().onHideLoading();
            if (response.isSuccess()) {
                getView().onChangePinSuccess(response.getMessage());
            } else {
                getView().onChangePinError(response.getMessage());
            }
        }
    }


}
