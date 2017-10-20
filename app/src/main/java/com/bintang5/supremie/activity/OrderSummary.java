package com.bintang5.supremie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bintang5.supremie.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

import fragment.OrderSummaryGridAdapter;
import model.Drink;
import model.DrinkStock;
import model.Mie;
import model.MieStock;
import model.Order;
import model.OrderSummaryItem;
import model.Topping;
import model.ToppingStock;

/**
 * Created by rei on 2/09/17.
 */

public class OrderSummary extends AppCompatActivity {

    ArrayList<OrderSummaryItem> items;
    OrderSummaryGridAdapter gridAdapter;
    Order tempOrder;
    Integer subTotal;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_summary);
        ExpHeightGridView gridView = (ExpHeightGridView)findViewById(R.id.grid_order_summary);

        //offset of -1 because mySQL IDs start at 1, not 0
//        MieStock chosenMie = null;
//        if (State.getInstance().getAllStock().getMieStocks() != null) {
//            ArrayList<MieStock> mieStocks = State.getInstance().getAllStock().getMieStocks();
//            for (MieStock ms : mieStocks) {
//                if (ms.id == State.getInstance().getMieId()) {
//                    State.getInstance().setMieStock(ms);
//                    chosenMie = ms;
//                    break;//since we're only doing 1 mie atm
//                }
//            }
//        }
//        get(State.getInstance().getMieId()-1);
//        Log.v("BITCH", String.valueOf(State.getInstance().getMasterOrder().mies.size()));
        items = new ArrayList<>();
        subTotal = 0;
        tempOrder = updateOrder();
        //start building the grid items
        //set first row
        if (tempOrder.mies.size() > 0) {
            for (Mie m: tempOrder.mies) {
                if (!m.brand.equals("NO")) {
                    OrderSummaryItem item = new OrderSummaryItem(m.brand + " - " +
                            m.flavour, "QTY - " + m.quantityMie.toString(),
                            "RP " + String.valueOf(m.price));
                    items.add(item);
                    if (!State.getInstance().getBrand().equals("Roti")) {
                        OrderSummaryItem pedas = new OrderSummaryItem("LEVEL PEDAS - LEVEL " + m.extraChili.toString(),
                                "", "RP " + String.valueOf(State.getInstance().getPedasPrice(m.extraChili)));
                        items.add(pedas);
                    }
                    subTotal += State.getInstance().getPedasPrice(m.extraChili);
                    subTotal += m.price;
                }
                if (m.toppings.size() > 0) {
                    for (Topping topping: m.toppings) {
                        OrderSummaryItem toppingItem = new OrderSummaryItem("TOPPING - " + topping.name,
                                "QTY - " + topping.quantity.toString(),
                                "RP " + String.valueOf(topping.quantity*topping.price));
                        items.add(toppingItem);
                        subTotal += topping.quantity * topping.price;
                    }
                }
            }
        }

        //set 2nd row
//        if (State.getInstance().getPedasLevel() != null) {
//            OrderSummaryItem pedas = new OrderSummaryItem("LEVEL PEDAS - LEVEL " + State.getInstance().getPedasLevel().toString(),
//                    "", "RP " + String.valueOf(State.getInstance().getPedasPrice(State.getInstance().getPedasLevel())));
//            items.add(pedas);
//            subTotal += State.getInstance().getPedasPrice(State.getInstance().getPedasLevel());
//        }

        //create topping rows
