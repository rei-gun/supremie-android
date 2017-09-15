package com.cashlez.android.garuda.library.cashlezlib.payment.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cashlez.android.garuda.library.cashlezlib.ISingleInputDialogListener;
import com.cashlez.android.garuda.library.cashlezlib.R;
import com.cashlez.android.garuda.library.cashlezlib.SingleInputDialogFragment;
import com.cashlez.android.garuda.library.cashlezlib.history.PaymentHistoryActivity;
import com.cashlez.android.garuda.library.cashlezlib.payment.BasePaymentActivity;
import com.cashlez.android.garuda.library.cashlezlib.payment.landing.LandingActivity;
import com.cashlez.android.sdk.CLPayment;
import com.cashlez.android.sdk.bean.ApprovalStatus;
import com.cashlez.android.sdk.bean.TransactionType;
import com.cashlez.android.sdk.model.CLPrintObject;
import com.cashlez.android.sdk.payment.CLPaymentResponse;
import com.cashlez.android.sdk.payment.CLVerificationMode;
import com.cashlez.android.sdk.service.CLPrintAlignEnum;
import com.cashlez.android.sdk.service.CLPrintEnum;
import com.cashlez.android.sdk.signaturepad.views.OnSignedListener;
import com.cashlez.android.sdk.signaturepad.views.SignaturePad;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

import static com.cashlez.android.garuda.library.cashlezlib.R.id.btnCancel;
import static com.cashlez.android.garuda.library.cashlezlib.R.id.btnSave;
import static com.cashlez.android.garuda.library.cashlezlib.util.CommonUtil.displayInfo;

