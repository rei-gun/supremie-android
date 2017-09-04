package fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.bintang5.supremie.R;
import com.bintang5.supremie.activity.State;

import java.util.Arrays;

/**
 * Created by rei on 2/09/17.
 */

public class ChoosePedasFragment extends Fragment {

    Integer pedasLevel;
    boolean[] items;
    PedasGridAdapter gridAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_pedas, container, false);
        GridView gridView = (GridView) view.findViewById(R.id.grid_pedas);
        pedasLevel = State.getInstance().getPedasLevel();
        items = new boolean[5];
        if (pedasLevel != null) {
            items[pedasLevel] = true;
        }

        gridAdapter = new PedasGridAdapter(getActivity(),
                items);
        gridView.setAdapter(gridAdapter);
//        if (pedasLevel != null) {
//            gridAdapter.setSelectedPosition(pedasLevel);
//            gridAdapter.notifyDataSetChanged();
//        }
//        gridAdapter.setSelectedPosition(pedasLevel);

        //TODO: select previously selected selectedBrand
//        if (pedasLevel != null) {
        setListener(this, gridView, items);
//        }
        return view;
    }

    private void setListener(final Fragment f, GridView gridView, final boolean[] items) {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (State.getInstance().getPedasLevel() == null ||
                        State.getInstance().getPedasLevel() != i) {
                    Arrays.fill(items, false);
                    items[i] = true;
                    //TODO: do this when fragment pauses instead
                    State.getInstance().setPedasLevel(i);
                    gridAdapter.notifyDataSetChanged();
                }

            }
        });
    }
}
