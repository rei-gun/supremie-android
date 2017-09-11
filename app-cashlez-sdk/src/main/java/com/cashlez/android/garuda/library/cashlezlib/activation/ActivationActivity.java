package com.cashlez.android.garuda.library.cashlezlib.activation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.cashlez.android.garuda.library.cashlezlib.BaseViewStateActivity;
import com.cashlez.android.garuda.library.cashlezlib.R;

import butterknife.BindView;
import butterknife.OnClick;

import static com.cashlez.android.garuda.library.cashlezlib.util.CommonUtil.displayInfo;

public class ActivationActivity extends BaseViewStateActivity<ActivationView, ActivationPresenter, ActivationViewState>
        implements ActivationView {

    @BindView((R.id.activation_code))
    EditText activationCode;
    @BindView(R.id.loadingView)
    ProgressBar activationLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @NonNull
    @Override
    public ActivationPresenter createPresenter() {
        return new ActivationPresenter(this);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_activation;

    }

    @OnClick(R.id.activate)
    protected void doActivate(){
        presenter.doActivate(activationCode.getText().toString().trim());
    }

    @Override
    public void onActivationSuccess(String message) {
        displayInfo(this, message);
    }

    @Override
    public void onActivationError(String failedMessage) {
        viewState.setStateActivationFailed(failedMessage);
        displayInfo(this, failedMessage);
    }

    @NonNull
    @Override
    public ActivationViewState createViewState() {
        return new ActivationViewState();
    }

    @Override
    public void onNewViewStateInstance() {
        new ActivationViewState();
    }

    @Override
    public void onShowLoading() {
        viewState.setStateShowLoading();
        showProgress(activationLoading, true);
    }

    @Override
    public void onHideLoading() {
        viewState.setStateHideLoading();
        showProgress(activationLoading, false);
    }
}
