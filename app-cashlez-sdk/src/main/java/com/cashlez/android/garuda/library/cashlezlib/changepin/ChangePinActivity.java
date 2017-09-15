package com.cashlez.android.garuda.library.cashlezlib.changepin;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.cashlez.android.garuda.library.cashlezlib.BaseViewStateActivity;
import com.cashlez.android.garuda.library.cashlezlib.R;
import com.cashlez.android.sdk.managepassword.CLManagePasswordHandler;
import com.cashlez.android.sdk.managepassword.CLManagePasswordResponse;
import com.cashlez.android.sdk.managepassword.ICLManagePasswordHandler;

import butterknife.BindView;
import butterknife.OnClick;

import static com.cashlez.android.garuda.library.cashlezlib.util.CommonUtil.displayInfo;

public class ChangePinActivity extends BaseViewStateActivity<ChangePinView, ChangePinPresenter, ChangePinViewState>
        implements ChangePinView {

    @BindView(R.id.change_pin_user_name)
    EditText userName;
    @BindView(R.id.loadingView)
    ProgressBar changePinLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @NonNull
    @Override
    public ChangePinPresenter createPresenter() {
        return new ChangePinPresenter(this);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.change_password;
    }

    @OnClick(R.id.change_pin)
    protected void doChangePin() {
        presenter.doChangePin(userName.getText().toString().trim());
    }

    @Override
    public void onShowLoading() {
        viewState.setStateShowLoading();
        showProgress(changePinLoading, true);
    }

    @Override
    public void onHideLoading() {
        viewState.setStateHideLoading();
        showProgress(changePinLoading, false);
    }

    @NonNull
    @Override
    public ChangePinViewState createViewState() {
        return new ChangePinViewState();
    }

    @Override
    public void onNewViewStateInstance() {
        new ChangePinViewState();
    }

    @Override
    public void onChangePinSuccess(String message) {
        displayInfo(this, message);
    }

    @Override
    public void onChangePinError(String message) {
        viewState.setStateChangePinFailed(message);
        displayInfo(this, message);
    }
}
