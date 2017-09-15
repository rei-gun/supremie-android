package utils;

import android.content.Context;
import android.util.Log;

import com.cashlez.android.sdk.login.CLLoginHandler;
import com.cashlez.android.sdk.login.CLLoginResponse;
import com.cashlez.android.sdk.login.ICLLoginService;

import model.User;

/**
 * Created by rei on 6/09/17.
 */

public class CashlezLogin implements ICLLoginService {

    private CLLoginHandler loginService;

    public CashlezLogin(Context context) {
        loginService = new CLLoginHandler(context, this);
    }

    void doLogin(User user) {
        loginService.doLogin(user.getUserName(), user.getPin());
    }

    public void doLoginAggregator(User user) {
        loginService.doLogin(user.getPublicKey(), user.getPrivateKey(), user.getUserName(), user.getAggregatorId());
    }

    @Override
    public void onStartActivation(String mobileUpdateURL) {
//        getView().onHideLoading();
//        getView().onStartActivation();
    }

    @Override
    public void onLoginResponse(CLLoginResponse clLoginResponse) {
        if (clLoginResponse.isSuccess()) {
            //login ok
            Log.v("BANG", "LOGGED IN");
        } else {
            //login not OK
            Log.v("BANG", "LOGIN FAIL"+clLoginResponse.getMessage());
        }
    }
}
