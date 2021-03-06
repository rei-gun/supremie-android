package com.bintang5.supremie.activity;

import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.bintang5.supremie.R;

import java.util.ArrayList;

import model.Mie;
import model.MieStock;
import model.Order;
import utils.CashlezPayment;
import utils.OrderServer;

/**
 * Created by rei on 2/09/17.
 */

public class ChoosePaymentMethod extends BasePaymentActivity {



    @Override
    protected void onStart() {
        super.onStart();
        cashlezPayment.initLocation();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_payment_method);

        disableUserInput();


        Button cashButton = (Button)findViewById(R.id.button_cash);
        cashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(ChoosePaymentMethod.this);
//                builder.setTitle("Pastikan Anda mau bayar dengan cash");
//                builder.setMessage("Anda tidak bisa berubah putusan setelah kini");
//                builder.setPositiveButton("Konfirmasi", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        postOrder("cash");
//
//                    }
//                });
//                builder.setNegativeButton("Ganti Methode", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.cancel();
//                    }
//                });
//                builder.show();
                postOrder("cash");
            }
        });
/*
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
        */
    }


    private void postOrder(String paymentMethod) {
//        MieStock mieStock = State.getInstance().getMieStock();
//        Log.v("HALO", State.getInstance().getDrinks().toString());
        //Drinks only
//        if (mieStock == null && State.getInstance().getToppingQuantities() == null) { //(State.getInstance().getDrinks() != null ||
                        //State.getInstance().getDrinks().size() != 0)) {
        //Ordering mie
//        } else if (mieStock != null) {
//            Mie mie = new Mie(mieStock.id, mieStock.brand, mieStock.flavour, State.getInstance().getQuantityMie(),
//                    1, mieStock.price,
//                    State.getInstance().getPedasLevel(), "", State.getInstance().getToppings());
//            mies.add(mie);
        //topping only
//        } else if (mieStock == null && (State.getInstance().getDrinks() == null ||
//                    State.getInstance().getDrinks().size() == 0)){
//            Mie mie = new Mie(24, "NO", "MIE", 1, 1, 1, 1, "", State.getInstance().getToppings());
//            mies.add(mie);
//        }

        if (paymentMethod.equals("cash")) {
            State.getInstance().tempOrder.paymentMethod = "cash";
//            Log.v("BURP", State.getInstance().getGrandTotal().toString()+State.getInstance().getDiningMethod()+State.getInstance().getDrinks().toString());
//            Order order = new Order(State.getInstance().getGrandTotal(),
//                    paymentMethod, State.getInstance().getDiningMethod(),
//                    mies, State.getInstance().getDrinks());
            OrderServer.getInstance(this).createOrder(cashlezPayment, this,
                    State.getInstance().tempOrder);
        } else if (paymentMethod.equals("debit")) {
            /*
            CLPayment debitCLPayment = new CLPayment();
            debitCLPayment.setAmount("5");//State.getInstance().getGrandTotal().toString());
            debitCLPayment.setDescription("debit test");
            debitCLPayment.setTransactionType(TransactionType.DEBIT);
            debitCLPayment.setVerificationMode(CLVerificationMode.PIN);
            cashlezPayment.doPayDebitPin(debitCLPayment);
            */
//            Order order = new Order(State.getInstance().getGrandTotal(),
//                    "card", State.getInstance().getDiningMethod(),
//                    mies, State.getInstance().getDrinks());
//            OrderServer.getInstance(this).createOrder(cashlezPayment, this, order);
        } else if (paymentMethod.equals("credit")) {
            /*
            CLPayment debitCLPayment = new CLPayment();
            debitCLPayment.setAmount("100");//State.getInstance().getGrandTotal().toString());
            debitCLPayment.setDescription("credit test");
            debitCLPayment.setTransactionType(TransactionType.DEBIT);
            debitCLPayment.setVerificationMode(CLVerificationMode.PIN);
            cashlezPayment.doPayCreditPin(debitCLPayment);
            */
//            Order order = new Order(State.getInstance().getGrandTotal(),
//                    "card", State.getInstance().getDiningMethod(),
//                    mies, State.getInstance().getDrinks());
//            OrderServer.getInstance(this).createOrder(cashlezPayment, this,order);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        cashlezPayment.stopLocationServices();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cashlezPayment.doStartPayment();
    }



}
