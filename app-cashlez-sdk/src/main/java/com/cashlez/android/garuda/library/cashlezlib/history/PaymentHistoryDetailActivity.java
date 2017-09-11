package com.cashlez.android.garuda.library.cashlezlib.history;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cashlez.android.garuda.library.cashlezlib.BaseViewStateActivity;
import com.cashlez.android.garuda.library.cashlezlib.DoubleInputDialogFragment;
import com.cashlez.android.garuda.library.cashlezlib.IInputDialogListener;
import com.cashlez.android.garuda.library.cashlezlib.R;
import com.cashlez.android.sdk.bean.ApprovalStatus;
import com.cashlez.android.sdk.companion.printer.CLPrinterResponse;
import com.cashlez.android.sdk.payment.CLPaymentResponse;
import com.cashlez.android.sdk.paymenthistorydetail.CLPaymentHistoryDetailResponse;
import com.cashlez.android.sdk.util.CLDateUtil;

import butterknife.BindView;

import static com.cashlez.android.garuda.library.cashlezlib.history.PaymentHistoryActivity.TRANSACTION_ID;
import static com.cashlez.android.garuda.library.cashlezlib.util.CommonUtil.displayInfo;


public class PaymentHistoryDetailActivity extends BaseViewStateActivity<HistoryDetailView,
        HistoryDetailPresenter, HistoryDetailViewState> implements HistoryDetailView, IInputDialogListener {

    private static final String TAG = "HistoryDetailActivity";

    @BindView(R.id.transaction_history_amount)
    TextView amount;
    @BindView(R.id.transaction_history_detail_card_number)
    TextView cardHolderName;
    @BindView(R.id.transaction_history_card_image)
    ImageView cardImage;
    @BindView(R.id.card_label)
    TextView cardLabel;
    @BindView(R.id.history_row_transaction_status)
    TextView transactionStatus;
    @BindView(R.id.approval_code_content)
    TextView approvalCode;
    @BindView(R.id.trace_number_content)
    TextView traceNo;
    @BindView(R.id.batch_number_content)
    TextView batchNo;
    @BindView(R.id.transaction_id_content)
    TextView trxId;
    @BindView(R.id.invoice_no_content)
    TextView invoiceNo;
    @BindView(R.id.rref_no_content)
    TextView reffNo;
    @BindView(R.id.tc_content)
    TextView tc;
    @BindView(R.id.transaction_date_content)
    TextView transDate;
    @BindView(R.id.transaction_time_content)
    TextView transTime;
    @BindView(R.id.voided_date_content)
    TextView voidedDate;
    @BindView(R.id.voided_by_content)
    TextView voidedBy;
    @BindView(R.id.merchant_id_full_content)
    TextView mid;
    @BindView(R.id.tid_content)
    TextView tid;
    @BindView(R.id.transaction_type_content)
    TextView transType;
    @BindView(R.id.transaction_detail_content)
    TextView transDescription;
    @BindView(R.id.product_picture)
    ImageView paymentImage;
    @BindView(R.id.progress_history)
    ProgressBar progressBar;
    @BindView(R.id.loadingView)
    ProgressBar historyDetailLoading;

    private String transactionId;

    private CLPaymentHistoryDetailResponse historyDetailResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getExtras();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.history_detail;
    }

    private void getExtras() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            transactionId = bundle.getString(TRANSACTION_ID);
        }
    }

    @NonNull
    @Override
    public HistoryDetailPresenter createPresenter() {
        return new HistoryDetailPresenter(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.history_detail_menu, menu);
        return true;
    }

    private void showInputDialog(String title, int firstInputType, String firstHint, int firstMaxLength,
                                 int secondType, String secondHint, int secondMaxLength) {
        FragmentManager fm = getSupportFragmentManager();
        DoubleInputDialogFragment editNameDialogFragment =
                DoubleInputDialogFragment.newInstance(title, firstInputType, firstHint, firstMaxLength,
                        secondType, secondHint, secondMaxLength);
        editNameDialogFragment.show(fm, "input_user_name");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.void_payment:
                showInputDialog(getResources().getString(R.string.please_input_to_proceed), InputType.TYPE_CLASS_TEXT,
                        getResources().getString(R.string.void_user), 30,
                        InputType.TYPE_CLASS_TEXT, getResources().getString(R.string.void_password), 15);
                return true;

            case R.id.print:
                if (historyDetailResponse != null) {
                    presenter.doPrint(getApplicationState(), historyDetailResponse.getPaymentResponse());
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onFinishEditDialog(String userName, String password) {
        presenter.doVoidPayment(userName, password, historyDetailResponse.getPaymentResponse());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void setDetails(CLPaymentHistoryDetailResponse historyDetailResponse) {
        CLPaymentResponse paymentResponse = historyDetailResponse.getPaymentResponse();
        amount.setText(paymentResponse.getAmount());
        cardHolderName.setText(paymentResponse.getCardHolderName());
        cardLabel.setText(paymentResponse.getCardType());
        transactionStatus.setText(paymentResponse.getTransactionStatus() >= ApprovalStatus.ISSUER_REFERRAL.getCode()
                && paymentResponse.getTransactionStatus() <= ApprovalStatus.UNKNOWN_ERROR.getCode()
                ? getResources().getString(R.string.declined)
                : ApprovalStatus.getStatus(paymentResponse.getTransactionStatus()).getMessage());
        approvalCode.setText(paymentResponse.getApprovalCode());
        traceNo.setText(paymentResponse.getTraceNo());
        batchNo.setText(paymentResponse.getBatchNo());
        invoiceNo.setText(paymentResponse.getInvoiceNo());
        transDescription.setText(paymentResponse.getTransDesc());
        reffNo.setText(paymentResponse.getRefNo());
        tc.setText(paymentResponse.getApplicationCryptogram());
        transTime.setText(CLDateUtil.getPrintTransTime(paymentResponse.getTransTime(), paymentResponse.getClientTransactionTimeZone()));
        transDate.setText(String.format("%s,\n%s", paymentResponse.getTransDate(),
                CLDateUtil.getPrintTransTime(paymentResponse.getTransTime(), paymentResponse.getClientTransactionTimeZone()).trim()));
        trxId.setText(paymentResponse.getTransactionId());
        mid.setText(paymentResponse.getMid());
        tid.setText(paymentResponse.getTid());
        transType.setText(paymentResponse.getTransactionType().getValue());
        voidedDate.setText(!paymentResponse.getVoidedDate().equals("-")
                ? String.format("%s,\n%s", paymentResponse.getVoidedDate(),
                CLDateUtil.getPrintTransTime(paymentResponse.getVoidedTime(), paymentResponse.getClientTransactionTimeZone()).trim())
                : "-");
        voidedBy.setText(paymentResponse.getVoidedBy());
    }

    @Override
    public void onShowLoading() {
        viewState.setStateShowLoading();
        showProgress(historyDetailLoading, true);
    }

    @Override
    public void onHideLoading() {
        viewState.setStateHideLoading();
        showProgress(historyDetailLoading, false);
    }

    @NonNull
    @Override
    public HistoryDetailViewState createViewState() {
        return new HistoryDetailViewState();
    }

    @Override
    public void onNewViewStateInstance() {
        presenter.doSalesHistoryDetail(transactionId);
    }

    @Override
    public void onPaymentHistorySuccess(CLPaymentHistoryDetailResponse paymentHistoryDetailResponse) {
        viewState.setHistoryResponse(paymentHistoryDetailResponse);
        this.historyDetailResponse = paymentHistoryDetailResponse;
        viewState.setHistoryResponse(paymentHistoryDetailResponse);
        setDetails(paymentHistoryDetailResponse);
    }

    @Override
    public void onPaymentHistoryError(String message) {
        displayInfo(this, message);
    }

    @Override
    public void onLoadPaymentImage(Bitmap photo) {
        paymentImage.setImageBitmap(photo);
    }

    @Override
    public void onVoidResponse(String message) {
        displayInfo(this, message);
    }

    @Override
    public void onSendReceiptResponse(String message) {
        displayInfo(this, message);
    }

    @Override
    public void onPrinterResponse(CLPrinterResponse printerResponse) {
        displayInfo(this, printerResponse.getMessage());
    }
}
