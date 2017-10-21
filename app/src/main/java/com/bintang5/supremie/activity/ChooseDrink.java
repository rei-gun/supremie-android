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

import fragment.DrinkGridAdapter;
import model.DrinkStock;

/**
 * Created by rei on 2/09/17.
 */

public class ChooseDrink extends SupremieActivity {
    int[] quantities;
    Button lanjut;
    @Nullable
    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.grid_always_active_lanjut);
        super.onCreate(savedInstanceState);
        TextView title = (TextView)findViewById(R.id.toolbar_title);
        title.setText("PILIH MINUM");
        GridView gridView = (GridView)findViewById(R.id.grid_mie_flavour);
        State state = State.getInstance();

        ArrayList<DrinkStock> drinkStocks = state.getAllStock().getDrinkStocks();
        if (State.getInstance().getDrinkQuantities() != null) {
            quantities = State.getInstance().getDrinkQuantities();
        } else {
            State.getInstance().initDrinkQuantities(drinkStocks.size());
            quantities = State.getInstance().getDrinkQuantities();
        }

        final DrinkGridAdapter gridAdapter = new DrinkGridAdapter(this,
                drinkStocks, quantities);
        gridAdapter.setOnQuantityChangeListener(new DrinkGridAdapter.OnQuantityChangeListener() {
            @Override
            public void onQuantityChange(int quantity) {
                if (quantity == 0 && State.getInstance().getMieId() == null) {
                    disableLanjut();
                } else {
                    enableLanjut();
                }
            }
        });
        gridView.setAdapter(gridAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                gridAdapter.addQuantity(i);
                gridAdapter.notifyDataSetChanged();
            }
        });

        //initialise the lanjutkan button
        //User has chosen Roti/Pisang or Drinks only
        if (State.getInstance().getBrand() == null || State.getInstance().getBrand().equals("Roti") ||
                State.getInstance().getBrand().equals("Pisang")) {
            enableLanjut();
        //User has chosen toppings only
        } else if (State.getInstance().getSubMieId() == null && (State.getInstance().getDrinks() == null || State.getInstance().getDrinks().size() == 0)) {
            disableLanjut();
        } else {
            enableLanjut();
        }

    }

    public void enableLanjut() {
        lanjut = (Button)findViewById(R.id.topping_lanjutkan);
        lanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ChooseDrink.this, OrderSummary.class);
                startActivity(i);
                overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });
        lanjut.setBackgroundColor(ContextCompat.getColor(this, R.color.supremieRed));
    }

    public void disableLanjut() {
        if (!State.getInstance().getBrand().equals("Roti") ||
                !State.getInstance().getBrand().equals("Pisang")) {
            lanjut = (Button) findViewById(R.id.topping_lanjutkan);
            lanjut.setOnClickListener(null);
            lanjut.setBackgroundColor(ContextCompat.getColor(this, R.color.lightGrey));
        }
    }

}
