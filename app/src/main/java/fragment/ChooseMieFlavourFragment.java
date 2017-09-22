package fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.bintang5.supremie.R;
import com.bintang5.supremie.activity.State;

import java.util.ArrayList;

import model.MieStock;

/**
 * Created by rei on 2/09/17.
 */

public class ChooseMieFlavourFragment extends Fragment {

    Integer chosenFlavour;
    int[] quantities;
    State state;
    ArrayList<MieStock> oneBrand;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_flavour, container, false);
        GridView gridView = (GridView) view.findViewById(R.id.grid_mie_flavour);
        state = State.getInstance();
        oneBrand = state.getAllStock().getOfBrand(State.getInstance().getBrand());
        quantities = new int[oneBrand.size()];

        if (State.getInstance().getChooseMieFragmentId() != null) {
            quantities[State.getInstance().getChooseMieFragmentId()] =
                    State.getInstance().getQuantityMie();
        }
        final MieFlavourGridAdapter gridAdapter = new MieFlavourGridAdapter(getActivity(),
                oneBrand, quantities, chosenFlavour);
        gridView.setAdapter(gridAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.v("DERP", String.valueOf(i));
                gridAdapter.setQuantity(i);
                gridAdapter.notifyDataSetChanged();
            }
        });
        return view;
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
