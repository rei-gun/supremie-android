package com.bintang5.supremie.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.bintang5.supremie.R;

import java.util.ArrayList;

import fragment.MieFlavourGridAdapter;
import model.MieStock;

/**
 * Created by rei on 2/09/17.
 */

public class ChooseMieFlavourFragment extends AppCompatActivity {

    Integer chosenFlavour;
    int[] quantities;
    State state;
    ArrayList<MieStock> oneBrand;


    @Nullable
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_choose_flavour);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        GridView gridView = (GridView)findViewById(R.id.grid_mie_flavour);
        state = State.getInstance();
        oneBrand = state.getAllStock().getOfBrand(State.getInstance().getBrand());
        quantities = new int[oneBrand.size()];

        if (State.getInstance().getChooseMieFragmentId() != null) {
            quantities[State.getInstance().getChooseMieFragmentId()] =
                    State.getInstance().getQuantityMie();
        }
        final MieFlavourGridAdapter gridAdapter = new MieFlavourGridAdapter(this,
                oneBrand, quantities, chosenFlavour);
        gridView.setAdapter(gridAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.v("DERP", String.valueOf(i));
                gridAdapter.addQuantity(i);
                gridAdapter.notifyDataSetChanged();
            }
        });
    }


    //TODO: this don't work!
    @Override
    public void onPause() {
        super.onPause();
//        if (chosenFlavour != null) {
//            State.getInstance().setChooseMieFragmentId(chosenFlavour, quantities[chosenFlavour]);
//            State.getInstance().setMieId(oneBrand.get(chosenFlavour).id);
//        }
    }
}
