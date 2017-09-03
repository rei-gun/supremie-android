package fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.bintang5.supremie.R;
import com.bintang5.supremie.activity.MainActivity;
import com.bintang5.supremie.activity.State;

import java.util.ArrayList;

import model.MieStock;

/**
 * Created by rei on 2/09/17.
 */

public class ChooseMieFlavourFragment extends Fragment {

    Integer chosenFlavour;
//    Integer chosenQuantity;
    int[] quantities;
    MainActivity mainActivity;
    ArrayList<MieStock> oneBrand;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            //probably orientation change

            chosenFlavour = savedInstanceState.getInt("chosenFlavour");
        } else {
            if (chosenFlavour != null) {
                //returning from backstack, data is fine, do nothing
            }
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_flavour, container, false);
        GridView gridView = (GridView) view.findViewById(R.id.grid_mie_flavour);
        mainActivity = (MainActivity)getActivity();
        MainActivity activity = (MainActivity)getActivity();
        oneBrand = activity.getAllStock().getOfBrand(activity.getBrand());
        quantities = new int[oneBrand.size()];
        for (int i=0; i<oneBrand.size(); i++) {
            quantities[i] = 0;
        }

        if (State.getInstance().getChooseMieFragmentId() != null) {
//            Log.v("DICK", State.getInstance().getChooseMieFragmentId().toString());
            quantities[State.getInstance().getChooseMieFragmentId()] =
                    State.getInstance().getQuantityMie();
        }
        final MieFlavourGridAdapter gridAdapter = new MieFlavourGridAdapter(getActivity(),
                oneBrand, quantities, chosenFlavour);
        gridView.setAdapter(gridAdapter);
        return view;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("chosenFlavour", chosenFlavour);
        outState.putInt("quantity", quantities[chosenFlavour]);
        Log.v("DICK", "DICKS");
    }

    @Override
    public void onPause() {
        super.onPause();
//        Log.v("DICK", "fragment paused"+chosenFlavour.toString());
        if (chosenFlavour != null) {

            State.getInstance().setChooseMieFragmentId(chosenFlavour, quantities[chosenFlavour]);
            State.getInstance().setMieId(oneBrand.get(chosenFlavour).id);
        }
    }
}
