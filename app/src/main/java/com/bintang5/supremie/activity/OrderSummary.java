package com.bintang5.supremie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bintang5.supremie.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

import fragment.OrderSummaryGridAdapter;
import model.Drink;
import model.DrinkStock;
import model.MieStock;
import model.OrderSummaryItem;
import model.Topping;
import model.ToppingStock;

/**
 * Created by rei on 2/09/17.
 */

public class OrderSummary extends AppCompatActivity {

    ArrayList<OrderSummaryItem> items;
    OrderSummaryGridAdapter gridAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_summary);
        ExpHeightGridView gridView = (ExpHeightGridView)findViewById(R.id.grid_order_summary);

        //offset of -1 because mySQL IDs start at 1, not 0
        MieStock chosenMie = null;
        if (State.getInstance().getAllStock().getMieStocks() != null) {
            ArrayList<MieStock> mieStocks = State.getInstance().getAllStock().getMieStocks();
            for (MieStock ms : mieStocks) {
                if (ms.id == State.getInstance().getMieId()) {
                    State.getInstance().setMieStock(ms);
                    chosenMie = ms;
                    break;//since we're only doing 1 mie atm
                }
            }
        }
//        get(State.getInstance().getMieId()-1);
        items = new ArrayList<>();
        Integer subTotal = 0;

        //set first row
        if (chosenMie != null) {
            OrderSummaryItem item = new OrderSummaryItem(chosenMie.brand + " - " +
                    chosenMie.flavour, "QTY - " + State.getInstance().getQuantityMie().toString(),
                    "RP " + String.valueOf(State.getInstance().getQuantityMie() * chosenMie.price));
            items.add(item);
            subTotal += State.getInstance().getQuantityMie() * chosenMie.price;
        }

        //set 2nd row
        if (State.getInstance().getPedasLevel() != null) {
            OrderSummaryItem pedas = new OrderSummaryItem("LEVEL PEDAS - LEVEL " + State.getInstance().getPedasLevel().toString(),
                    "", "RP " + String.valueOf(State.getInstance().getPedasPrice(State.getInstance().getPedasLevel())));
            items.add(pedas);
            subTotal += State.getInstance().getPedasPrice(State.getInstance().getPedasLevel());
        }

        //create topping rows
        int[] toppingQuantities = State.getInstance().getToppingQuantities();
        if (toppingQuantities != null) {
            ArrayList<ToppingStock> toppings = State.getInstance().getAllStock().getToppingStocks();
            ArrayList<Topping> ts = new ArrayList<>();
            for (int i = 0; i < toppingQuantities.length; i++) {
                if (toppingQuantities[i] > 0) {
                    ToppingStock toppingStock = toppings.get(i);
                    OrderSummaryItem topping = new OrderSummaryItem("TOPPING - " + toppingStock.name,
                            "QTY - " + String.valueOf(toppingQuantities[i]),
                            "RP " + String.valueOf(toppingQuantities[i] * toppingStock.price));
                    items.add(topping);
                    subTotal += toppingQuantities[i] * toppingStock.price;

                    //TODO: put below in a new thread
                    Topping t = new Topping(toppingStock.id, toppings.get(i).name, toppingQuantities[i], null, toppingStock.price);
                    ts.add(t);
                }
            }
            State.getInstance().setToppings(ts);
        }

        //create drink rows
        int[] drinkQuantities = State.getInstance().getDrinkQuantities();
        if (drinkQuantities != null) {
            ArrayList<DrinkStock> ds = State.getInstance().getAllStock().getDrinkStocks();
            ArrayList<Drink> drinks = new ArrayList<>();
            for (int i = 0; i < drinkQuantities.length; i++) {
                if (drinkQuantities[i] > 0) {
                    DrinkStock d = ds.get(i);
                    OrderSummaryItem dRow = new OrderSummaryItem("MINUMAN - " + d.brand,
                            "QTY - " + String.valueOf(drinkQuantities[i]),
                            "RP " + String.valueOf(drinkQuantities[i] * d.price));
                    items.add(dRow);
                    subTotal += drinkQuantities[i] * d.price;

                    //TODO: put below in a new thread
                    Drink drink = new Drink(d.id, d.brand, d.flavour, drinkQuantities[i], d.price);
                    drinks.add(drink);
                }
            }
            State.getInstance().setDrinks(drinks);
        }

        //create subtotal row
        ((TextView)findViewById(R.id.subtotal_value)).setText(
                State.getInstance().addDot("RP "+subTotal.toString()));


        //create tax & service charge row
        Double taxCharge = subTotal*0.15;
        TextView taxText = (TextView)findViewById(R.id.tax_value);
        if (taxCharge < 1000) {
            taxText.setText(String.valueOf(taxCharge.intValue()));
        } else {
            taxText.setText(State.getInstance().addDot("RP "+String.valueOf(taxCharge.intValue())));
        }

        String taxString = State.getInstance().addDot(String.valueOf(taxCharge.intValue()));
        State.getInstance().setTaxChargeString(taxString);

        Double grandTotal = subTotal+ (subTotal*0.15);
        String s = "RP "+String.valueOf(grandTotal.intValue());
        s = State.getInstance().addDot(s);
        ((TextView)findViewById(R.id.total_value)).setText(s);
        State.getInstance().setGrandTotal(grandTotal.intValue());

        gridAdapter = new OrderSummaryGridAdapter(this,
                items);
        gridView.setAdapter(gridAdapter);
        gridView.setExpanded(true);
        gridView.setFocusable(false);

        ImageButton b = (ImageButton) findViewById(R.id.button_go_to_payment_method);
        setBListener(this, b);
        b.bringToFront();

    }

    private void setBListener(final OrderSummary activity, ImageButton b) {
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderSummary.this, ChoosePaymentMethod.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intent);
                overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });
    }
}