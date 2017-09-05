package com.bintang5.supremie.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.bintang5.supremie.R;

import java.util.ArrayList;

import model.Mie;
import model.Order;
import utils.OrderServer;

/**
 * Created by rei on 2/09/17.
 */

public class PaymentMethodActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);

        Button dineInButton = (Button)findViewById(R.id.button_cash);
        dineInButton.setOnClickListener(new View.OnClickListener() {
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

        Button takeawayButton = (Button)findViewById(R.id.button_card);
        takeawayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                State.getInstance().setDiningMethod("bungkus");
/*
                Topping topping = new Topping(1, 1, null, 3000);
                ArrayList toppings = new ArrayList();
                toppings.add(topping);
                Mie mie = new Mie(1, 2, 1, 3000, "pakai", 2, "", toppings);
                ArrayList mies = new ArrayList();
                mies.add(mie);
                Drink drink = new Drink(1, 1, 2500);
                ArrayList drinks = new ArrayList();
                drinks.add(drink);
                Order order = new Order(10000, "cash", "bungkus", mies, drinks);

                OrderServer.getInstance(getActivity()).createOrder(order);
                */
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

        OrderServer.getInstance(this).createOrder(order);
    }
}
