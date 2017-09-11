package com.cashlez.android.garuda.library.cashlezlib.login;

import android.content.Context;

import com.cashlez.android.garuda.library.cashlezlib.BasePresenter;
import com.cashlez.android.sdk.login.CLLoginHandler;
import com.cashlez.android.sdk.login.CLLoginResponse;
import com.cashlez.android.sdk.login.ICLLoginService;

/**
 * Created by Taslim_Hartmann on 5/13/2017.
 */

class LoginPresenter extends BasePresenter<LoginView> implements ICLLoginService {

    private CLLoginHandler loginService;

    LoginPresenter(Context context) {
        loginService = new CLLoginHandler(context, this);
    }

    void doLogin(User user) {
        if (isViewAttached()) {
            getView().onShowLoading();
            loginService.doLogin(user.getUserName(), user.getPin());
        }
    }

    void doLoginAggregator(User user) {
        if (isViewAttached()) {
            getView().onShowLoading();
            loginService.doLogin(user.getPublicKey(), user.getPrivateKey(), user.getUserName(), user.getAggregatorId());
        }
    }

    @Override
    public void onStartActivation(String mobileUpdateURL) {
        if (isViewAttached()) {
            getView().onHideLoading();
            getView().onStartActivation();
        }
    }

    @Override
    public void onLoginResponse(CLLoginResponse clLoginResponse) {
        if (isViewAttached()) {
            getView().onHideLoading();
            if (clLoginResponse.isSuccess()) {
                getView().onLoginSuccess();
            } else {
                getView().onLoginFailed(clLoginResponse.getMessage());
            }
        }
    }
}
