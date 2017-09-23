package com.bintang5.supremie.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.bintang5.supremie.R;
import com.bintang5.supremie.activity.State;

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

    }

}
