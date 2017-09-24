package com.bintang5.supremie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.bintang5.supremie.R;

import java.util.ArrayList;

import fragment.MieBrandGridAdapter;
import model.MieStock;

/**
 * Created by rei on 2/09/17.
 */

public class ChooseMieBrandFragment extends SupremieActivity {

    String selectedBrand;
    MieBrandGridAdapter gridAdapter;
    @Nullable
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_choose_brand);
        super.onCreate(savedInstanceState);

        final GridView gridView = (GridView)findViewById(R.id.grid_mie_brand);
        ArrayList<MieStock> oneOfEachBrand = State.getInstance().getAllStock().getOneOfEachBrand();
        selectedBrand = State.getInstance().getBrand();

        gridAdapter = new MieBrandGridAdapter(this,
                oneOfEachBrand, selectedBrand);
        gridView.setAdapter(gridAdapter);

        //TODO: select previously selected selectedBrand
        setListener(gridView, oneOfEachBrand);

        Button lanjut = (Button)findViewById(R.id.brand_lanjutkan);
        lanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i;
                if (State.getInstance().getBrand() == null) {
                    i = new Intent(ChooseMieBrandFragment.this, ChooseDrinkFragment.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.enter, R.anim.exit);
                }
            }
        });

    }

    private void setListener(GridView gridView, final ArrayList<MieStock> oneOfEachBrand) {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //check clicked Brand is different to previous click
                if (State.getInstance().getBrand() != oneOfEachBrand.get(i).brand ) {
                    gridAdapter.selectedBrand = oneOfEachBrand.get(i).brand;
                    State.getInstance().setBrand(oneOfEachBrand.get(i).brand);
                    //TODO: do this when fragment pauses instead
                    State.getInstance().setMieId(null); //clear flavour
                    State.getInstance().setChooseMieFragmentId(null, null);//clear flavour
                    //TODO: do this only on the first time gridView is clicked
//                    ((MainActivity)getActivity()).enableTab(2); //enable flavour tab
                    gridAdapter.notifyDataSetChanged();

                }
                Intent intent = new Intent(ChooseMieBrandFragment.this, ChooseMieFlavourFragment.class);
                startActivity(intent);
                overridePendingTransition(R.anim.enter, R.anim.exit);


            }
        });
    }

}
