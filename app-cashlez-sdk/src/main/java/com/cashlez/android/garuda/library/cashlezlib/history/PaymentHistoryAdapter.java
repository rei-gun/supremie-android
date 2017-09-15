package com.cashlez.android.garuda.library.cashlezlib.history;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cashlez.android.garuda.library.cashlezlib.R;
import com.cashlez.android.sdk.payment.CLPaymentResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.cashlez.android.sdk.bean.StatusMapper.convertStatusCodeToString;

/**
 * Created by Taslim_Hartmann on 11/5/2016.
 */

class PaymentHistoryAdapter extends RecyclerView.Adapter<PaymentHistoryAdapter.ViewHolder> {

    private Context context;
    private List<CLPaymentResponse> items = new ArrayList<>();
    private ItemListener listener;

    PaymentHistoryAdapter(Context context, List<CLPaymentResponse> items, ItemListener listener) {
        this.context = context;
        this.items = items;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_row, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public List<CLPaymentResponse> getHistories() {
        return items;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.history_row_amount)
        TextView amount;
        @BindView(R.id.history_row_transaction_type)
        TextView trxType;
        @BindView(R.id.history_row_transaction_status)
        TextView trxStatus;
        @BindView(R.id.history_row_transaction_time)
        TextView trxTime;
        @BindView(R.id.history_row_transaction_date)
        TextView trxDate;
        @BindView(R.id.history_row_trace_number)
        TextView traceNo;
        @BindView(R.id.history_row_card_number)
        TextView cardNo;
        @BindView(R.id.history_row_approval_code)
        TextView approvalCode;

        public CLPaymentResponse item;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        public void setData(CLPaymentResponse paymentResponse) {
            this.item = paymentResponse;
            amount.setText(new StringBuilder().append(context.getResources().getString(R.string.transaction_amount))
                    .append(" ").append(paymentResponse.getAmount()).toString());
            cardNo.setText(new StringBuilder().append(context.getResources().getString(R.string.card_no))
                    .append(" ").append(paymentResponse.getCardNo()).toString());
            approvalCode.setText(new StringBuilder().append(context.getResources().getString(R.string.approval_code))
                    .append(" ").append(paymentResponse.getApprovalCode()).toString());
            traceNo.setText(new StringBuilder().append(context.getResources().getString(R.string.trace_no))
                    .append(" ").append(paymentResponse.getTraceNo()).toString());
            trxDate.setText(new StringBuilder().append(context.getResources().getString(R.string.transaction_date))
                    .append(" ").append(paymentResponse.getTransDate()).toString());
            trxTime.setText(new StringBuilder().append(context.getResources().getString(R.string.transaction_time))
                    .append(" ").append(paymentResponse.getTransTime()).toString());
            trxStatus.setText(new StringBuilder().append(context.getResources().getString(R.string.transaction_status))
                    .append(" ").append(convertStatusCodeToString(paymentResponse.getTransactionStatus()).toString()));
            trxType.setText(new StringBuilder().append(context.getResources().getString(R.string.transaction_type))
                    .append(" ").append(paymentResponse.getTransactionType().getValue()).toString());
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onItemClick(item);
            }
        }
    }

    public interface ItemListener {
        void onItemClick(CLPaymentResponse item);
    }
}