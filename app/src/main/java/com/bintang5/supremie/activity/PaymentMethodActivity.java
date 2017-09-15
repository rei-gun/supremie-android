package com.bintang5.supremie.activity;

import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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

            }
        });

        Button creditButton = (Button)findViewById(R.id.button_debit);
        creditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postOrder("card");
            }
        });
    }


    private void postOrder(String paymentMethod) {

        Mie mie = new Mie(State.getInstance().getMieId(), State.getInstance().getQuantityMie(),
                1, State.getInstance().getAllStock().getMieStocks().get(State.getInstance().getMieId()).price,
                State.getInstance().getPedasLevel() , "", State.getInstance().getToppings());
        ArrayList mies = new ArrayList();
        mies.add(mie);

        Order order = new Order(State.getInstance().getGrandTotal(),
                paymentMethod, State.getInstance().getDiningMethod(),
                mies, State.getInstance().getDrinks());

        if (paymentMethod.equals("cash")) {
            OrderServer.getInstance(this).createOrder(cashlezPayment, order);
        } else if (paymentMethod.equals("card")) {
            //TODO: trigger Cashlez card payment SDK
            CLPayment debitCLPayment = new CLPayment();
            debitCLPayment.setAmount("10000");
            debitCLPayment.setDescription("blabla");
            debitCLPayment.setTransactionType(TransactionType.DEBIT);
            debitCLPayment.setVerificationMode(CLVerificationMode.PIN);
//            debitCLPayment.setImage(payment.getItemImage());
            cashlezPayment.doPayDebitPin(debitCLPayment);
//        cashlezPayment.doPayCreditPin();
            //then createOrder(order)
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
