package com.bintang5.supremie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.bintang5.supremie.R;

import java.util.Arrays;

import fragment.PedasGridAdapter;

/**
 * Created by rei on 2/09/17.
 */

public class ChoosePedas extends SupremieActivity {

    Integer pedasLevel;
    boolean[] items;
    PedasGridAdapter gridAdapter;

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

        gridAdapter = new PedasGridAdapter(this,
                items, State.getInstance().getPedasDescriptions());
        gridView.setAdapter(gridAdapter);

        setListener(gridView, items);
    }

    private void setListener(GridView gridView, final boolean[] items) {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (State.getInstance().getPedasLevel() == null ||
                        State.getInstance().getPedasLevel() != i) {
                    Arrays.fill(items, false);
                    items[i] = true;
                    //TODO: do this when fragment pauses instead
                    State.getInstance().setPedasLevel(i);
//                    gridAdapter.notifyDataSetChanged();
                    Intent intent = new Intent(ChoosePedas.this, ChooseDrink.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.enter, R.anim. exit);
                }

            }
        });
    }
}
