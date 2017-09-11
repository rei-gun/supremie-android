package com.cashlez.android.garuda.library.cashlezlib.changepin;

import com.cashlez.android.garuda.library.cashlezlib.BaseMvpView;

/**
 * Created by Taslim_Hartmann on 5/16/2017.
 */

interface ChangePinView extends BaseMvpView{
    void onChangePinSuccess(String message);

    void onChangePinError(String message);
}
