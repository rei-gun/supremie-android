package com.bintang5.supremie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.bintang5.supremie.R;

import java.util.ArrayList;

import fragment.DrinkGridAdapter;
import model.DrinkStock;

/**
 * Created by rei on 2/09/17.
 */

public class ChooseDrinkFragment extends AppCompatActivity {
    int[] quantities;
    @Nullable
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_choose_topping);
        GridView gridView = (GridView)findViewById(R.id.grid_mie_flavour);
        State state = State.getInstance();

        ArrayList<DrinkStock> drinkStocks = state.getAllStock().getDrinkStocks();
        if (State.getInstance().getDrinkQuantities() != null) {
            quantities = State.getInstance().getDrinkQuantities();
        } else {
            State.getInstance().initDrinkQuantities(drinkStocks.size());
            quantities = State.getInstance().getDrinkQuantities();
        }

        DrinkGridAdapter gridAdapter = new DrinkGridAdapter(this,
                drinkStocks, quantities);
        gridView.setAdapter(gridAdapter);

        Button lanjut = (Button)findViewById(R.id.topping_lanjutkan);
        lanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ChooseDrinkFragment.this, OrderSummaryFragment.class);
                startActivity(i);
                overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });

    }

}
