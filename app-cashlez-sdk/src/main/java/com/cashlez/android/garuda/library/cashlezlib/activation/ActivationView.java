package com.cashlez.android.garuda.library.cashlezlib.activation;

import com.cashlez.android.garuda.library.cashlezlib.BaseMvpView;

/**
 * Created by Taslim_Hartmann on 5/16/2017.
 */

interface ActivationView extends BaseMvpView{
    void onActivationSuccess(String message);

    void onActivationError(String failedMessage);
}
