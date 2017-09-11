package com.cashlez.android.garuda.library.cashlezlib;

import com.hannesdorfmann.mosby3.mvp.MvpView;

/**
 * Created by Taslim on 12/05/2017.
 */

public interface BaseMvpView extends MvpView {
    void onShowLoading();

    void onHideLoading();

}
