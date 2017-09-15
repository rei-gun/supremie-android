package com.cashlez.android.garuda.library.cashlezlib.payment.landing;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cashlez.android.garuda.library.cashlezlib.ApplicationState;
import com.cashlez.android.garuda.library.cashlezlib.R;
import com.cashlez.android.garuda.library.cashlezlib.payment.BasePaymentActivity;
import com.cashlez.android.garuda.library.cashlezlib.payment.activity.PaymentActivity;
import com.cashlez.android.sdk.CLPayment;
import com.cashlez.android.sdk.payment.CLPaymentResponse;

import butterknife.BindView;
import butterknife.OnClick;

import static com.cashlez.android.garuda.library.cashlezlib.util.CommonUtil.displayInfo;

public class LandingActivity extends BasePaymentActivity<LandingView, LandingPresenter, LandingViewState>
        implements LandingView {

    public static final String PAYMENT = "PAYMENT";

    @BindView(R.id.landing_amount)
    EditText amount;
    @BindView(R.id.landing_description)
    EditText description;
    @BindView(R.id.landing_reader_status)
    TextView readerStatus;
    @BindView(R.id.landing_printer_status)
    TextView printerStatus;
    private String fileName;
    @BindView(R.id.loadingView)
    ProgressBar landingProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (toolbar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.landing;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!bluetoothAdapter.isEnabled())
            return;

        viewState.setStateStartPayment();
        presenter.doStartPayment();
    }

    @NonNull
    @Override
    public LandingPresenter createPresenter() {
        super.createPresenter();
        ApplicationState applicationState = (ApplicationState) getApplicationContext();
        return new LandingPresenter(applicationState);
    }

    @OnClick(R.id.landing_upload)
    protected void doUpload() {
        presenter.doUploadImage(getApplicationState());
    }

    @OnClick(R.id.landing_pay)
    protected void doPay() {
        CLPayment payment = new CLPayment();
        payment.setAmount(amount.getText().toString().trim());
        payment.setDescription(description.getText().toString().trim());
        payment.setImage(fileName);

        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PAYMENT, payment);
        startActivity(intent);
    }

    @NonNull
    @Override
    public LandingViewState createViewState() {
        super.createViewState();
        return new LandingViewState();
    }

    @Override
    public void onNewViewStateInstance() {
        super.onNewViewStateInstance();
    }

    @Override
    public void onUploadedSuccess(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void onUploadFailed(String failedMessage) {
        displayInfo(this, failedMessage);
    }

    @Override
    public void onReaderStatus(boolean isConnected) {
        viewState.setStateReaderResponse(isConnected);
        readerStatus.setText(String.valueOf(isConnected));
    }

    @Override
    public void onPrintingResponse(String message) {
        printerStatus.setText(message);
    }

    @Override
    public void onPaymentResult(CLPaymentResponse paymentResponse) {
        viewState.setStatePaymentSuccess(paymentResponse);
        displayInfo(this, paymentResponse.getMessage());
    }

    @Override
    public void onShowLoading() {
        viewState.setStateShowLoading();
        showProgress(landingProgress, true);
    }

    @Override
    public void onHideLoading() {
        viewState.setStateHideLoading();
        showProgress(landingProgress, false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }

        switch (requestCode) {
            case REQUEST_BT:
                presenter.doCreateHandler(getApplicationState(), getIntent().getExtras());
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.doCloseConnection();
        presenter.doClosePrinterConnection();
    }
}