public class PaymentActivity extends BasePaymentActivity<PaymentView, PaymentPresenter, PaymentViewState>
        implements PaymentView, ISingleInputDialogListener {

    private static final String TAG = "PaymentActivity";
    @BindView(R.id.payment_email_address)
    EditText emailAddress;
    @BindView(R.id.payment_phone_number)
    EditText phoneNo;
    @BindView(R.id.payment_send_receipt)
    Button sendReceipt;
    @BindView(R.id.payment_success_layout)
    LinearLayout successLayout;
    @BindView(R.id.payment_reader_status)
    TextView readerStatus;
    @BindView(R.id.payment_printer_status)
    TextView printerStatus;
    private Bitmap signBitmap;
    private String signSvg;
    @BindView(R.id.loadingView)
    ProgressBar paymentLoading;

    private CLPayment payment;
    private CLPaymentResponse paymentResponse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getExtras();

    }

    private void getExtras() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            payment = bundle.getParcelable(LandingActivity.PAYMENT);
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.payment;
    }


    private void captureSignatureDialog() {
        final SignaturePad mSignaturePad;
        final Button mClear;
        final Button mGetSign;
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.capture_signature);

        mSignaturePad = (SignaturePad) dialog.findViewById(R.id.signaturePad);
        mClear = (Button) dialog.findViewById(btnCancel);
        mGetSign = (Button) dialog.findViewById(btnSave);
        // if button is clicked, close the custom dialog
        mGetSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signBitmap = mSignaturePad.getTransparentSignatureBitmap();
                signSvg = mSignaturePad.getTrimmedSignatureSvg();
                presenter.doProceedSignature(signSvg);

                successLayout.setVisibility(View.VISIBLE);
                dialog.dismiss();
            }
        });
        mClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        mSignaturePad.setBackgroundColor(Color.WHITE);
        mSignaturePad.setOnSignedListener(new OnSignedListener() {
            @Override
            public void onStartSigning() {
            }

            @Override
            public void onSigned() {
                mGetSign.setEnabled(true);
                mClear.setEnabled(true);
            }

            @Override
            public void onClear() {
                mGetSign.setEnabled(false);
                mClear.setEnabled(true);
            }
        });

        dialog.show();
    }

    @NonNull
    @Override
    public PaymentPresenter createPresenter() {
        return new PaymentPresenter();
    }

    @Override
    protected void onResume() {
        viewState.setStartPayment();
        super.onResume();
        presenter.doStartPayment();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.doStopLocationProvider();
    }

    @Override
    public void onReaderStatus(boolean isConnected) {
        viewState.setStateReaderResponse(isConnected);
        readerStatus.setText(String.valueOf(isConnected));
    }

    @OnClick(R.id.payment_debit_pin)
    protected void doPayDebitPin() {
        successLayout.setVisibility(View.GONE);
        CLPayment debitCLPayment = new CLPayment();
        debitCLPayment.setAmount(payment.getAmount());
        debitCLPayment.setDescription(payment.getDescription());
        debitCLPayment.setTransactionType(TransactionType.DEBIT);
        debitCLPayment.setVerificationMode(CLVerificationMode.PIN);
        debitCLPayment.setImage(payment.getItemImage());
        presenter.doPayDebitPin(debitCLPayment);
    }

    @OnClick(R.id.payment_debit_sign)
    protected void doPayDebitSign() {
        successLayout.setVisibility(View.GONE);
        CLPayment debitSign = new CLPayment();
        debitSign.setAmount(payment.getAmount());
        debitSign.setDescription(payment.getDescription());
        debitSign.setTransactionType(TransactionType.DEBIT);
        debitSign.setVerificationMode(CLVerificationMode.SIGNATURE);
        debitSign.setImage(payment.getItemImage());
        presenter.doPayDebitSign(debitSign);
    }

    @OnClick(R.id.payment_credit_pin)
    protected void doPayCreditPin() {
        successLayout.setVisibility(View.GONE);
        CLPayment creditPin = new CLPayment();
        creditPin.setAmount(payment.getAmount());
        creditPin.setDescription(payment.getDescription());
        creditPin.setTransactionType(TransactionType.CREDIT);
        creditPin.setVerificationMode(CLVerificationMode.PIN);
        creditPin.setImage(payment.getItemImage());
        presenter.doPayCreditPin(creditPin);
    }

    @OnClick(R.id.payment_credit_sign)
    protected void doPayCreditSign() {
        successLayout.setVisibility(View.GONE);
        CLPayment creditSign = new CLPayment();
        creditSign.setAmount(payment.getAmount());
        creditSign.setDescription(payment.getDescription());
        creditSign.setTransactionType(TransactionType.CREDIT);
        creditSign.setVerificationMode(CLVerificationMode.SIGNATURE);
        creditSign.setImage(payment.getItemImage());
        presenter.doPayCreditSign(creditSign);
    }

    @OnClick(R.id.payment_cash)
    protected void doPayCash() {
        successLayout.setVisibility(View.GONE);
        CLPayment cashCLPayment = new CLPayment();
        cashCLPayment.setAmount(payment.getAmount());
        cashCLPayment.setDescription(payment.getDescription());
        cashCLPayment.setTransactionType(TransactionType.CASH);
        cashCLPayment.setImage(payment.getItemImage());
        presenter.doPayCash(cashCLPayment);
    }

    @OnClick(R.id.payment_print)
    protected void doPrint() {
        if (paymentResponse != null) {
            presenter.doPrintPaymentResult(paymentResponse);
        }
    }

    @OnClick(R.id.payment_print_free_text)
    protected void doPrintFreeText() {
        ArrayList<CLPrintObject> freeText = new ArrayList<>();
        CLPrintObject clPrintObject = new CLPrintObject();
        clPrintObject.setFreeText("Rp. 9.999.999");
        clPrintObject.setFormat(CLPrintEnum.TITLE);
        clPrintObject.setAlign(CLPrintAlignEnum.CENTER);
        freeText.add(clPrintObject);
        clPrintObject = new CLPrintObject();
        clPrintObject.setFreeText("Aqua Botol 300 ml");
        clPrintObject.setFormat(CLPrintEnum.BOLD);
        clPrintObject.setAlign(CLPrintAlignEnum.CENTER);
        freeText.add(clPrintObject);
        clPrintObject = new CLPrintObject();
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.bank_logo_0008);
        clPrintObject.setBitmap(largeIcon);
        clPrintObject.setFormat(CLPrintEnum.SMALL_LOGO);
        clPrintObject.setAlign(CLPrintAlignEnum.CENTER);
        freeText.add(clPrintObject);
        presenter.doPrintFreeText(freeText);
    }

    @OnClick(R.id.payment_send_receipt)
    protected void doSendReceipt() {
        if (paymentResponse != null) {
            paymentResponse.setEmailAddress(emailAddress.getText().toString().trim());
            paymentResponse.setHpNo(phoneNo.getText().toString().trim());
            presenter.doSendReceipt(paymentResponse);
        }
    }

    @OnClick(R.id.payment_fab)
    protected void doGetHistoryList() {
        Intent intent = new Intent(this, PaymentHistoryActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.payment_check_reader)
    protected void doCheckReader() {
        successLayout.setVisibility(View.GONE);
        presenter.doCheckReader();
    }

    @Override
    public void onProvideSignature(CLPaymentResponse paymentResponse) {
        Log.d(TAG, paymentResponse.toString());
        displayInfo(this, paymentResponse.getMessage());
        captureSignatureDialog();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.send_message_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.send_message:
                showInputDialog(getResources().getString(R.string.please_input_to_proceed),
                        InputType.TYPE_TEXT_FLAG_MULTI_LINE | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES,
                        getResources().getString(R.string.send_message), 250);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showInputDialog(String title, int firstInputType, String firstHint, int firstMaxLength) {
        FragmentManager fm = getSupportFragmentManager();
        SingleInputDialogFragment editNameDialogFragment =
                SingleInputDialogFragment.newInstance(title, firstInputType, firstHint, firstMaxLength);
        editNameDialogFragment.show(fm, "input_message");
    }

    @Override
    public void onFinishEditDialog(String message) {
        presenter.doSendHelpMessage(message);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    presenter.doConnectLocationProvider(getApplicationState());
                } else {
                    displayInfo(this, getResources().getString(R.string.permission_message));
                    finish();
                }
            }
        }
    }

    @NonNull
    @Override
    public PaymentViewState createViewState() {
        return new PaymentViewState();
    }

    @Override
    public void onNewViewStateInstance() {
        super.onNewViewStateInstance();

        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
                || (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST);
        } else {
            presenter.doConnectLocationProvider(getApplicationState());
        }
    }

    @Override
    public void onShowLoading() {
        viewState.setStateShowLoading();
        showProgress(paymentLoading, true);
    }

    @Override
    public void onHideLoading() {
        viewState.setStateHideLoading();
        showProgress(paymentLoading, false);
    }

    @Override
    public void onPrinterResponse(String printerMessage) {
        printerStatus.setText(printerMessage);
    }

    @Override
    public void onSendReceiptResponse(String message) {
        displayInfo(this, message);
    }

    @Override
    public void onSendHelpMessage(String message) {
        displayInfo(this, message);
    }

    @Override
    public void onCompanionSuccess(String companionName) {
        displayInfo(this, companionName);
    }

    @Override
    public void onCompanionError(String message) {
        displayInfo(this, message);
    }

    @Override
    public void onPrintingResponse(String message) {
        printerStatus.setText(message);
    }

    @Override
    public void onPaymentResult(CLPaymentResponse paymentResponse) {
        viewState.setStatePaymentSuccess(paymentResponse);
        this.paymentResponse = paymentResponse;

        if (this.paymentResponse.getMessage() != null)
            displayInfo(this, paymentResponse.getMessage());
        else if (this.paymentResponse.getErrorMessage() != null)
            displayInfo(this, paymentResponse.getErrorMessage());

        if (this.paymentResponse.isSuccess()) {
            if (paymentResponse.getTransactionStatus() != null) {
                if (paymentResponse.getTransactionStatus() == ApprovalStatus.APPROVED.getCode()) {
                    successLayout.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (paymentLoading.isShown()) {
            displayInfo(this, getResources().getString(R.string.please_input_to_proceed));
        } else {
            super.onBackPressed();
        }
    }
}