//        int[] toppingQuantities = State.getInstance().getToppingQuantities();
//        if (toppingQuantities != null) {
//            ArrayList<ToppingStock> toppings = State.getInstance().getAllStock().getToppingStocks();
//            ArrayList<Topping> ts = new ArrayList<>();
//            for (int i = 0; i < toppingQuantities.length; i++) {
//                if (toppingQuantities[i] > 0) {
//                    ToppingStock toppingStock = toppings.get(i);
//                    OrderSummaryItem topping = new OrderSummaryItem("TOPPING - " + toppingStock.name,
//                            "QTY - " + String.valueOf(toppingQuantities[i]),
//                            "RP " + String.valueOf(toppingQuantities[i] * toppingStock.price));
//                    items.add(topping);
//                    subTotal += toppingQuantities[i] * toppingStock.price;
//
//                    //TODO: put below in a new thread
//                    Topping t = new Topping(toppingStock.id, toppings.get(i).name, toppingQuantities[i], null, toppingStock.price);
//                    ts.add(t);
//                }
//            }
//            State.getInstance().setToppings(ts);
//        }

        int[] drinkQuantities = State.getInstance().getDrinkQuantities();
        if (drinkQuantities != null) {
            ArrayList<DrinkStock> ds = State.getInstance().getAllStock().getDrinkStocks();
            for (int i = 0; i < drinkQuantities.length; i++) {
                if (drinkQuantities[i] > 0) {
                    DrinkStock d = ds.get(i);
                    OrderSummaryItem dRow = new OrderSummaryItem("MINUMAN - " + d.brand,
                            "QTY - " + String.valueOf(drinkQuantities[i]),
                            "RP " + String.valueOf(drinkQuantities[i] * d.price));
                    items.add(dRow);
                    subTotal += drinkQuantities[i] * d.price;

                    //TODO: put below in a new thread
                }
            }
//            State.getInstance().setDrinks(drinks);
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
        State.getInstance().setTaxServiceString(taxString);

        Double grandTotal = subTotal+ (subTotal*0.15);
        String s = "RP "+String.valueOf(grandTotal.intValue());
        s = State.getInstance().addDot(s);
        ((TextView)findViewById(R.id.total_value)).setText(s);
        State.getInstance().setGrandTotal(grandTotal.intValue());
        tempOrder.totalPrice = grandTotal.intValue();

        gridAdapter = new OrderSummaryGridAdapter(this,
                items);
        gridView.setAdapter(gridAdapter);
        gridView.setExpanded(true);
        gridView.setFocusable(false);

        Button b = (Button) findViewById(R.id.button_go_to_payment_method);
        setBListener(this, b);
        b.bringToFront();
        Button nambah = (Button)findViewById(R.id.button_new_mie);
        setNambahListener(this, nambah);

    }

    private void setBListener(final OrderSummary activity, Button b) {
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                State.getInstance().tempOrder = tempOrder;
                Intent intent = new Intent(OrderSummary.this, ChoosePaymentMethod.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intent);
                overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });
    }

    private void setNambahListener(final OrderSummary orderSummary, Button b) {
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                State.getInstance().setMasterOrder(tempOrder);
                State.getInstance().clearNewMie();
                Intent intent = new Intent(OrderSummary.this, ChooseMieBrand.class);
                orderSummary.startActivity(intent);
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
            }
        });
    }

    private Order updateOrder() {
        Order mo = State.getInstance().getMasterOrder();
//        Log.v("HERP", State.getInstance().getDrinks().toString());
        Order tempOrder = new Order(mo.totalPrice, null, State.getInstance().getDiningMethod(),
                new ArrayList(mo.mies), new ArrayList());
        MieStock mieStock = State.getInstance().getMieStock();
        ArrayList mies = tempOrder.mies;
        ArrayList drinks = tempOrder.drinks;

        if (mieStock == null && State.getInstance().getToppingQuantities() == null) {
            //Drinks only
        } else if (mieStock != null) {//Ordering mie
            Log.v("BITCH", "FART");
            Mie mie = new Mie(mieStock.id, mieStock.brand, mieStock.flavour, State.getInstance().getQuantityMie(),
                    1, mieStock.price * State.getInstance().getQuantityMie(),
                    State.getInstance().getPedasLevel(), "", new ArrayList<Topping>());
            mies.add(mie);
            ArrayList<Topping> ts = mie.toppings;
            int[] toppingQuantities = State.getInstance().getToppingQuantities();
            if (toppingQuantities != null) {
                ArrayList<ToppingStock> toppings;
                if (State.getInstance().getBrand().equals("Roti")) {
                    toppings = State.getInstance().getRotiToppings();
                } else {
                    toppings = State.getInstance().getAllStock().getToppingStocks();
                }
                for (int i = 0; i < toppingQuantities.length; i++) {
                    if (toppingQuantities[i] > 0) {
                        //Roti chosen, offset non roti toppings
                        ToppingStock toppingStock;
//                        if (State.getInstance().getBrand().equals("Roti")) {
//                            int offset = State.getInstance().getRotiToppings().size();
//                            toppingStock = State.getInstance().getRotiToppings();
//                        } else {
                            toppingStock = toppings.get(i);
//                        }
                        Topping topping = new Topping(toppingStock.id, toppingStock.name, toppingQuantities[i],
                                null, toppingStock.price);
                        ts.add(topping);
                    }
                }
            }

        } else if (mieStock == null && (State.getInstance().getDrinks() == null ||
                State.getInstance().getDrinks().size() == 0)) { //topping only
            Mie mie = new Mie(24, "NO", "MIE", 1, 1, 1, 0, "", buildToppings());
            mies.add(mie);
        }

        //create drink rows
        int[] drinkQuantities = State.getInstance().getDrinkQuantities();
        if (drinkQuantities != null) {
            ArrayList<DrinkStock> ds = State.getInstance().getAllStock().getDrinkStocks();
            for (int i = 0; i < drinkQuantities.length; i++) {
                if (drinkQuantities[i] > 0) {
                    DrinkStock d = ds.get(i);
                    Drink drink = new Drink(d.id, d.brand, d.flavour, drinkQuantities[i], d.price);
                    drinks.add(drink);
                }
            }
//            State.getInstance().setDrinks(drinks);
        }
        return tempOrder;
    }

    private ArrayList<Topping> buildToppings() {
        ArrayList<Topping> ts = new ArrayList<>();
        int[] toppingQuantities = State.getInstance().getToppingQuantities();
        if (toppingQuantities != null) {
            ArrayList<ToppingStock> toppings = State.getInstance().getAllStock().getToppingStocks();
            for (int i = 0; i < toppingQuantities.length; i++) {
                if (toppingQuantities[i] > 0) {
                    ToppingStock toppingStock = toppings.get(i);
                    Topping topping = new Topping(toppingStock.id, toppingStock.name, toppingQuantities[i],
                            null, toppingStock.price);
                    ts.add(topping);
                }
            }
        }
        return ts;
    }
}