package com.bintang5.supremie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.bintang5.supremie.R;

import java.util.ArrayList;

import fragment.ToppingGridAdapter;
import model.ToppingStock;

/**
 * Created by rei on 2/09/17.
 */

public class ChooseTopping extends SupremieActivity {

    int[] quantities;
    Button lanjut;
    @Nullable
    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.grid_always_active_lanjut);
        super.onCreate(savedInstanceState);

        TextView title = (TextView)findViewById(R.id.toolbar_title);
        title.setText("PILIH TOPPING");

        GridView gridView = (GridView)findViewById(R.id.grid_mie_flavour);
        State state = State.getInstance();

        ArrayList<ToppingStock> toppingStocks;
        if (State.getInstance().getBrand() != null &&
                (State.getInstance().getBrand().equals("Roti") ||
                State.getInstance().getBrand().equals("Pisang"))) {
            toppingStocks = state.getRotiToppings();

        } else { //normal toppings
            toppingStocks = state.getAllStock().getToppingStocks();
        }

        if (State.getInstance().getToppingQuantities() != null) {
            quantities = State.getInstance().getToppingQuantities();
        } else {
            State.getInstance().initToppingQuantities(toppingStocks.size());
            quantities = State.getInstance().getToppingQuantities();
        }
        final ToppingGridAdapter toppingGridAdapter = new ToppingGridAdapter(this,
                toppingStocks, quantities);
        toppingGridAdapter.setOnQuantityChangeListener(new ToppingGridAdapter.OnQuantityChangeListener() {
            @Override
            public void onQuantityChange(int quantity) {
                if (quantity == 0 && State.getInstance().getMieId() == null) {
                    disableLanjut();
                } else {
                    enableLanjut();
                }
            }
        });
        gridView.setAdapter(toppingGridAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                toppingGridAdapter.addQuantity(i);
                toppingGridAdapter.notifyDataSetChanged();
            }
        });
        if (State.getInstance().getBrand() == null && (State.getInstance().getToppings() == null ||
                                                    State.getInstance().getToppings().size() == 0)) {
            disableLanjut();
        } else {
            enableLanjut();
        }
/*
        Button lanjut = (Button)findViewById(R.id.topping_lanjutkan);
        lanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (State.getInstance().getBrand() == null) { //no mie chosen
                    Intent i = new Intent(ChooseTopping.this, OrderSummary.class);
                    startActivity(i);
                } else if (State.getInstance().getBrand().equals("Nasi")) {
                    Intent i = new Intent(ChooseTopping.this, ChoosePedasNasi.class);
                    startActivity(i);
                } else {
                    Intent i = new Intent(ChooseTopping.this, ChoosePedas.class);
                    startActivity(i);
                }
                overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });*/
    }

    public void enableLanjut() {
        lanjut = (Button)findViewById(R.id.topping_lanjutkan);
        lanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (State.getInstance().getBrand() == null) { //no mie chosen
                    Intent i = new Intent(ChooseTopping.this, OrderSummary.class);
                    startActivity(i);
                } else if (State.getInstance().getBrand().equals("Nasi")) {
                    Intent i = new Intent(ChooseTopping.this, ChoosePedasNasi.class);
                    startActivity(i);
                } else if(State.getInstance().getBrand().equals("Roti") ||
                        State.getInstance().getBrand().equals("Pisang")) {
                    Intent i = new Intent(ChooseTopping.this, ChooseDrink.class);
                    startActivity(i);
                } else {
                    Intent i = new Intent(ChooseTopping.this, ChoosePedas.class);
                    startActivity(i);
                }
                overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });
        lanjut.setBackgroundColor(ContextCompat.getColor(this, R.color.supremieRed));
    }

    public void disableLanjut() {
        if (State.getInstance().getBrand() != null &&
                (!State.getInstance().getBrand().equals("Roti") &&
                !State.getInstance().getBrand().equals("Pisang"))
            ) {
            lanjut = (Button) findViewById(R.id.topping_lanjutkan);
            lanjut.setOnClickListener(null);
            lanjut.setBackgroundColor(ContextCompat.getColor(this, R.color.lightGrey));
        }
    }
}
