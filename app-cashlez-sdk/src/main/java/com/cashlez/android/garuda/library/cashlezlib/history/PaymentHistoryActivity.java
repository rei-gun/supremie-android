package com.cashlez.android.garuda.library.cashlezlib.history;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.cashlez.android.garuda.library.cashlezlib.BaseLCEActivity;
import com.cashlez.android.garuda.library.cashlezlib.R;
import com.cashlez.android.garuda.library.cashlezlib.DoubleInputDialogFragment;
import com.cashlez.android.garuda.library.cashlezlib.IInputDialogListener;
import com.cashlez.android.sdk.payment.CLPaymentResponse;
import com.hannesdorfmann.mosby3.mvp.viewstate.lce.LceViewState;
import com.hannesdorfmann.mosby3.mvp.viewstate.lce.data.RetainingLceViewState;

import java.util.List;

import butterknife.BindView;

public class PaymentHistoryActivity extends BaseLCEActivity<SwipeRefreshLayout, List<CLPaymentResponse>,
        PaymentHistoryView, PaymentHistoryPresenter> implements PaymentHistoryView, PaymentHistoryAdapter.ItemListener,
        IInputDialogListener, SwipeRefreshLayout.OnRefreshListener {

    public static final String TRANSACTION_ID = "TRANSACTION_ID";
    @BindView(R.id.payment_list)
    RecyclerView historyList;
    @BindView(R.id.contentView)
    SwipeRefreshLayout refreshLayout;
    int page = 0;
    private PaymentHistoryAdapter historyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        contentView.setOnRefreshListener(this);
        historyList.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.payment_history;
    }

    @NonNull
    @Override
    public PaymentHistoryPresenter createPresenter() {
        return new PaymentHistoryPresenter(this);
    }

    @Override
    public LceViewState<List<CLPaymentResponse>, PaymentHistoryView> createViewState() {
        return new RetainingLceViewState<>();
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        page++;
        presenter.doPaymentDetail(page, pullToRefresh);
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return HistoryErrorMessage.get(e, pullToRefresh, this);
    }

    @Override
    public void onItemClick(CLPaymentResponse item) {
        Intent intent = new Intent(this, PaymentHistoryDetailActivity.class);
        intent.putExtra(TRANSACTION_ID, item.getTransactionId());
        startActivityForResult(intent, 10);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode, @Nullable Bundle options) {
        super.startActivityForResult(intent, requestCode, options);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.history_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                showInputDialog(getResources().getString(R.string.please_input_to_proceed),
                        InputType.TYPE_CLASS_TEXT,
                        getResources().getString(R.string.invoice_no), 15,
                        InputType.TYPE_CLASS_TEXT,
                        getResources().getString(R.string.approval_code), 15);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showInputDialog(String title, int firstInputType, String firstHint, int firstMaxLength,
                                 int secondType, String secondHint, int secondMaxLength) {
        FragmentManager fm = getSupportFragmentManager();
        DoubleInputDialogFragment editNameDialogFragment =
                DoubleInputDialogFragment.newInstance(title, firstInputType, firstHint, firstMaxLength,
                        secondType, secondHint, secondMaxLength);
        editNameDialogFragment.show(fm, "history_menu");
    }

    @Override
    public void onFinishEditDialog(String invoiceNo, String approvalCode) {
        presenter.doPaymentDetail(1, invoiceNo, approvalCode);
    }

    @Override
    public void onRefresh() {
        loadData(true);
    }

    @Override
    public void showContent() {
        super.showContent();
        contentView.setRefreshing(false);
    }

    @Override
    public void showError(Throwable e, boolean pullToRefresh) {
        super.showError(e, pullToRefresh);
        contentView.setRefreshing(false);
    }

    @Override
    public List<CLPaymentResponse> getData() {
        return historyAdapter == null ? null : historyAdapter.getHistories();
    }

    @Override
    public void setData(List<CLPaymentResponse> data) {
        refreshLayout.setRefreshing(false);
        historyAdapter = new PaymentHistoryAdapter(this, data, this);
        historyList.setAdapter(historyAdapter);
        historyAdapter.notifyDataSetChanged();
    }

    private class SearchViewExpandListener implements MenuItemCompat.OnActionExpandListener {

        private Context context;

        public SearchViewExpandListener(Context c) {
            context = c;
        }

        @Override
        public boolean onMenuItemActionExpand(MenuItem item) {
            ((AppCompatActivity) context).getSupportActionBar().setDisplayShowHomeEnabled(true);
            return false;
        }

        @Override
        public boolean onMenuItemActionCollapse(MenuItem item) {
            ((AppCompatActivity) context).getSupportActionBar().setDisplayShowHomeEnabled(false);
            return false;
        }
    }
}