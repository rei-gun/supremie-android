package com.bintang5.supremie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.bintang5.supremie.R;
import com.bintang5.supremie.activity.State;

import java.util.Arrays;

import fragment.PedasGridAdapter;

/**
 * Created by rei on 2/09/17.
 */

public class ChoosePedasFragment extends AppCompatActivity {

    Integer pedasLevel;
    boolean[] items;
    PedasGridAdapter gridAdapter;

    @Nullable
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_choose_pedas);
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
                    Intent intent = new Intent(ChoosePedasFragment.this, ChooseDrinkFragment.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.enter, R.anim. exit);
                }

            }
        });
    }
}
