package com.cashlez.android.garuda.library.cashlezlib.login;

import com.cashlez.android.garuda.library.cashlezlib.BaseMvpView;
import com.hannesdorfmann.mosby3.mvp.MvpView;

/**
 * Created by Taslim_Hartmann on 5/13/2017.
 */

interface LoginView extends BaseMvpView {

    void onStartActivation();

    void onLoginSuccess();

    void onLoginFailed(String failedMessage);

}
