package com.bintang5.supremie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.bintang5.supremie.R;
import com.bintang5.supremie.activity.State;

import java.util.ArrayList;

import fragment.ToppingGridAdapter;
import model.ToppingStock;

/**
 * Created by rei on 2/09/17.
 */

public class ChooseToppingFragment extends AppCompatActivity {

    int[] quantities;
    @Nullable
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_choose_topping);
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
                Intent i = new Intent(ChooseToppingFragment.this, ChoosePedasFragment.class);
                startActivity(i);
                overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });

    }

}
