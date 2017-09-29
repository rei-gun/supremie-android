package com.bintang5.supremie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
    @Nullable
    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.grid_always_active_lanjut);
        super.onCreate(savedInstanceState);

        TextView title = (TextView)findViewById(R.id.toolbar_title);
        title.setText("PILIH TOPPING");

        GridView gridView = (GridView)findViewById(R.id.grid_mie_flavour);
        State state = State.getInstance();

        ArrayList<ToppingStock> toppingStocks = state.getAllStock().getToppingStocks();
        if (State.getInstance().getToppingQuantities() != null) {
            quantities = State.getInstance().getToppingQuantities();
        } else {
            State.getInstance().initToppingQuantities(toppingStocks.size());
            quantities = State.getInstance().getToppingQuantities();
        }
        final ToppingGridAdapter toppingGridAdapter = new ToppingGridAdapter(this,
                toppingStocks, quantities);
        gridView.setAdapter(toppingGridAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                toppingGridAdapter.addQuantity(i);
                toppingGridAdapter.notifyDataSetChanged();
            }
        });

        Button lanjut = (Button)findViewById(R.id.topping_lanjutkan);
        lanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (State.getInstance().getBrand().equals("Nasi")) {
                    Log.v("HALO", "ABANG");
                    Intent i = new Intent(ChooseTopping.this, ChoosePedasNasi.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.enter, R.anim.exit);
                } else {
                    Intent i = new Intent(ChooseTopping.this, ChoosePedas.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.enter, R.anim.exit);
                }
            }
        });

    }

}
