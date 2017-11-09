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

import fragment.MieBrandGridAdapter;
import model.MieStock;

/**
 * Created by rei on 2/09/17.
 */

public class ChooseMieBrand extends SupremieActivity {

    String selectedBrand;
    MieBrandGridAdapter gridAdapter;
    Button lanjut;
    ArrayList<MieStock> oneOfEachBrand;

    @Nullable
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.choose_brand);
        super.onCreate(savedInstanceState);
        TextView actionBarTitle = (TextView)findViewById(R.id.toolbar_title);
        actionBarTitle.setText("PILIH MIE / NASI / ROTI / PISANG");
        actionBarTitle.setTextSize(43);

        final GridView gridView = (GridView)findViewById(R.id.grid_mie_brand);
        oneOfEachBrand = State.getInstance().getAllStock().getOneOfEachBrand();
        selectedBrand = State.getInstance().getBrand();

        gridAdapter = new MieBrandGridAdapter(this,
                oneOfEachBrand, selectedBrand);

        gridView.setAdapter(gridAdapter);

        //TODO: select previously selected selectedBrand
        setListener(gridView, oneOfEachBrand);

        if (State.getInstance().getBrand() == null) {
            enableLanjut();
            enableToppingLanjut();
        } else {
            disableLanjut();
            disableToppingLanjut();
        }
    }

    private void setListener(GridView gridView, final ArrayList<MieStock> oneOfEachBrand) {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //check clicked Brand is different to previous click
                if (State.getInstance().getBrand() != oneOfEachBrand.get(i).brand ) {
                    State.getInstance().toppingQuantities = null; //if you change brand, topping choices get cleared
                    gridAdapter.selectedBrand = oneOfEachBrand.get(i).brand;
                    State.getInstance().setBrand(oneOfEachBrand.get(i).brand);
                    //TODO: do this when fragment pauses instead
                    State.getInstance().setMieId(null); //clear flavour
                    State.getInstance().setChooseMieFragmentId(null, null);//clear flavour
                    //TODO: do this only on the first time gridView is clicked
                    disableLanjut();
                    disableToppingLanjut();
                    gridAdapter.notifyDataSetChanged();

                }
                Intent intent;
                //Roti Bakar chosen, skip ChooseFlavour
                if (State.getInstance().getBrand().equals("Roti") ||
                        State.getInstance().getBrand().equals("Pisang") ||
                        State.getInstance().getBrand().equals("Nasi")) {
                    State.getInstance().setMieStock(oneOfEachBrand.get(i));
                    State.getInstance().quantityMie = 1;
                    State.getInstance().setPedasLevel(0);
                    intent = new Intent(ChooseMieBrand.this, ChooseTopping.class);
                }
                else if (!State.getInstance().getBrand().equals("Nasi")) {
                    intent = new Intent(ChooseMieBrand.this, ChooseMieFlavour.class);
                } else {
                    MieStock nasiStock = oneOfEachBrand.get(i);
                    State.getInstance().setChooseMieFragmentId(i, 1);
                    State.getInstance().setMieId(nasiStock.id);
                    intent = new Intent(ChooseMieBrand.this, ChooseTopping.class);
                }
                startActivity(intent);
                overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });
    }


    public void enableLanjut() {
        lanjut = (Button)findViewById(R.id.brand_lanjutkan);
        lanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i;
                if (State.getInstance().getBrand() == null) {
                    i = new Intent(ChooseMieBrand.this, ChooseDrink.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.enter, R.anim.exit);
                }
            }
        });
        lanjut.setBackgroundColor(ContextCompat.getColor(this, R.color.supremieRed));
    }


    public void disableLanjut() {
        lanjut = (Button)findViewById(R.id.brand_lanjutkan);
        lanjut.setOnClickListener(null);
        lanjut.setBackgroundColor(ContextCompat.getColor(this, R.color.lightGrey));
    }


    public void enableToppingLanjut() {
        lanjut = (Button)findViewById(R.id.lanjut_to_topping);
        lanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i;
                if (State.getInstance().getBrand() == null) {
                    i = new Intent(ChooseMieBrand.this, ChooseTopping.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.enter, R.anim.exit);
                }
            }
        });
        lanjut.setBackgroundColor(ContextCompat.getColor(this, R.color.supremieRed));
    }


    public void disableToppingLanjut() {
        lanjut = (Button)findViewById(R.id.lanjut_to_topping);
        lanjut.setOnClickListener(null);
        lanjut.setBackgroundColor(ContextCompat.getColor(this, R.color.lightGrey));
    }
}
