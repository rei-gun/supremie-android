package com.bintang5.supremie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.bintang5.supremie.R;

import java.util.Arrays;

import fragment.PedasGridAdapter;
import fragment.PedasNasiGridAdapter;

/**
 * Created by rei on 2/09/17.
 */

public class ChoosePedasNasi extends SupremieActivity {

    Integer pedasLevel;
    boolean[] items;
    PedasNasiGridAdapter gridAdapter;

    @Nullable
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.choose_pedas);
        super.onCreate(savedInstanceState);
        TextView title = (TextView)findViewById(R.id.toolbar_title);
        title.setText("PILIH PEDAS");

        GridView gridView = (GridView)findViewById(R.id.grid_pedas);
        pedasLevel = State.getInstance().getPedasLevel();
        items = new boolean[4];
        if (pedasLevel != null) {
            items[pedasLevel] = true;
        }

        Log.v("HALO", State.getInstance().getNasiPedasDescriptions().toString());
        gridAdapter = new PedasNasiGridAdapter(this,
                items, State.getInstance().getNasiPedasDescriptions());
        gridView.setAdapter(gridAdapter);

        setListener(gridView, items);
    }

    private void setListener(GridView gridView, final boolean[] items) {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Arrays.fill(items, false);
                items[i] = true;
                //TODO: do this when fragment pauses instead
                State.getInstance().setPedasLevel(i);
                Intent intent = new Intent(ChoosePedasNasi.this, ChooseDrink.class);
                startActivity(intent);
                overridePendingTransition(R.anim.enter, R.anim. exit);

            }
        });
    }
}
