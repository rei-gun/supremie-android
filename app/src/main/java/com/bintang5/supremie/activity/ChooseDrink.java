package com.bintang5.supremie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
        gridView.setAdapter(gridAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                gridAdapter.addQuantity(i);
                gridAdapter.notifyDataSetChanged();
            }
        });

        Button lanjut = (Button)findViewById(R.id.topping_lanjutkan);
        lanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (State.getInstance().getDrinkQuantities() != null) {
                    Intent i = new Intent(ChooseDrink.this, OrderSummary.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.enter, R.anim.exit);
                }
            }
        });

    }

}
