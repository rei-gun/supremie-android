package com.bintang5.supremie.activity;

import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.bintang5.supremie.R;
import com.cashlez.android.sdk.CLPayment;
import com.cashlez.android.sdk.bean.TransactionType;
import com.cashlez.android.sdk.payment.CLVerificationMode;

import java.util.ArrayList;

import model.Mie;
import model.Order;
import utils.CashlezPayment;
import utils.OrderServer;

/**
 * Created by rei on 2/09/17.
 */

public class PaymentMethodActivity extends AppCompatActivity {

    protected BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private CashlezPayment cashlezPayment;

    @Override
    protected void onStart() {
        super.onStart();
        cashlezPayment.initLocation();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);


        if (!bluetoothAdapter.isEnabled()) {
            //TODO: bluetooth not enabled
        } else {
            cashlezPayment = new CashlezPayment(this);

        }

        Button cashButton = (Button)findViewById(R.id.button_cash);
        cashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PaymentMethodActivity.this);
                builder.setTitle("Pastikan Anda mau bayar dengan cash");
                builder.setMessage("Anda tidak bisa berubah putusan setelah kini");
                builder.setPositiveButton("Konfirmasi", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        postOrder("cash");

                    }
                });
                builder.setNegativeButton("Ganti Methode", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.show();
            }
        });

        Button debitButton = (Button)findViewById(R.id.button_credit);
            debitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postOrder("credit");
            }
        });

        Button creditButton = (Button)findViewById(R.id.button_debit);
        creditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postOrder("debit");
            }
        });
    }


    private void postOrder(String paymentMethod) {
        Mie mie = new Mie(State.getInstance().getMieId(), State.getInstance().getQuantityMie(),
                1, State.getInstance().getAllStock().getMieStocks().get(State.getInstance().getMieId()).price,
                State.getInstance().getPedasLevel() , "", State.getInstance().getToppings());
        ArrayList mies = new ArrayList();
        mies.add(mie);

        if (paymentMethod.equals("cash")) {
            Order order = new Order(State.getInstance().getGrandTotal(),
                    paymentMethod, State.getInstance().getDiningMethod(),
                    mies, State.getInstance().getDrinks());
            Log.v("BIGDICK", order.toString());
            OrderServer.getInstance(this).createOrder(cashlezPayment, order);
        } else if (paymentMethod.equals("debit")) {
            CLPayment debitCLPayment = new CLPayment();
            debitCLPayment.setAmount("5");//State.getInstance().getGrandTotal().toString());
            debitCLPayment.setDescription("debit test");
            debitCLPayment.setTransactionType(TransactionType.DEBIT);
            debitCLPayment.setVerificationMode(CLVerificationMode.PIN);
            cashlezPayment.doPayDebitPin(debitCLPayment);
            //then createOrder(order)
        } else if (paymentMethod.equals("credit")) {
            CLPayment debitCLPayment = new CLPayment();
            debitCLPayment.setAmount("100");//State.getInstance().getGrandTotal().toString());
            debitCLPayment.setDescription("credit test");
            debitCLPayment.setTransactionType(TransactionType.DEBIT);
            debitCLPayment.setVerificationMode(CLVerificationMode.PIN);
            cashlezPayment.doPayCreditPin(debitCLPayment);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        cashlezPayment.unregisterReceiver();
        cashlezPayment.stopLocationServices();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cashlezPayment.registerReceiver();
        cashlezPayment.doStartPayment();
    }

}
