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

import fragment.MieFlavourGridAdapter;
import model.MieStock;

/**
 * Created by rei on 2/09/17.
 */

public class ChooseMieFlavour extends SupremieActivity {

    Integer chosenFlavour;
    int[] quantities;
    State state;
    ArrayList<MieStock> oneBrand;
    Button lanjut;

    @Nullable
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.choose_flavour);
        super.onCreate(savedInstanceState);
        TextView title = (TextView)findViewById(R.id.toolbar_title);
        title.setText("PILIH MIE");

        GridView gridView = (GridView)findViewById(R.id.grid_mie_flavour);
        state = State.getInstance();
        oneBrand = state.getAllStock().getOfBrand(State.getInstance().getBrand());
        quantities = new int[oneBrand.size()];
        chosenFlavour = state.getSubMieId();

        if (State.getInstance().getSubMieId() != null) {
            quantities[State.getInstance().getSubMieId()] =
                    State.getInstance().getQuantityMie();
        }
        final MieFlavourGridAdapter flavourGridAdapter = new MieFlavourGridAdapter(this,
                oneBrand, quantities, chosenFlavour);
        flavourGridAdapter.setOnQuantityChangeListener(new MieFlavourGridAdapter.OnQuantityChangeListener() {
            @Override
            public void onQuantityChange(int quantity) {
                if (quantity > 0) {
                    enableLanjut();
                } else if (quantity == 0) {
                    disableLanjut();
                }
            }
        });

        gridView.setAdapter(flavourGridAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Log.v("DERP", String.valueOf(i));
                flavourGridAdapter.addQuantity(i);
                flavourGridAdapter.notifyDataSetChanged();
            }
        });

        //initialise the lanjutkan button
        if (State.getInstance().getMieId() != null) {
            enableLanjut();
        } else {
            disableLanjut();
        }
    }

    public void enableLanjut() {
        lanjut = (Button)findViewById(R.id.flavour_lanjutkan);
        lanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ChooseMieFlavour.this, ChooseTopping.class);
                startActivity(i);
                overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });
        lanjut.setBackgroundColor(ContextCompat.getColor(this, R.color.supremieRed));
    }

    public void disableLanjut() {
        lanjut = (Button)findViewById(R.id.flavour_lanjutkan);
        lanjut.setOnClickListener(null);
        lanjut.setBackgroundColor(ContextCompat.getColor(this, R.color.lightGrey));
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
